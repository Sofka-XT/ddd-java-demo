package co.com.sofka.infrastructure;

import co.com.sofka.domain.generic.AggregateRootId;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.bus.ErrorEvent;
import co.com.sofka.infraestructure.bus.EventBus;
import co.com.sofka.infraestructure.repository.EventStoreRepository;
import co.com.sofka.infraestructure.store.StoredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Flow;


public class SubscriberFirestore<T extends AggregateRootId> implements Flow.Subscriber<DomainEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriberFirestore.class);

    private EventStoreRepository<T> repository;
    private Set<DomainEvent> domainEvents;
    private Flow.Subscription subscription;
    private EventBus eventBus;

    public SubscriberFirestore( final EventStoreRepository<T> repository,
                                final EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
        this.domainEvents = new HashSet<>();
    }

    @Override
    public void onSubscribe(final Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(final DomainEvent domainEvent) {
        Optional.of(repository).ifPresent(repo -> {
            StoredEvent storedEvent = StoredEvent.wrapEvent(domainEvent);
            repo.saveEvent((T) domainEvent.aggregateRootId, storedEvent);
        });
        Optional.of(eventBus).ifPresent(bus -> bus.publish(domainEvent));
        subscription.request(1);
        domainEvents.add(domainEvent);
    }



    @Override
    public void onError(final Throwable throwable) {
        Optional.of(eventBus).ifPresent(bus -> bus.publishError(new ErrorEvent(
                504, "There is a problem saving the event",
                throwable.getMessage())
        ));
        subscription.cancel();
    }

    @Override
    public void onComplete() {
        String message = domainEvents.size() + " events saved and published";
        LOGGER.info(message);
    }
}
