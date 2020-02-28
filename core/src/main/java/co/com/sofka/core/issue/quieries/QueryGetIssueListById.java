package co.com.sofka.core.issue.quieries;

import co.com.sofka.domain.generic.QueryGeneric;
import com.fasterxml.jackson.annotation.JsonProperty;

public class QueryGetIssueListById extends QueryGeneric {
    private String aggregateRoodId;

    public QueryGetIssueListById(@JsonProperty("aggregateRoodId") String aggregateRoodId) {
        this.aggregateRoodId = aggregateRoodId;
    }

    public String getAggregateRoodId() {
        return aggregateRoodId;
    }
}
