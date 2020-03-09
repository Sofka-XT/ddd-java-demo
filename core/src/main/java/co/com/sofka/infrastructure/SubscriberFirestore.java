package co.com.sofka.infrastructure;



import co.com.sofka.domain.generic.AggregateRootId;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.bus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Flow;


public class SubscriberFirestore implements Flow.Subscriber<DomainEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriberFirestore.class);

    private FirestoreRepository firestoreRepository;
    private String aggregateRootId;
    private Set<DomainEvent> domainEvents;
    private EventBus eventBus;

    public SubscriberFirestore(final String aggregateRootId,
                               final FirestoreRepository firestoreRepository,
                               final EventBus eventBus) {
        this.aggregateRootId = aggregateRootId;
        this.firestoreRepository = firestoreRepository;
        this.eventBus = eventBus;
        this.domainEvents = new HashSet<>();
    }

    @Override
    public void onSubscribe(final Flow.Subscription subscription) {
        LOGGER.info("onSubscribe");
    }

    @Override
    public void onNext(final DomainEvent domainEvent) {
        eventBus.publish(domainEvent);
        firestoreRepository.saveEvent(new AggregateRootId(aggregateRootId), domainEvent);
        domainEvents.add(domainEvent);
    }

    @Override
    public void onError(final Throwable throwable) {
        BdConnection.closeDatabase();
    }

    @Override
    public void onComplete() {
        String message = domainEvents.size() + " events saved and published";
        LOGGER.info(message);
    }
}
