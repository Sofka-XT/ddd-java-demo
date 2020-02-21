package co.com.sofka.usecases;

import co.com.sofka.core.issue.IssueList;
import co.com.sofka.core.issue.events.IssueWithBasicInformationCreated;
import co.com.sofka.domain.AggregateRootId;
import co.com.sofka.generic.AggregateRootRepository;
import co.com.sofka.generic.values.BasicInformationProperty;

public class IssueCreateUseCase {
    public static void main( String[] args ) {
        //Crear un issue
        AggregateRootRepository aggregateRootRepository = new AggregateRootRepository();
        AggregateRootId anAggregateRootId = new AggregateRootId("uuid");
        IssueList issueList = new IssueList(anAggregateRootId);

        //accionar el comportamiento
        issueList.createIssueWithBasicInformation(new BasicInformationProperty("title", "test"));
        var changes = issueList.getUncommittedChanges();
        var issueId = ((IssueWithBasicInformationCreated)changes.get(0)).getIssueId();
        aggregateRootRepository.saveEventsWithAn(anAggregateRootId, changes);
        issueList.markChangesAsCommitted();

    }
}
