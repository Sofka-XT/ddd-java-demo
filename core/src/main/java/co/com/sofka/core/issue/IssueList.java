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
import co.com.sofka.core.issue.values.PersonProperty;
import co.com.sofka.core.label.LabelList;
import co.com.sofka.domain.generic.AggregateRoot;
import co.com.sofka.domain.generic.AggregateRootId;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.generic.exeption.IssueException;
import co.com.sofka.generic.values.BasicInformationProperty;
import co.com.sofka.generic.values.PeriodProperty;
import co.com.sofka.generic.values.StatusProperty;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.Objects;
import java.util.function.Consumer;

public class IssueList extends AggregateRoot<AggregateRootId> {

    private Collection<Issue> issueCollection;
    private final Consumer<IssueWithBasicInformationCreated> createIssueWithBasicInformation = event -> {
        var issue = new Issue(event.getIssueId(), event.getBasicInformation());
        issueCollection.add(issue);
    };
    private final Consumer<FullIssueWithoutBasicInformationUpdated> updateFullIssueWithoutBasicInformation = event -> {
        var issue = selectIssueBy(event.getIssueId());
        issue.updatePerson(event.getPerson());
        issue.updatePeriod(event.getPeriod());
        issue.updateStatus(event.getStatus());
    };
    private final Consumer<IssueBasicInformationUpdated> updatedIssueBasicInformation = event -> {
        var issue = selectIssueBy(event.getIssueId());
        issue.updateBasicInformation(event.getBasicInformation());
    };
    private final Consumer<IssuePersonUpdated> updatedIssuePerson = event -> {
        var issue = selectIssueBy(event.getIssueId());
        issue.updatePerson(event.getPerson());
    };
    private final Consumer<IssuePeriodUpdated> updatedIssuePeriod = event -> {
        var issue = selectIssueBy(event.getIssueId());
        issue.updatePeriod(event.getPeriod());
    };
    private final Consumer<IssueStatusUpdated> updatedIssueStatus = event -> {
        var issue = selectIssueBy(event.getIssueId());
        issue.updateStatus(event.getStatusProperty());
    };
    private final Consumer<IssueLabelsDeleted> deleteIssueLabels = event -> {
        var issue = selectIssueBy(event.getIssueId());
        issue.deleteLabelList();
    };
    private final Consumer<IssueLabelUpdated> updatedIssueLabels = event -> {
        var issue = selectIssueBy(event.getIssueId());
        issue.updateLabelList(event.getLabelList());
    };
    private final Consumer<IssueDeleted> deleteIssue = event -> {
        var issue = selectIssueBy(event.getIssueId());
        issueCollection.remove(issue);
    };

    public IssueList(final AggregateRootId aggregateRootId) {
        super(aggregateRootId);
        issueCollection = new ArrayList<>();
        registerActions(createIssueWithBasicInformation, updatedIssueStatus,
                updatedIssuePeriod, updateFullIssueWithoutBasicInformation,
                updatedIssueBasicInformation, updatedIssuePerson,
                updatedIssueLabels, deleteIssueLabels);
    }

    public static IssueList from(final AggregateRootId id, final List<DomainEvent> history) {
        IssueList entity = new IssueList(id);
        history.forEach(entity::applyEvent);
        return entity;
    }

    public void createIssueWithBasicInformation(final BasicInformationProperty basicInformation) {
        IssueId issueId = new IssueId(UUID.randomUUID().toString());
        appendChange(new IssueWithBasicInformationCreated(issueId, basicInformation))
                .apply(createIssueWithBasicInformation);
    }

    public void updateFullIssueWithoutBasicInformation(final IssueId issueId, final PeriodProperty period,
                                                       final PersonProperty person, final StatusProperty status) {
        appendChange(new FullIssueWithoutBasicInformationUpdated(issueId, period, person, status))
                .apply(updateFullIssueWithoutBasicInformation);
    }

    public void updateIssueBasicInformationBy(final IssueId issueId, final BasicInformationProperty basicInformation) {
        appendChange(new IssueBasicInformationUpdated(issueId, basicInformation))
                .apply(updatedIssueBasicInformation);
    }

    public void updateIssuePersonBy(final IssueId issueId, final PersonProperty person) {
        appendChange(new IssuePersonUpdated(issueId, person))
                .apply(updatedIssuePerson);
    }

    public void updateLabelListBy(final IssueId issueId, final LabelList labelList) {
        appendChange(new IssueLabelUpdated(issueId, labelList))
                .apply(updatedIssueLabels);
    }

    public void updateIssuePeriodBy(final IssueId issueId, final PeriodProperty period) {
        appendChange(new IssuePeriodUpdated(issueId, period))
                .apply(updatedIssuePeriod);
    }

    public void updateIssueStatusBy(final IssueId issueId, final StatusProperty status) {
        appendChange(new IssueStatusUpdated(issueId, status))
                .apply(updatedIssueStatus);
    }

    public void deleteLabelsBy(final IssueId issueId) {
        appendChange(new IssueLabelsDeleted(issueId))
                .apply(deleteIssueLabels);

    }

    public void deleteIssueBy(final IssueId issueId) {
        appendChange(new IssueDeleted(issueId))
                .apply(deleteIssue);

    }

    public Collection<Issue> getAllIssues() {
        return this.issueCollection;
    }

    private Issue selectIssueBy(final IssueId issueId) {
        return getAllIssues()
                .stream()
                .filter(issue -> issue.issueId().equals(issueId))
                .findFirst()
                .orElseThrow(() -> new IssueException("No existe el issue"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        IssueList issueList = (IssueList) o;
        return Objects.equals(issueCollection, issueList.issueCollection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), issueCollection,
                createIssueWithBasicInformation, updateFullIssueWithoutBasicInformation,
                updatedIssueBasicInformation, updatedIssuePerson, updatedIssuePeriod,
                updatedIssueStatus, deleteIssueLabels, updatedIssueLabels, deleteIssue);
    }
}
