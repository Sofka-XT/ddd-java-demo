package co.com.sofka.generic;

import co.com.sofka.domain.AggregateRootId;
import co.com.sofka.domain.DomainEvent;
import co.com.sofka.infraestructure.EventStoreRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AggregateRootRepository implements EventStoreRepository {

    private Map<String, List<DomainEvent>> domainEventList;

    public AggregateRootRepository(){
        domainEventList = new HashMap<>();
    }

    @Override
    public List<DomainEvent> getEventsBy(AggregateRootId aggregateRootId) {
        return domainEventList.get(aggregateRootId.toString());
    }

    @Override
    public void saveEventsWithAn(final AggregateRootId aggregateRootId, final List<DomainEvent> events) {
        domainEventList.merge(aggregateRootId.toString(), events, (events1, events2) -> {
            events1.addAll(events2);
            return events1;
        });
    }

    public Map<String, List<DomainEvent>> getDomainEventList() {
        return domainEventList;
    }
}
