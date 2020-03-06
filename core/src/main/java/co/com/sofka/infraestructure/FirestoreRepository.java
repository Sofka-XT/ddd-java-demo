package co.com.sofka.infraestructure;


import co.com.sofka.domain.generic.AggregateRootId;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.generic.StoredEvent;
import co.com.sofka.infraestructure.repository.EventStoreRepository;
import co.com.sofka.infraestructure.repository.QueryFaultException;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.CollectionReference;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static co.com.sofka.generic.helpers.SerializeHelper.serializeEvent;

public class FirestoreRepository implements EventStoreRepository {

    private final Firestore database;

    public FirestoreRepository(final Firestore database) {
        this.database = database;
    }

    @Override
    public List<DomainEvent> getEventsBy(final AggregateRootId aggregateRootId) {
        List<QueryDocumentSnapshot> query = getQuerySnapshotApiFuture(aggregateRootId);
        return query.stream().map(this::getDomainEvent).collect(Collectors.toList());
    }

    private DomainEvent getDomainEvent(QueryDocumentSnapshot document) {
        final ObjectMapper mapper = new ObjectMapper();
        StoredEvent storedEvent = mapper.convertValue(document.getData(), StoredEvent.class);
        try {
            Class<DomainEvent> domainEventClass = (Class<DomainEvent>) Class.forName(storedEvent.getTypeName());
            return mapper.readValue(storedEvent.getEventBody(), domainEventClass);
        } catch (IOException | ClassNotFoundException e) {
            throw new EventMapperException(e.getMessage());
        }
    }

    private List<QueryDocumentSnapshot> getQuerySnapshotApiFuture(final AggregateRootId aggregateRootId) {
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
    public void saveEvent(final AggregateRootId aggregateRootId, final DomainEvent event) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        final String eventSerialization = serializeEvent(mapper, event);
        StoredEvent storedEvent = wrapEvent(event, eventSerialization);
        setDocumentToCollection(aggregateRootId, event, storedEvent);

    }

    private void setDocumentToCollection(final AggregateRootId aggregateRootId,
                                         final DomainEvent event,
                                         final StoredEvent storedEvent) {
        try {
            database.collection(aggregateRootId.toString())
                    .document(event.uuid.toString())
                    .set(storedEvent).get();

        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new PersistenceResultException();
        }
    }

    private StoredEvent wrapEvent(final DomainEvent domainEvent, final String eventSerialization) {
        return new StoredEvent(domainEvent.getClass().getCanonicalName(),
                new Date(domainEvent.when.toEpochMilli()),
                eventSerialization
        );
    }

    public Map<String, List<DomainEvent>> getDomainEventList() {

        Map<String, List<DomainEvent>> allEventsCollection = new HashMap<>();

        Iterable<CollectionReference> future = database.getCollections();
        future.forEach(collectionReference ->
                allEventsCollection.put(collectionReference.getPath(),
                        getEventsBy(new AggregateRootId(collectionReference.getPath()))));
        return allEventsCollection;
    }
}

