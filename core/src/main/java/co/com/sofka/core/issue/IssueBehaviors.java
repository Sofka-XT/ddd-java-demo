package co.com.sofka.core.issue;

import co.com.sofka.core.issue.events.*;
import co.com.sofka.core.issue.values.IssueId;
import co.com.sofka.domain.generic.AggregateEvent;
import co.com.sofka.generic.exceptions.IssueException;


public class IssueBehaviors extends AggregateEvent.EventBehaviors<IssueList> {
    {
       add((IssueWithBasicInformationCreated event) -> {
           var issue = new Issue(event.getIssueId(), event.getBasicInformation());
           entity.issueCollection.add(issue);
       });

       add((FullIssueWithoutBasicInformationUpdated event) -> {
           var issue = selectIssueBy(event.getIssueId());
           issue.updatePerson(event.getPerson());
           issue.updatePeriod(event.getPeriod());
           issue.updateStatus(event.getStatus());
       });
       add((IssueBasicInformationUpdated event) -> {
           var issue = selectIssueBy(event.getIssueId());
           issue.updateBasicInformation(event.getBasicInformation());
       });

       add((IssuePersonUpdated event) -> {
           var issue = selectIssueBy(event.getIssueId());
           issue.updatePerson(event.getPerson());
       });

       add((IssuePeriodUpdated event)->{
           var issue = selectIssueBy(event.getIssueId());
           issue.updatePeriod(event.getPeriod());
       });

       add((IssueStatusUpdated event)->{
           var issue = selectIssueBy(event.getIssueId());
           issue.updateStatus(event.getStatusProperty());
       });

       add((IssueLabelsDeleted event)->{
           var issue = selectIssueBy(event.getIssueId());
           issue.deleteLabelList();
       });

       add((IssueLabelUpdated event)->{
           var issue = selectIssueBy(event.getIssueId());
           issue.updateLabelList(event.getLabelList());
       });

        add((IssueDeleted event) -> {
            var issue = selectIssueBy(event.getIssueId());
            entity.issueCollection.remove(issue);
        });

    }

    private Issue selectIssueBy(final IssueId issueId) {
        return entity.issueCollection
                .stream()
                .filter(issue -> issue.issueId().equals(issueId))
                .findFirst()
                .orElseThrow(() -> new IssueException("the issue with id ".concat(issueId.getUuid()).concat(" doesn't exist")));
    }

    protected IssueBehaviors(IssueList entity) {
        super(entity);
    }
}
