package co.com.sofka.infraestructure;



import co.com.sofka.domain.generic.AggregateRootId;
import co.com.sofka.domain.generic.DomainEvent;

import java.util.concurrent.Flow;

import static co.com.sofka.infraestructure.BdConnection.closeDatabase;


public class SubscriberFirestore implements Flow.Subscriber<DomainEvent> {

    private FirestoreRepository firestoreRepository;
    private String aggregateRootId;

    public SubscriberFirestore(String aggregateRootId, FirestoreRepository firestoreRepository) {
        this.aggregateRootId = aggregateRootId;
        this.firestoreRepository = firestoreRepository;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {

    }

    @Override
    public void onNext(DomainEvent domainEvent) {
        firestoreRepository.saveEvent(new AggregateRootId(aggregateRootId), domainEvent);
    }

    @Override
    public void onError(Throwable throwable) {
        closeDatabase();
    }

    @Override
    public void onComplete() {
        closeDatabase();
    }
}
