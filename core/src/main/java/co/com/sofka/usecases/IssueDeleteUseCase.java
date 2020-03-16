package co.com.sofka.usecases;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.core.issue.IssueList;
import co.com.sofka.core.issue.values.IssueId;
import co.com.sofka.core.issue.values.IssueListId;
import co.com.sofka.domain.generic.AggregateRootId;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.repository.EventStoreRepository;
import co.com.sofka.infraestructure.repository.QueryFaultException;


import java.util.List;

public class IssueDeleteUseCase extends UseCase<IssueDeleteUseCase.Request, ResponseEvents> {

    EventStoreRepository<IssueListId> repository;

    public IssueDeleteUseCase(EventStoreRepository<IssueListId> repository) {
        this.repository = repository;
    }

    @Override
    protected void executeUseCase(Request requestValues) {

        IssueListId issueListId = requestValues.issueListId;
        try {
            IssueList issueList = IssueList.from(issueListId, repository.getEventsBy(issueListId));
            issueList.deleteIssueBy(requestValues.issueId);
            emit().onSuccess(new ResponseEvents(issueList.getUncommittedChanges()));
            issueList.markChangesAsCommitted();
        } catch (QueryFaultException e) {
            emit().onError(new RuntimeException(e.getCause()));
        }

    }

    public static class Request implements UseCase.RequestValues {
        private IssueListId issueListId;
        private IssueId issueId;

        public Request(IssueListId issueListId, IssueId issueId) {
            this.issueListId = issueListId;
            this.issueId = issueId;
        }
    }


}
