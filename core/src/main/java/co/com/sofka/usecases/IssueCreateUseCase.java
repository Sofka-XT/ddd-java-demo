package co.com.sofka.usecases;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.core.issue.IssueList;
import co.com.sofka.domain.generic.AggregateRootId;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.generic.values.BasicInformationProperty;

import java.util.List;

public class IssueCreateUseCase extends UseCase<IssueCreateUseCase.Request, IssueCreateUseCase.Response> {

    @Override
    protected void executeUseCase(final Request requestValues) {

        AggregateRootId anAggregateRootId = new AggregateRootId(requestValues.uuid);
        IssueList issueList = new IssueList(anAggregateRootId);

        issueList.createIssueWithBasicInformation(requestValues.basicInformation);
        emit().onSuccess(new Response(issueList.getUncommittedChanges()));
        issueList.markChangesAsCommitted();

    }

    public static class Request implements UseCase.RequestValues {
        private String uuid;
        private BasicInformationProperty basicInformation;

        public Request(final String uuid, final BasicInformationProperty basicInformation) {
            this.uuid = uuid;
            this.basicInformation = basicInformation;
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
