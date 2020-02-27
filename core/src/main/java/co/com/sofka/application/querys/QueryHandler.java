package co.com.sofka.application.querys;

import co.com.sofka.domain.generic.AggregateRootId;

import java.util.Collection;

public interface QueryHandler<T> {

    Collection<T> findById(AggregateRootId id);

    Collection<T> findAll();
}
