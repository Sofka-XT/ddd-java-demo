package co.com.sofka.usecases.handlers.quieries;

import co.com.sofka.application.querys.QueryHandler;
import co.com.sofka.core.issue.values.IssueId;
import co.com.sofka.infrastructure.IssueEntity;
import co.com.sofka.infrastructure.IssueEntityRepository;

import java.util.Collection;

public class QueryHandlerImpl implements QueryHandler<IssueEntity> {

    private IssueEntityRepository repository;

    public QueryHandlerImpl(final IssueEntityRepository repository) {
        this.repository = repository;
    }


    @Override
    public IssueEntity findById(final IssueId id) {
        return repository.findById(id)
                .orElseThrow(() -> new QueryHandlerException("Issue with id "
                        .concat(id.getUuid())
                        .concat(" no exist in the database.")));
    }

    @Override
    public Collection<IssueEntity> findAll() {
        return repository.findAll();
    }
}
