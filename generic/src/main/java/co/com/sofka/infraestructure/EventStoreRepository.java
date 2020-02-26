package co.com.sofka.infraestructure;

import co.com.sofka.domain.AggregateRootId;
import co.com.sofka.domain.DomainEvent;

import java.util.List;

public interface EventStoreRepository {

    List<DomainEvent> getEventsBy(AggregateRootId aggregateRootId) throws QueryFaultException;
    void saveEventsWithAn(AggregateRootId aggregateRootId, DomainEvent event);
}
