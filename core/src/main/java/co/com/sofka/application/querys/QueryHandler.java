package co.com.sofka.application.querys;

import co.com.sofka.core.issue.values.IssueId;

import java.util.Collection;

public interface QueryHandler<T> {

    T findById(IssueId id);

    Collection<T> findAll();
}
