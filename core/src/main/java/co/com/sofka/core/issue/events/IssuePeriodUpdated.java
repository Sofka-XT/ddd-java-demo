package co.com.sofka.core.issue.events;

import co.com.sofka.core.issue.values.IssueId;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.generic.values.PeriodProperty;

public class IssuePeriodUpdated extends DomainEvent {

    private final IssueId issueId;
    private final PeriodProperty period;

    public IssuePeriodUpdated(final IssueId issueId, final PeriodProperty period) {
        super("issue_period_updated");
        this.issueId = issueId;
        this.period = period;
    }

    public IssueId getIssueId() {
        return issueId;
    }

    public PeriodProperty getPeriod() {
        return period;
    }

}
