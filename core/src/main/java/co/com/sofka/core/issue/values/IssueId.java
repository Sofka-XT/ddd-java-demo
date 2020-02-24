package co.com.sofka.core.issue.values;

import java.util.Objects;

import static co.com.sofka.generic.DomainAssertions.checkNonNullForIssue;

public class IssueId {

    private final String uuid;

    public IssueId(String uuid) {
        checkNonNullForIssue(uuid,"ID Issue can´t be null");
        this.uuid = uuid;
    }

    public static IssueId generateWith(String uui){
        return new IssueId(uui);
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IssueId issueId = (IssueId) o;
        return Objects.equals(uuid, issueId.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

}
