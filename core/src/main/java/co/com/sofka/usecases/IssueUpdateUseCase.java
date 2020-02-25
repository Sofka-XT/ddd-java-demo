package co.com.sofka.usecases;

import co.com.sofka.core.issue.IssueList;
import co.com.sofka.core.issue.events.IssueWithBasicInformationCreated;
import co.com.sofka.domain.AggregateRootId;
import co.com.sofka.infraestructure.FirestoreRepository;
import co.com.sofka.generic.values.BasicInformationProperty;
import co.com.sofka.generic.values.StatusProperty;

import java.io.IOException;

import static co.com.sofka.infraestructure.BdConnection.firebaseInstance;

public class IssueUpdateUseCase {

    public static void main( String[] args ) throws IOException {
        //Crear un issue
        FirestoreRepository firestoreRepository = new FirestoreRepository(firebaseInstance());
        AggregateRootId anAggregateRootId = new AggregateRootId("lis");
        IssueList issueList = new IssueList(anAggregateRootId);

        //accionar el comportamiento
        issueList.createIssueWithBasicInformation(new BasicInformationProperty("nuevo issue", ""));

        var changes = issueList.getUncommittedChanges();
        var issueId = ((IssueWithBasicInformationCreated)changes.get(0)).getIssueId();
        firestoreRepository.saveEventsWithAn(anAggregateRootId, changes);
        //issueList.markChangesAsCommitted();

        //actualizar estado
        IssueList issueSaved = IssueList.from(anAggregateRootId, firestoreRepository.getEventsBy(anAggregateRootId));
        issueSaved.updateIssueStatusBy(issueId, StatusProperty.CLOSE);
        var changes2 = issueSaved.getUncommittedChanges();
        firestoreRepository.saveEventsWithAn(anAggregateRootId, changes2);
        //issueSaved.markChangesAsCommitted();

        System.out.println(firestoreRepository.getDomainEventList());

/*
new ObjectMapper()
         .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
        .writeValueAsString(new IssueStatusUpdated(new IssueId("idIssue01"), StatusProperty.CLOSE));
 */
    }
}
