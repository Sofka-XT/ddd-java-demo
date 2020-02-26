package co.com.sofka.usecases;

import co.com.sofka.business.UseCase;
import co.com.sofka.core.issue.IssueList;
import co.com.sofka.core.issue.events.IssueWithBasicInformationCreated;
import co.com.sofka.domain.AggregateRootId;
import co.com.sofka.domain.DomainEvent;
import co.com.sofka.generic.values.BasicInformationProperty;
import co.com.sofka.generic.values.StatusProperty;

import java.util.List;

public class IssueUpdateUseCase extends UseCase<IssueUpdateUseCase.Request, IssueUpdateUseCase.Response> {

    @Override
    protected void executeUseCase(Request requestValues) {

        AggregateRootId anAggregateRootId = new AggregateRootId(requestValues.uuid);
        IssueList issueList = new IssueList(anAggregateRootId);

        issueList.createIssueWithBasicInformation(requestValues.basicInformation);

        var uncommittedChanges = issueList.getUncommittedChanges();
        var issueId = ((IssueWithBasicInformationCreated)uncommittedChanges.get(0)).getIssueId();
        issueList.updateIssueStatusBy(issueId, requestValues.status);

        emit().onSuccess(new IssueUpdateUseCase.Response(issueList.getUncommittedChanges()));
        issueList.markChangesAsCommitted();
    }

    public static class Request implements UseCase.RequestValues {
        private String uuid;
        private BasicInformationProperty basicInformation;
        private StatusProperty status;

        public Request(String uuid, BasicInformationProperty basicInformation, StatusProperty status) {
            this.uuid = uuid;
            this.basicInformation = basicInformation;
            this.status = status;
        }
    }

    public static class Response implements UseCase.ResponseEvents {

        private List<DomainEvent> domainEvents;

        public Response(List<DomainEvent> domainEvents) {
            this.domainEvents = List.copyOf(domainEvents);
        }

        @Override
        public List<DomainEvent> getDomainEvents() {
            return domainEvents;
        }
    }

}
