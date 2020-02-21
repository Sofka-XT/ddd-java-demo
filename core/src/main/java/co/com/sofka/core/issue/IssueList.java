package co.com.sofka.core.issue;

import co.com.sofka.core.issue.events.*;
import co.com.sofka.core.issue.values.IssueId;
import co.com.sofka.core.issue.values.PersonProperty;
import co.com.sofka.core.label.LabelList;

import co.com.sofka.generic.exeption.IssueException;
import co.com.sofka.generic.values.BasicInformationProperty;
import co.com.sofka.generic.values.PeriodProperty;
import co.com.sofka.generic.values.StatusProperty;

import co.com.sofka.domain.AggregateRoot;
import co.com.sofka.domain.AggregateRootId;
import co.com.sofka.domain.DomainEvent;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class IssueList extends AggregateRoot {

    private Collection<Issue> issueCollection;

    public IssueList(final AggregateRootId aggregateRootId) {
        super(aggregateRootId);
        issueCollection = new ArrayList<>();
        registerActions(createIssueWithBasicInformation, updatedIssueStatus,
                updatedIssuePeriod, updateFullIssueWithoutBasicInformation,
                updatedIssueBasicInformation, updatedIssuePerson,
                updatedIssueLabels, deleteIssueLabels);
    }

    public void createIssueWithBasicInformation(BasicInformationProperty basicInformation) {
        IssueId issueId = new IssueId(UUID.randomUUID().toString());
        appendChange(new IssueWithBasicInformationCreated(issueId,basicInformation))
                .apply(createIssueWithBasicInformation);
    }

    private final Consumer<IssueWithBasicInformationCreated> createIssueWithBasicInformation = event ->{
        var issue = new Issue(event.getIssueId(), event.getBasicInformation());
        issueCollection.add(issue);
    };

    public void updateFullIssueWithoutBasicInformation(IssueId issueId, PeriodProperty period,
                                                       PersonProperty person, StatusProperty status) {
        appendChange(new FullIssueWithoutBasicInformationUpdated(issueId, period, person, status))
                .apply(updateFullIssueWithoutBasicInformation);
    }

    private final Consumer<FullIssueWithoutBasicInformationUpdated> updateFullIssueWithoutBasicInformation = event ->{
        var issue = selectIssueBy(event.getIssueId());
        issue.updatePerson(event.getPerson());
        issue.updatePeriod(event.getPeriod());
        issue.updateStatus(event.getStatus());
    };

    public void updateIssueBasicInformationBy(IssueId issueId, BasicInformationProperty basicInformation) {
        appendChange(new IssueBasicInformationUpdated(issueId, basicInformation))
            .apply(updatedIssueBasicInformation);
    }

    private final Consumer<IssueBasicInformationUpdated> updatedIssueBasicInformation = event ->{
        var issue = selectIssueBy(event.getIssueId());
        issue.updateBasicInformation(event.getBasicInformation());
    };

    public void updateIssuePersonBy(IssueId issueId, PersonProperty person) {
        appendChange(new IssuePersonUpdated(issueId, person))
                .apply(updatedIssuePerson);
    }

    private final Consumer<IssuePersonUpdated> updatedIssuePerson = event ->{
        var issue = selectIssueBy(event.getIssueId());
        issue.updatePerson(event.getPerson());
    };

    public void updateLabelListBy(IssueId issueId, LabelList labelList) {
        appendChange(new IssueLabelUpdated(issueId, labelList))
                .apply(updatedIssueLabels);
    }

    public void updateIssuePeriodBy(IssueId issueId, PeriodProperty period) {
        appendChange(new IssuePeriodUpdated(issueId, period))
                .apply(updatedIssuePeriod);
    }

    private final Consumer<IssuePeriodUpdated> updatedIssuePeriod = event ->{
        var issue = selectIssueBy(event.getIssueId());
        issue.updatePeriod(event.getPeriod());
    };

    public void updateIssueStatusBy(IssueId issueId, StatusProperty status) {
        appendChange(new IssueStatusUpdated(issueId, status))
                .apply(updatedIssueStatus);
    }

    private final Consumer<IssueStatusUpdated> updatedIssueStatus = event ->{
        var issue = selectIssueBy(event.getIssueId());
        issue.updateStatus(event.getStatusProperty());
    };

    public void deleteLabelsBy(IssueId issueId){
       appendChange(new IssueLabelsDeleted(issueId))
               .apply(deleteIssueLabels);

    }

    private final Consumer<IssueLabelsDeleted> deleteIssueLabels = event ->{
        var issue = selectIssueBy(event.getIssueId());
        issue.deleteLabelList();
    };

    private final Consumer<IssueLabelUpdated> updatedIssueLabels = event ->{
        var issue = selectIssueBy(event.getIssueId());
        issue.updateLabelList(event.getLabelList());
    };

    public void deleteIssueBy(IssueId issueId) {
        appendChange(new IssueDeleted(issueId))
                .apply(deleteIssue);

    }

    private final Consumer<IssueDeleted> deleteIssue = event ->{
        var issue = selectIssueBy(event.getIssueId());
        issueCollection.remove(issue);
    };

    public Collection<Issue> getAllIssues(){
        return this.issueCollection;
    }

    private Issue selectIssueBy(IssueId issueId) {
        return getAllIssues()
                .stream()
                .filter(issue -> issue.issueId().equals(issueId))
                .findFirst()
                .orElseThrow(() -> new IssueException("No existe el issue"));
    }

    public static IssueList from(AggregateRootId id, List<DomainEvent> history) {
        IssueList entity = new IssueList(id);
        history.forEach(entity::applyEvent);
        return entity;
    }


}
