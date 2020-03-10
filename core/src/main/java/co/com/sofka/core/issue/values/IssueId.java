package co.com.sofka.core.issue.values;

import static co.com.sofka.generic.DomainAssertions.checkNonNullForIssue;

public class IssueId {

    private final String uuid;

    public IssueId(final String uuid) {
        checkNonNullForIssue(uuid, "ID Issue can´t be null");
        this.uuid = uuid;
    }

    public static IssueId generateWith(String uui) {
        return new IssueId(uui);
    }

    public String getUuid() {
        return uuid;
    }


}
