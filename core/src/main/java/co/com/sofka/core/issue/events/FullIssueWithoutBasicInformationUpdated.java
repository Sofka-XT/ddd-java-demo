package co.com.sofka.core.issue.events;

import co.com.sofka.core.issue.values.IssueId;
import co.com.sofka.core.issue.values.IssueListId;
import co.com.sofka.core.issue.values.PersonProperty;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.generic.values.PeriodProperty;
import co.com.sofka.generic.values.StatusProperty;

public class FullIssueWithoutBasicInformationUpdated extends DomainEvent {

    private final IssueId issueId;
    private final PeriodProperty period;
    private final PersonProperty person;
    private final StatusProperty status;

    public FullIssueWithoutBasicInformationUpdated(final IssueListId issueListId,
                                                   final IssueId issueId,
                                                   final PeriodProperty period,
                                                   final PersonProperty person,
                                                   final StatusProperty status) {
        super("full_issue_without_basic_information_updated", issueListId);
        this.issueId = issueId;
        this.period = period;
        this.person = person;
        this.status = status;
    }

    public IssueId getIssueId() {
        return issueId;
    }

    public PeriodProperty getPeriod() {
        return period;
    }

    public PersonProperty getPerson() {
        return person;
    }

    public StatusProperty getStatus() {
        return status;
    }

}
