package co.com.sofka.usecases;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.core.issue.IssueList;
import co.com.sofka.domain.generic.AggregateRootId;
import co.com.sofka.domain.generic.DomainEvent;


import java.util.List;

public class IssueDeleteUseCase extends UseCase<IssueDeleteUseCase.Request, IssueDeleteUseCase.Response> {

    @Override
    protected void executeUseCase(Request requestValues) {

        AggregateRootId anAggregateRootId = new AggregateRootId(requestValues.uuid);
        IssueList issueList = new IssueList(anAggregateRootId);
        //issueList.
        //issueList.deleteIssueBy();

    }

    public static class Request implements UseCase.RequestValues {
        private String uuid;

        public Request(final String uuid) {
            this.uuid = uuid;
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
