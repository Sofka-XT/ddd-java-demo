package co.com.sofka.usecases;

import co.com.sofka.business.UseCase;
import co.com.sofka.core.issue.IssueList;
import co.com.sofka.core.issue.events.IssueWithBasicInformationCreated;
import co.com.sofka.core.label.LabelList;
import co.com.sofka.core.label.values.LabelListId;
import co.com.sofka.domain.AggregateRootId;
import co.com.sofka.domain.DomainEvent;
import co.com.sofka.generic.values.BasicInformationProperty;

import java.util.List;

public class LabelAddToIssueUseCase extends UseCase<LabelAddToIssueUseCase.Request, LabelAddToIssueUseCase.Response> {

    @Override
    protected void executeUseCase(Request requestValues) {

        AggregateRootId anAggregateRootId = new AggregateRootId(requestValues.uuid);
        IssueList issueList = new IssueList(anAggregateRootId);

        issueList.createIssueWithBasicInformation(requestValues.basicInformation);

        var uncommittedChanges = issueList.getUncommittedChanges();
        var issueId = ((IssueWithBasicInformationCreated)uncommittedChanges.get(0)).getIssueId();
        LabelList labelList = new LabelList(requestValues.labelListId);
        labelList.createLabel(requestValues.color, requestValues.title);

        issueList.updateLabelListBy(issueId, labelList);

        emit().onSuccess(new LabelAddToIssueUseCase.Response(issueList.getUncommittedChanges()));
        issueList.markChangesAsCommitted();
    }

    public static class Request implements UseCase.RequestValues {
        private String uuid;
        private BasicInformationProperty basicInformation;
        private LabelListId labelListId;
        private String color;
        private String title;

        public Request(String uuid, BasicInformationProperty basicInformation, LabelListId labelListId, String color, String title) {
            this.uuid = uuid;
            this.basicInformation = basicInformation;
            this.labelListId = labelListId;
            this.color = color;
            this.title = title;
        }
    }

    public static class Response implements UseCase.ResponseEvents {

        private List<DomainEvent> domainEvents;

        public Response(List<DomainEvent> domainEvents) {
            this.domainEvents = domainEvents;
        }

        @Override
        public List<DomainEvent> getDomainEvents() {
            return domainEvents;
        }
    }
}
