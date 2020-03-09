package co.com.sofka.core.issue.queries;


import com.fasterxml.jackson.annotation.JsonProperty;

public class QueryGetIssueById  {

    private String issueId;

    public QueryGetIssueById(@JsonProperty("aggregateRoodId") final String issueId) {
        this.issueId = issueId;
    }

    public String getIssueId() {
        return issueId;
    }
}
