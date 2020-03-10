package co.com.sofka.infrastructure;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Document(collection = "issue")
public class IssueEntity {

    @Id
    private Object issueId;
    private Object basicInformation;
    private Object person;
    private Object statusProperty;
    private Object period;
    private Object labelList;
    private String type;
    private UUID uuid;
    private Long versionType;
    private Instant when;

    public IssueEntity() {
    }

    public Object getIssueId() {
        return issueId;
    }

    public void setIssueId(Object issueId) {
        this.issueId = issueId;
    }

    public Object getBasicInformation() {
        return basicInformation;
    }

    public void setBasicInformation(Object basicInformation) {
        this.basicInformation = basicInformation;
    }

    public Object getPerson() {
        return person;
    }

    public void setPerson(Object person) {
        this.person = person;
    }

    public Object getStatusProperty() {
        return statusProperty;
    }

    public void setStatusProperty(Object statusProperty) {
        this.statusProperty = statusProperty;
    }

    public Object getPeriod() {
        return period;
    }

    public void setPeriod(Object period) {
        this.period = period;
    }

    public Object getLabelList() {
        return labelList;
    }

    public void setLabelList(Object labelList) {
        this.labelList = labelList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Long getVersionType() {
        return versionType;
    }

    public void setVersionType(Long versionType) {
        this.versionType = versionType;
    }

    public Instant getWhen() {
        return when;
    }

    public void setWhen(Instant when) {
        this.when = when;
    }
}
