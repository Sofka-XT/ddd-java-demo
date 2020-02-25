package co.com.sofka.infraestructure;

import co.com.sofka.domain.AggregateRootId;
import co.com.sofka.domain.DomainEvent;
import co.com.sofka.generic.StoredEvent;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.Tuple;
import com.google.cloud.firestore.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FirestoreRepository implements EventStoreRepository {
    private static final Logger logger = LoggerFactory.getLogger(FirestoreRepository.class);


    private final Firestore db;

    public FirestoreRepository(Firestore db){
        this.db = db;
    }

    @Override
    public List<DomainEvent> getEventsBy(AggregateRootId aggregateRootId) throws QueryFaultException {
        List<QueryDocumentSnapshot> query  = getQuerySnapshotApiFuture(aggregateRootId);
        return query.stream().map(document -> {
                final ObjectMapper mapper = new ObjectMapper();
                StoredEvent storedEvent = mapper.convertValue(document.getData(), StoredEvent.class);
                try {
                    Class<DomainEvent> domainEventClass = (Class<DomainEvent>) Class.forName(storedEvent.getTypeName());
                    return mapper.readValue(storedEvent.getEventBody(), domainEventClass);
                } catch (IOException | ClassNotFoundException e) {
                   throw new EventMapperException();
                }
            }).collect(Collectors.toList());

    }

    private List<QueryDocumentSnapshot> getQuerySnapshotApiFuture(AggregateRootId aggregateRootId) throws QueryFaultException {
        ApiFuture<QuerySnapshot> query = db.collection(aggregateRootId.toString())
                .orderBy("occurredOn")
                .get();
        try {
            return query.get().getDocuments();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new QueryFaultException();
        }
    }

    @Override
    public void saveEventsWithAn(final AggregateRootId aggregateRootId, final List<DomainEvent> events) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        events.stream()
                .map(getDomainEventTupleFunction(mapper))
                .map(getTupleApiFutureFunction(aggregateRootId))
                .map(getApiFutureWriteResultFunction())
                .forEach(writeResult -> logger.info(String.valueOf(writeResult.getUpdateTime())));
    }

    private Function<ApiFuture<WriteResult>, WriteResult> getApiFutureWriteResultFunction() {
        return writeResultApiFuture -> {
            try {
                return writeResultApiFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                Thread.currentThread().interrupt();
                throw new PersistenceResultException();
            }
        };
    }

    private Function<DomainEvent, Tuple<UUID, StoredEvent>> getDomainEventTupleFunction(ObjectMapper mapper) {
        return domainEvent -> {
            final String eventSerialization = serializeEvent(mapper, domainEvent);
            StoredEvent storedEvent = wrapEvent(domainEvent, eventSerialization);
            return Tuple.of(domainEvent.uuid, storedEvent);

        };
    }

    private Function<Tuple<UUID, StoredEvent>, ApiFuture<WriteResult>> getTupleApiFutureFunction(AggregateRootId aggregateRootId) {
        return storedEventTuple ->
                db.collection(aggregateRootId.toString())
                .document(storedEventTuple.x().toString())
                .set(storedEventTuple.y());
    }

    private StoredEvent wrapEvent(DomainEvent domainEvent, String eventSerialization) {
        return new StoredEvent(domainEvent.getClass().getCanonicalName(),
                        domainEvent.when,
                        eventSerialization
        );
    }

    private String serializeEvent(ObjectMapper mapper, DomainEvent domainEvent) {
        final String eventSerialization;
        try {
            eventSerialization = mapper.writeValueAsString(domainEvent);
        } catch (JsonProcessingException e) {
           throw new EventMapperException();
        }
        return eventSerialization;
    }

    public Map<String, List<DomainEvent>> getDomainEventList() {

        Map<String, List<DomainEvent>> allEventsCollection= new HashMap<>();

        Iterable<CollectionReference> future = db.getCollections();
        future.forEach(collectionReference ->
                allEventsCollection.put(collectionReference.getPath(), getEventsBy(new AggregateRootId(collectionReference.getPath()))));
        return allEventsCollection;
    }
}

