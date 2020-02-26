package co.com.sofka.infraestructure;

import co.com.sofka.domain.AggregateRootId;
import co.com.sofka.domain.DomainEvent;
import co.com.sofka.generic.StoredEvent;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class FirestoreRepository implements EventStoreRepository {

    private static final Logger logger = LoggerFactory.getLogger(FirestoreRepository.class);


    private final Firestore database;

    public FirestoreRepository(Firestore database){
        this.database = database;
    }

    @Override
    public List<DomainEvent> getEventsBy(AggregateRootId aggregateRootId) {
        List<QueryDocumentSnapshot> query  = getQuerySnapshotApiFuture(aggregateRootId);
        return query.stream().map(document -> {
                final ObjectMapper mapper = new ObjectMapper();
                StoredEvent storedEvent = mapper.convertValue(document.getData(), StoredEvent.class);
                try {
                    Class<DomainEvent> domainEventClass = (Class<DomainEvent>) Class.forName(storedEvent.getTypeName());
                    return mapper.readValue(storedEvent.getEventBody(), domainEventClass);
                } catch (IOException | ClassNotFoundException e) {
                   throw new EventMapperException(e.getMessage());
                }
            }).collect(Collectors.toList());

    }

    private List<QueryDocumentSnapshot> getQuerySnapshotApiFuture(AggregateRootId aggregateRootId) {
        ApiFuture<QuerySnapshot> query = database.collection(aggregateRootId.toString())
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
    public void saveEventsWithAn(final AggregateRootId aggregateRootId, final DomainEvent event) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        final String eventSerialization = serializeEvent(mapper, event);
        StoredEvent storedEvent = wrapEvent(event, eventSerialization);
        setDocumentToCollection(aggregateRootId, event, storedEvent);

    }

    private void setDocumentToCollection(AggregateRootId aggregateRootId, DomainEvent event, StoredEvent storedEvent) {
        try {
            WriteResult writeResult = database.collection(aggregateRootId.toString())
                    .document(event.uuid.toString())
                    .set(storedEvent).get();

            logger.info(String.valueOf(writeResult.getUpdateTime()));

        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new PersistenceResultException();
        }
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

        Iterable<CollectionReference> future = database.getCollections();
        future.forEach(collectionReference ->
                allEventsCollection.put(collectionReference.getPath(), getEventsBy(new AggregateRootId(collectionReference.getPath()))));
        return allEventsCollection;
    }
}

