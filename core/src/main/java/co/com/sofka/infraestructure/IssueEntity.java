package co.com.sofka.infraestructure;

import co.com.sofka.core.issue.values.IssueId;
import co.com.sofka.core.issue.values.PersonProperty;
import co.com.sofka.core.label.LabelList;
import co.com.sofka.generic.values.BasicInformationProperty;
import co.com.sofka.generic.values.PeriodProperty;
import co.com.sofka.generic.values.StatusProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Document(collection = "issue")
public class IssueEntity {

    @Id
    private IssueId issueId;

    private BasicInformationProperty basicInformation;
    private PersonProperty person;
    private StatusProperty statusProperty;
    private PeriodProperty period;

    @JsonIgnore
    private LabelList labelList;

    private  String type;
    private  UUID uuid;

    @JsonIgnore
    private Long versionType;

    @JsonIgnore
    private Instant when;

    public IssueEntity() {
    }

    public IssueEntity(final IssueId issueId, final BasicInformationProperty basicInformation,
                       final PersonProperty person, final StatusProperty statusProperty,
                       final PeriodProperty period,
                       final String type, final UUID uuid) {
        this.issueId = issueId;
        this.basicInformation = basicInformation;
        this.person = person;
        this.statusProperty = statusProperty;
        this.period = period;
        this.type = type;
        this.uuid = uuid;
    }

    public IssueId getIssueId() {
        return issueId;
    }

    public void setIssueId(final IssueId issueId) {
        this.issueId = issueId;
    }

    public BasicInformationProperty getBasicInformation() {
        return basicInformation;
    }

    public void setBasicInformation(final BasicInformationProperty basicInformation) {
        this.basicInformation = basicInformation;
    }

    public PersonProperty getPerson() {
        return person;
    }

    public void setPerson(final PersonProperty person) {
        this.person = person;
    }

    public StatusProperty getStatusProperty() {
        return statusProperty;
    }

    public void setStatusProperty(final StatusProperty statusProperty) {
        this.statusProperty = statusProperty;
    }

    public PeriodProperty getPeriod() {
        return period;
    }

    public void setPeriod(final PeriodProperty period) {
        this.period = period;
    }

    public LabelList getLabelList() {
        return labelList;
    }

    public void setLabelList(final LabelList labelList) {
        this.labelList = labelList;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(final UUID uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public Long getVersionType() {
        return versionType;
    }

    public void setVersionType(final Long versionType) {
        this.versionType = versionType;
    }

    public Instant getWhen() {
        return when;
    }

    public void setWhen(final Instant when) {
        this.when = when;
    }
}
