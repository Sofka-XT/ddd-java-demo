package co.com.sofka.usecases;

import co.com.sofka.business.UseCase;
import co.com.sofka.core.issue.IssueList;
import co.com.sofka.core.issue.events.IssueWithBasicInformationCreated;
import co.com.sofka.core.issue.values.PersonProperty;
import co.com.sofka.domain.AggregateRootId;
import co.com.sofka.domain.DomainEvent;
import co.com.sofka.generic.values.BasicInformationProperty;
import co.com.sofka.generic.values.PeriodProperty;
import co.com.sofka.generic.values.StatusProperty;

import java.util.List;

public class IssueCreateAndUpdateUseCase extends UseCase<IssueCreateAndUpdateUseCase.Request, IssueCreateAndUpdateUseCase.Response> {

    @Override
    protected void executeUseCase(Request requestValues) {

        AggregateRootId anAggregateRootId = new AggregateRootId(requestValues.uuid);
        IssueList issueList = new IssueList(anAggregateRootId);

        issueList.createIssueWithBasicInformation(requestValues.basicInformation);

        var uncommittedChanges = issueList.getUncommittedChanges();
        var issueId = ((IssueWithBasicInformationCreated)uncommittedChanges.get(0)).getIssueId();
        issueList.updateFullIssueWithoutBasicInformation(issueId, requestValues.period,requestValues.person, requestValues.status);

        emit().onSuccess(new IssueCreateAndUpdateUseCase.Response(issueList.getUncommittedChanges()));
        issueList.markChangesAsCommitted();
    }

    public static class Request implements UseCase.RequestValues {
        private String uuid;
        private BasicInformationProperty basicInformation;
        private PeriodProperty period;
        private PersonProperty person;
        private StatusProperty status;

        public Request(String uuid, BasicInformationProperty basicInformation, PeriodProperty period, PersonProperty person, StatusProperty status) {
            this.uuid = uuid;
            this.basicInformation = basicInformation;
            this.period = period;
            this.person = person;
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
