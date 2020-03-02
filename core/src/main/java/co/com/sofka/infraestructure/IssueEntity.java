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

    public BasicInformationProperty basicInformation;
    public PersonProperty person;
    public StatusProperty statusProperty;
    public PeriodProperty period;
    public  LabelList labelList;

    private  UUID uuid;
    private  String type;
    private Long versionType;

    @JsonIgnore
    private Instant when;

    public IssueEntity() {
    }

    public IssueEntity(IssueId issueId, BasicInformationProperty basicInformation, PersonProperty person, StatusProperty statusProperty, PeriodProperty period, LabelList labelList, UUID uuid, String type, Long versionType, Instant when) {
        this.issueId = issueId;
        this.basicInformation = basicInformation;
        this.person = person;
        this.statusProperty = statusProperty;
        this.period = period;
        this.labelList = labelList;
        this.uuid = uuid;
        this.type = type;
        this.versionType = versionType;
        this.when = when;
    }

    public IssueId getIssueId() {
        return issueId;
    }

    public void setIssueId(IssueId issueId) {
        this.issueId = issueId;
    }

    public BasicInformationProperty getBasicInformation() {
        return basicInformation;
    }

    public void setBasicInformation(BasicInformationProperty basicInformation) {
        this.basicInformation = basicInformation;
    }

    public PersonProperty getPerson() {
        return person;
    }

    public void setPerson(PersonProperty person) {
        this.person = person;
    }

    public StatusProperty getStatusProperty() {
        return statusProperty;
    }

    public void setStatusProperty(StatusProperty statusProperty) {
        this.statusProperty = statusProperty;
    }

    public PeriodProperty getPeriod() {
        return period;
    }

    public void setPeriod(PeriodProperty period) {
        this.period = period;
    }

    public LabelList getLabelList() {
        return labelList;
    }

    public void setLabelList(LabelList labelList) {
        this.labelList = labelList;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
