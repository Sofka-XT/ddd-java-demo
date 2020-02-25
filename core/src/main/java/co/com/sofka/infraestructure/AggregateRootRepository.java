package co.com.sofka.infraestructure;

import co.com.sofka.core.issue.Issue;
import co.com.sofka.domain.AggregateRootId;
import co.com.sofka.domain.DomainEvent;
import co.com.sofka.infraestructure.EventStoreRepository;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.protobuf.Any;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class AggregateRootRepository implements EventStoreRepository {

    private final Firestore db;

    private Map<String, List<DomainEvent>> domainEventList;

    public AggregateRootRepository(Firestore db){
        this.db = db;
        domainEventList = new HashMap<>();
    }

    @Override
    public List<DomainEvent> getEventsBy(AggregateRootId aggregateRootId) {

        List<DomainEvent> domainEventListNew = null;

        ApiFuture<QuerySnapshot> future = db.collection(aggregateRootId.toString()).get();
        try {
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            documents.forEach(document -> {
                domainEventListNew.add(document.toObject(DomainEvent.class));
            });

            domainEventList.put(aggregateRootId.toString(), domainEventListNew);

            //domainEventList = (Map<String, List<DomainEvent>>) documents;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return (List<DomainEvent>) domainEventList;
    }

    @Override
    public void saveEventsWithAn(final AggregateRootId aggregateRootId, final List<DomainEvent> events) {

        events.forEach(domainEvent ->{
            try {
                WriteResult future = db
                        .collection(aggregateRootId.toString())
                        .document(domainEvent.uuid.toString())
                        .set(domainEvent).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });



     /*   events.forEach(domainEvent ->  db.collection("events")
                .document(aggregateRootId
                        .toString()).set(domainEvent));*/

        domainEventList.merge(aggregateRootId.toString(), events, (events1, events2) -> {
            events1.addAll(events2);
            return events1;
        });
    }

    public Map<String, List<DomainEvent>> getDomainEventList() {

        Iterable<CollectionReference> future = db.getCollections();

        return domainEventList;
    }
}
