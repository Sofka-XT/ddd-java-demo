package co.com.sofka.core.issue;


import co.com.sofka.core.issue.events.IssueWithBasicInformationCreated;
import co.com.sofka.core.issue.events.FullIssueWithoutBasicInformationUpdated;
import co.com.sofka.core.issue.events.IssueBasicInformationUpdated;
import co.com.sofka.core.issue.events.IssuePersonUpdated;
import co.com.sofka.core.issue.events.IssuePeriodUpdated;
import co.com.sofka.core.issue.events.IssueStatusUpdated;
import co.com.sofka.core.issue.events.IssueLabelsDeleted;
import co.com.sofka.core.issue.events.IssueLabelUpdated;
import co.com.sofka.core.issue.events.IssueDeleted;

import co.com.sofka.core.issue.values.IssueId;
import co.com.sofka.core.issue.values.IssueListId;
import co.com.sofka.core.issue.values.PersonProperty;
import co.com.sofka.core.label.LabelList;
import co.com.sofka.domain.generic.AggregateEvent;
import co.com.sofka.domain.generic.AggregateRoot;
import co.com.sofka.domain.generic.AggregateRootId;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.generic.exceptions.IssueException;
import co.com.sofka.generic.values.BasicInformationProperty;
import co.com.sofka.generic.values.PeriodProperty;
import co.com.sofka.generic.values.StatusProperty;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.Objects;
import java.util.function.Consumer;

public class IssueList extends AggregateEvent<IssueListId> {

    protected Collection<Issue> issueCollection;


    public IssueList(final IssueListId issueListId) {
        super(issueListId);
        issueCollection = new ArrayList<>();
        registerEntityBehavior(new IssueBehaviors(this));
    }

    public static IssueList from(final IssueListId id, final List<DomainEvent> history) {
        IssueList entity = new IssueList(id);
        history.forEach(entity::applyEvent);
        return entity;
    }

    public void createIssueWithBasicInformation(final BasicInformationProperty basicInformation) {
        IssueId issueId = new IssueId(UUID.randomUUID().toString());
        appendChange(new IssueWithBasicInformationCreated(this.entityId, issueId, basicInformation))
                .apply();
    }

    public void updateFullIssueWithoutBasicInformation(final IssueId issueId,
                                                       final PeriodProperty period, final PersonProperty person,
                                                       final StatusProperty status) {
        appendChange(new FullIssueWithoutBasicInformationUpdated(this.entityId, issueId, period, person, status))
                .apply();
    }

    public void updateIssueBasicInformationBy(final IssueId issueId,
                                              final BasicInformationProperty basicInformation) {
        appendChange(new IssueBasicInformationUpdated(this.entityId, issueId, basicInformation))
                .apply();
    }

    public void updateIssuePersonBy(final IssueId issueId,
                                    final PersonProperty person) {
        appendChange(new IssuePersonUpdated(this.entityId, issueId, person))
                .apply();
    }

    public void updateLabelListBy(final IssueId issueId,
                                  final LabelList labelList) {
        appendChange(new IssueLabelUpdated(this.entityId, issueId, labelList))
                .apply();
    }

    public void updateIssuePeriodBy(final IssueId issueId,
                                    final PeriodProperty period) {
        appendChange(new IssuePeriodUpdated(this.entityId, issueId, period))
                .apply();
    }

    public void updateIssueStatusBy(final IssueId issueId,
                                    final StatusProperty status) {
        appendChange(new IssueStatusUpdated(this.entityId, issueId, status))
                .apply();
    }

    public void deleteLabelsBy(final IssueId issueId) {
        appendChange(new IssueLabelsDeleted(this.entityId, issueId))
                .apply();
    }

    public void deleteIssueBy(final IssueId issueId) {
        appendChange(new IssueDeleted(this.entityId, issueId))
                .apply();
    }

    public Collection<Issue> getAllIssues() {
        return this.issueCollection;
    }
}
