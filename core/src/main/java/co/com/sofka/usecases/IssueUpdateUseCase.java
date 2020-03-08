package co.com.sofka.usecases;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.core.issue.IssueList;
import co.com.sofka.core.issue.events.IssueWithBasicInformationCreated;
import co.com.sofka.domain.generic.AggregateRootId;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.generic.values.BasicInformationProperty;
import co.com.sofka.generic.values.StatusProperty;

import java.util.List;

public class IssueUpdateUseCase extends UseCase<IssueUpdateUseCase.Request, IssueUpdateUseCase.Response> {

    @Override
    protected void executeUseCase(final Request requestValues) {

        AggregateRootId anAggregateRootId = new AggregateRootId(requestValues.uuid);
        IssueList issueList = new IssueList(anAggregateRootId);

        issueList.createIssueWithBasicInformation(requestValues.basicInformation);

        var uncommittedChanges = issueList.getUncommittedChanges();
        var issueId = ((IssueWithBasicInformationCreated) uncommittedChanges.get(0)).getIssueId();
        issueList.updateIssueStatusBy(issueId, requestValues.status);

        emit().onSuccess(new IssueUpdateUseCase.Response(issueList.getUncommittedChanges()));
        issueList.markChangesAsCommitted();
    }

    public static class Request implements UseCase.RequestValues {
        private String uuid;
        private BasicInformationProperty basicInformation;
        private StatusProperty status;

        public Request(final String uuid,
                       final BasicInformationProperty basicInformation,
                       final StatusProperty status) {

            this.uuid = uuid;
            this.basicInformation = basicInformation;
            this.status = status;
        }
    }

    public static class Response implements UseCase.PubEvents {

        private List<DomainEvent> domainEvents;

        public Response(final List<DomainEvent> domainEvents) {
            this.domainEvents = List.copyOf(domainEvents);
        }

        @Override
        public List<DomainEvent> getDomainEvents() {
            return domainEvents;
        }
    }

}
