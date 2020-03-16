package co.com.sofka.core.issue.queries;

public class QueryGetIssueById  {

    private String issueId;

    public QueryGetIssueById(final String issueId) {
        this.issueId = issueId;
    }

    public String getIssueId() {
        return issueId;
    }
}
