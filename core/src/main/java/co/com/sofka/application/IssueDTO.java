package co.com.sofka.application;

import co.com.sofka.core.issue.values.PersonProperty;
import co.com.sofka.core.label.values.LabelListId;
import co.com.sofka.domain.Command;
import co.com.sofka.generic.values.BasicInformationProperty;
import co.com.sofka.generic.values.PeriodProperty;
import co.com.sofka.generic.values.StatusProperty;
import com.fasterxml.jackson.annotation.JsonProperty;


public class IssueDTO extends Command {
    public String uuid;
    public BasicInformationProperty basicInformation;
    public PeriodProperty period;
    public PersonProperty person;
    public StatusProperty status;
    public LabelListId labelListId;
    public String color;
    public String title;


    public IssueDTO(@JsonProperty("type") String type, @JsonProperty("uuid") String uuid,
                    @JsonProperty("basicInformation") BasicInformationProperty basicInformation,
                    @JsonProperty("period") PeriodProperty period, @JsonProperty("person") PersonProperty person,
                    @JsonProperty("status") StatusProperty status,
                    @JsonProperty("labelListId") LabelListId labelListId, @JsonProperty("color") String color,
                    @JsonProperty("title") String title) {
        super(type);
        this.uuid = uuid;
        this.basicInformation = basicInformation;
        this.period = period;
        this.person = person;
        this.status = status;
        this.labelListId = labelListId;
        this.color = color;
        this.title = title;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public BasicInformationProperty getBasicInformation() {
        return basicInformation;
    }

    public void setBasicInformation(BasicInformationProperty basicInformation) {
        this.basicInformation = basicInformation;
    }

    public PeriodProperty getPeriod() {
        return period;
    }

    public void setPeriod(PeriodProperty period) {
        this.period = period;
    }

    public PersonProperty getPerson() {
        return person;
    }

    public void setPerson(PersonProperty person) {
        this.person = person;
    }

    public StatusProperty getStatus() {
        return status;
    }

    public void setStatus(StatusProperty status) {
        this.status = status;
    }

    public LabelListId getLabelListId() {
        return labelListId;
    }

    public void setLabelListId(LabelListId labelListId) {
        this.labelListId = labelListId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
