package co.com.sofka.core.issue;

import co.com.sofka.core.issue.values.IssueId;
import co.com.sofka.core.issue.values.PersonProperty;
import co.com.sofka.core.label.LabelList;
import co.com.sofka.generic.values.BasicInformationProperty;
import co.com.sofka.generic.values.PeriodProperty;
import co.com.sofka.generic.values.StatusProperty;

import java.util.Objects;

import static co.com.sofka.generic.DomainAssertions.checkNonNullForIssue;

public class Issue {

    private final IssueId issueId;
    private BasicInformationProperty basicInformation;
    private PersonProperty person;
    private StatusProperty status;
    private PeriodProperty period;
    private LabelList labelList;

    public Issue(final IssueId issueId, final BasicInformationProperty basicInformation) {
        checkNonNullForIssue(issueId, "Issue id can't be null");
        checkNonNullForIssue(basicInformation, "Basic information can't be null");
        this.issueId = issueId;
        this.basicInformation = basicInformation;
    }

    public PersonProperty person() {
        return person;
    }

    public StatusProperty status() {
        return status;
    }

    public PeriodProperty period() {
        return period;
    }

    public LabelList labelList() {
        return labelList;
    }

    public IssueId issueId() {
        return issueId;
    }

    public BasicInformationProperty basicInformation() {
        return basicInformation;
    }

    public void updatePerson(final PersonProperty person) {
        checkNonNullForIssue(person, "Person can't be null");
        this.person = person;
    }

    public void updateStatus(final StatusProperty status) {
        checkNonNullForIssue(status, "Status can't be null");
        this.status = status;
    }

    public void updatePeriod(final PeriodProperty period) {
        checkNonNullForIssue(period, "Period can't be null");
        this.period = period;
    }

    public void updateBasicInformation(final BasicInformationProperty basicInformation) {
        checkNonNullForIssue(basicInformation, "Basic information can't be null");
        this.basicInformation = basicInformation;
    }

    public void updateLabelList(final LabelList labelList) {
        checkNonNullForIssue(labelList, "Label List can't be null");
        this.labelList = labelList;
    }

    public void deleteLabelList() {
        this.labelList = null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Issue issue = (Issue) o;
        return Objects.equals(issueId, issue.issueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(issueId);
    }
}
