package co.com.sofka.usecases;

import co.com.sofka.core.issue.IssueList;
import co.com.sofka.core.issue.events.IssueWithBasicInformationCreated;
import co.com.sofka.domain.AggregateRootId;
import co.com.sofka.infraestructure.AggregateRootRepository;
import co.com.sofka.generic.values.BasicInformationProperty;

import java.io.IOException;

import static co.com.sofka.infraestructure.BdConnection.firebaseInstance;

public class IssueCreateUseCase {
    public static void main( String[] args ) throws IOException {


        //Crear un issue
        AggregateRootRepository aggregateRootRepository = new AggregateRootRepository(firebaseInstance());
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
