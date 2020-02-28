package co.com.sofka.infraestructure;



import co.com.sofka.domain.generic.AggregateRootId;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.bus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Flow;

import static co.com.sofka.infraestructure.BdConnection.closeDatabase;


public class SubscriberFirestore implements Flow.Subscriber<DomainEvent> {

    private static final Logger logger = LoggerFactory.getLogger(SubscriberFirestore.class);

    private FirestoreRepository firestoreRepository;
    private String aggregateRootId;
    private Set<DomainEvent> domainEvents;
    private EventBus eventBus;

    public SubscriberFirestore(String aggregateRootId, FirestoreRepository firestoreRepository, EventBus eventBus) {
        this.aggregateRootId = aggregateRootId;
        this.firestoreRepository = firestoreRepository;
        this.eventBus = eventBus;
        this.domainEvents = new HashSet<>();
        List s = new LinkedList();
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {

    }

    @Override
    public void onNext(DomainEvent domainEvent) {
        eventBus.publish(domainEvent);
        firestoreRepository.saveEvent(new AggregateRootId(aggregateRootId), domainEvent);
        domainEvents.add(domainEvent);
    }

    @Override
    public void onError(Throwable throwable) {
        closeDatabase();
    }

    @Override
    public void onComplete() {
        logger.info(domainEvents.size()+" events saved and published");
    }
}
