package co.com.sofka.usecases.handlers.quieries;

import co.com.sofka.application.querys.QueryHandler;
import co.com.sofka.core.issue.quieries.QueryGetIssueListById;
import co.com.sofka.domain.generic.AggregateRootId;

import java.util.Collection;

public class QueryHandlerGetById implements QueryHandler<QueryGetIssueListById> {


    @Override
    public Collection<QueryGetIssueListById> findById(AggregateRootId id) {
        return null;
    }

    @Override
    public Collection<QueryGetIssueListById> findAll() {
        return null;
    }
}
