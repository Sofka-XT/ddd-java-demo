package co.com.sofka.core.issue.events;

import co.com.sofka.core.issue.values.IssueId;
import co.com.sofka.core.issue.values.IssueListId;
import co.com.sofka.core.label.LabelList;
import co.com.sofka.domain.generic.DomainEvent;

public class IssueLabelUpdated extends DomainEvent {

    private final IssueId issueId;
    private final LabelList labelList;

    public IssueLabelUpdated(final IssueListId issueListId, final IssueId issueId, final LabelList labelList) {
        super("issue_label_updated", issueListId);
        this.issueId = issueId;
        this.labelList = labelList;
    }

    public IssueId getIssueId() {
        return issueId;
    }

    public LabelList getLabelList() {
        return labelList;
    }


}
