package co.com.sofka.usecases;

import co.com.sofka.core.issue.IssueList;
import co.com.sofka.core.issue.events.IssueWithBasicInformationCreated;
import co.com.sofka.core.issue.values.PersonProperty;
import co.com.sofka.domain.AggregateRootId;
import co.com.sofka.infraestructure.FirestoreRepository;
import co.com.sofka.generic.values.BasicInformationProperty;
import co.com.sofka.generic.values.PeriodProperty;
import co.com.sofka.generic.values.StatusProperty;

import java.io.IOException;
import java.util.Date;

import static co.com.sofka.infraestructure.BdConnection.firebaseInstance;

public class IssueCreateAndUpdateUseCase {

    public static void main( String[] args ) throws IOException {
        //Crear un issue
        FirestoreRepository firestoreRepository = new FirestoreRepository(firebaseInstance());
        AggregateRootId anAggregateRootId = new AggregateRootId("uuid");
        IssueList issueList = new IssueList(anAggregateRootId);


        //accionar el comportamiento
        issueList.createIssueWithBasicInformation(new BasicInformationProperty("title", "test"));

        var changes = issueList.getUncommittedChanges();
        var issueId = ((IssueWithBasicInformationCreated)changes.get(0)).getIssueId();
        firestoreRepository.saveEventsWithAn(anAggregateRootId, changes);
        //issueList.markChangesAsCommitted();

        //actualizar la información del issue
        IssueList issueSaved = IssueList.from(anAggregateRootId, firestoreRepository.getEventsBy(anAggregateRootId));
        issueSaved.updateFullIssueWithoutBasicInformation(issueId,
                new PeriodProperty(new Date(), new Date()),
                new PersonProperty("Juanpa is sick"),
                StatusProperty.OPEN);

        var changes2 = issueSaved.getUncommittedChanges();
        firestoreRepository.saveEventsWithAn(anAggregateRootId, changes2);
        //issueSaved.markChangesAsCommitted();

        System.out.println(firestoreRepository.getDomainEventList());
    }
}
