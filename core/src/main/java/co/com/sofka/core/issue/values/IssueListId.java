package co.com.sofka.core.issue.values;

import co.com.sofka.domain.generic.AggregateRootId;

import java.util.UUID;

public class IssueListId extends AggregateRootId {


    public IssueListId(String uuid) {
        super(uuid);
    }

    public static IssueListId create() {
        return new IssueListId(UUID.randomUUID().toString());
    }

    @Override
    public String toString() {
        return super.value();
    }
}
