package co.com.sofka.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AggregateRoot<T extends  AggregateRootId> extends Entity<T> {
    private final List<DomainEvent> changes;
    private final List<Consumer<? super DomainEvent>> handleActions;

    public AggregateRoot(T entityId) {
        super(entityId);
        this.changes = new LinkedList<>();
        this.handleActions = new LinkedList<>();
    }

    public List<DomainEvent> getUncommittedChanges(){
        return changes;
    }

    protected Function<Consumer<? extends DomainEvent>, AggregateRoot> appendChange(DomainEvent event){
        changes.add(event);
        return action -> {
            ((Consumer)action).accept(event);
            long version = currentVersionOf(event.type);
            event.nextVersionType(version);
            return this;
        };
    }

    private long currentVersionOf(String eventType){
        return changes.stream().filter(event -> event.type.equals(eventType)).count();
    }

    @SafeVarargs
    protected final void registerActions(Consumer<? extends DomainEvent> ...actions){
        for (Consumer<? extends DomainEvent> consumer: actions){
            handleActions.add((Consumer<? super DomainEvent>) consumer);
        }
    }

    protected void applyEvent(DomainEvent domainEvent){
        handleActions.forEach(consumer -> {
            try {
                consumer.accept(domainEvent);
            }catch (ClassCastException e){ }
        });
    }

    public void markChangesAsCommitted(){
        changes.clear();
    }

}
