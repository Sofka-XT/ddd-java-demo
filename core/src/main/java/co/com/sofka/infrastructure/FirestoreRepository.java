package co.com.sofka.infrastructure;


import co.com.sofka.core.issue.values.IssueListId;
import co.com.sofka.domain.generic.AggregateRootId;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.repository.EventStoreRepository;
import co.com.sofka.infraestructure.repository.QueryFaultException;
import co.com.sofka.infraestructure.store.StoredEvent;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.CollectionReference;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class FirestoreRepository implements EventStoreRepository<IssueListId> {

    private final Firestore database;

    public FirestoreRepository(final Firestore database) {
        this.database = database;
    }

    @Override
    public List<DomainEvent> getEventsBy(final IssueListId issueListId) throws QueryFaultException {
        List<QueryDocumentSnapshot> query = getQuerySnapshotApiFuture(issueListId);
        return query.stream().map(this::getDomainEvent).collect(Collectors.toList());
    }

    private DomainEvent getDomainEvent(QueryDocumentSnapshot document) {
        Gson gson = new Gson();
        StoredEvent storedEvent = gson.fromJson(gson.toJson(document.getData()), StoredEvent.class);
        try {
            Class<DomainEvent> domainEventClass = (Class<DomainEvent>) Class.forName(storedEvent.getTypeName());
            return gson.fromJson(storedEvent.getEventBody(), domainEventClass);
        } catch (ClassNotFoundException e) {
            throw new EventMapperException(e.getMessage());
        }
    }

    private List<QueryDocumentSnapshot> getQuerySnapshotApiFuture(final AggregateRootId aggregateRootId) throws QueryFaultException {
        ApiFuture<QuerySnapshot> query = database.collection(aggregateRootId.toString())
                .orderBy("occurredOn")
                .get();
        try {
            return query.get().getDocuments();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new QueryFaultException("Error");
        }
    }

    @Override
    public void saveEvent(IssueListId issueListId, StoredEvent storedEvent) {
        setDocumentToCollection(issueListId, storedEvent);
    }

    private void setDocumentToCollection(final IssueListId issueListId,
                                         final StoredEvent storedEvent) {
        Gson gson = new Gson();

        try {
            Class<DomainEvent> domainEventClass = (Class<DomainEvent>) Class.forName(storedEvent.getTypeName());
            final DomainEvent event = gson.fromJson(storedEvent.getEventBody(), domainEventClass);
            database.collection(issueListId.toString())
                    .document(event.uuid.toString())
                    .set(storedEvent)
                    .get();

        } catch (InterruptedException | ExecutionException | ClassNotFoundException e) {
            Thread.currentThread().interrupt();
            throw new PersistenceResultException();
        }
    }

    public Map<String, List<DomainEvent>> getDomainEventList() {

        Map<String, List<DomainEvent>> allEventsCollection = new HashMap<>();

        Iterable<CollectionReference> future = database.getCollections();
        future.forEach(collectionReference ->
        {
            try {
                allEventsCollection.put(collectionReference.getPath(),
                        getEventsBy(new IssueListId(collectionReference.getPath())));
            } catch (QueryFaultException e) {
                e.printStackTrace();
            }
        });
        return allEventsCollection;
    }
}

