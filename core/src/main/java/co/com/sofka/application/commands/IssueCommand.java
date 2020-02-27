package co.com.sofka.application.commands;


import co.com.sofka.domain.generic.Command;

@Deprecated
public class IssueCommand extends Command {

    private String aggregateId;

    public IssueCommand(String type, String aggregateId) {
        super(type);
        this.aggregateId = aggregateId;
    }

    public String getAggregateId() {
        return aggregateId;
    }

}
