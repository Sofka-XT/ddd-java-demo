package co.com.sofka.core.issue.queries;

import co.com.sofka.domain.generic.QueryGeneric;
import com.fasterxml.jackson.annotation.JsonProperty;

public class QueryGetIssueById extends QueryGeneric {

    private String issueId;

    public QueryGetIssueById(@JsonProperty("aggregateRoodId") final String issueId) {
        this.issueId = issueId;
    }

    public String getIssueId() {
        return issueId;
    }
}
