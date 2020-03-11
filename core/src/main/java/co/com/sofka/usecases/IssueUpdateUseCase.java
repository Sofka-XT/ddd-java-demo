package co.com.sofka.usecases;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.core.issue.IssueList;
import co.com.sofka.core.issue.values.IssueId;
import co.com.sofka.core.issue.values.IssueListId;
import co.com.sofka.generic.values.StatusProperty;
import co.com.sofka.infraestructure.repository.EventStoreRepository;
import co.com.sofka.infraestructure.repository.QueryFaultException;

public class IssueUpdateUseCase extends UseCase<IssueUpdateUseCase.Request, ResponseEvents> {

    EventStoreRepository<IssueListId> repository;

    public IssueUpdateUseCase(EventStoreRepository<IssueListId> repository) {
        this.repository = repository;
    }

    @Override
    protected void executeUseCase(final Request requestValues) {
        IssueListId issueListId = requestValues.issueListId;
        try {
            IssueList issueList = IssueList.from(issueListId, repository.getEventsBy(issueListId));
            issueList.updateIssueStatusBy(requestValues.issueId, requestValues.status);
            emit().onSuccess(new ResponseEvents(issueList.getUncommittedChanges()));
            issueList.markChangesAsCommitted();
        } catch (QueryFaultException e) {
            emit().onError(new RuntimeException(e.getCause()));
        }
    }

    public static class Request implements UseCase.RequestValues {
        private IssueListId issueListId;
        private IssueId issueId;
        private StatusProperty status;

        public Request(final IssueListId issueListId,
                       final IssueId issueId,
                       final StatusProperty status) {

            this.issueListId = issueListId;
            this.issueId = issueId;
            this.status = status;
        }
    }



}
