package co.com.sofka.infraestructure;

import co.com.sofka.domain.AggregateRootId;
import co.com.sofka.domain.DomainEvent;

import java.util.concurrent.Flow;

import static co.com.sofka.infraestructure.BdConnection.closeDatabase;
import static co.com.sofka.infraestructure.BdConnection.getDatabaseInstance;


public class SubscriberFirestoreRepository implements Flow.Subscriber<DomainEvent> {

    FirestoreRepository firestoreRepository;
    String aggregateRootId;

    public SubscriberFirestoreRepository(String aggregateRootId) {
        this.aggregateRootId = aggregateRootId;
        firestoreRepository = new FirestoreRepository(getDatabaseInstance());
    }
    @Override
    public void onSubscribe(Flow.Subscription subscription) {

    }

    @Override
    public void onNext(DomainEvent domainEvent) {
        firestoreRepository.saveEventsWithAn(new AggregateRootId(aggregateRootId), domainEvent);
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
