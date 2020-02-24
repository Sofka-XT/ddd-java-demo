package co.com.sofka.generic;

import co.com.sofka.domain.AggregateRootId;
import co.com.sofka.domain.DomainEvent;
import co.com.sofka.infraestructure.EventStoreRepository;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
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
        return domainEventList.get(aggregateRootId.toString());
    }

    @Override
    public void saveEventsWithAn(final AggregateRootId aggregateRootId, final List<DomainEvent> events) {

        Map<String, List<DomainEvent>> docData = new HashMap<>();
        docData.put("event", events);

        events.forEach(domainEvent ->{
            ApiFuture<WriteResult> future = db
                    .collection(aggregateRootId.toString())
                    .document(domainEvent.uuid.toString())
                    .set(domainEvent);

            try {
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });



     /*   events.forEach(domainEvent ->  db.collection("events")
                .document(aggregateRootId
                        .toString()).set(domainEvent));*/

        ;
/*        domainEventList.merge(aggregateRootId.toString(), events, (events1, events2) -> {
            events1.addAll(events2);
            return events1;
        });*/
    }

    public Map<String, List<DomainEvent>> getDomainEventList() {
        return domainEventList;
    }
}
