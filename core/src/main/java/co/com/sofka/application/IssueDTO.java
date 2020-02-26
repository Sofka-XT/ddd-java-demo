package co.com.sofka.application;

import co.com.sofka.core.issue.values.PersonProperty;
import co.com.sofka.core.label.values.LabelListId;
import co.com.sofka.domain.Command;
import co.com.sofka.generic.values.BasicInformationProperty;
import co.com.sofka.generic.values.PeriodProperty;
import co.com.sofka.generic.values.StatusProperty;

public class IssueDTO extends Command {
    public String uuid;
    public BasicInformationProperty basicInformation;
    public PeriodProperty period;
    public PersonProperty person;
    public StatusProperty status;
    public LabelListId labelListId;
    public String color;
    public String title;

    public IssueDTO(String type) {
        super(type);
    }
}
