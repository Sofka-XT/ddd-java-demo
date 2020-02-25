package co.com.sofka.usecases;

import co.com.sofka.core.issue.IssueList;
import co.com.sofka.core.issue.events.IssueWithBasicInformationCreated;
import co.com.sofka.core.label.LabelList;
import co.com.sofka.core.label.values.LabelListId;
import co.com.sofka.domain.AggregateRootId;
import co.com.sofka.infraestructure.FirestoreRepository;
import co.com.sofka.generic.values.BasicInformationProperty;

import java.io.IOException;
import java.util.UUID;

import static co.com.sofka.infraestructure.BdConnection.firebaseInstance;

public class LabelAddToIssueUseCase {
    public static void main( String[] args ) throws IOException {
        //Crear un issue
        FirestoreRepository firestoreRepository = new FirestoreRepository(firebaseInstance());
        AggregateRootId anAggregateRootId = new AggregateRootId("uuid");
        IssueList issueList = new IssueList(anAggregateRootId);
        final LabelListId labelListId = new LabelListId(UUID.randomUUID().toString());

        //accionar el comportamiento
        issueList.createIssueWithBasicInformation(new BasicInformationProperty("title", "test"));


        var changes = issueList.getUncommittedChanges();
        var issueId = ((IssueWithBasicInformationCreated)changes.get(0)).getIssueId();
        LabelList labelList = new LabelList(labelListId);
        labelList.createLabel("Black", "Fixes");
        issueList.updateLabelListBy(issueId, labelList);
        firestoreRepository.saveEventsWithAn(anAggregateRootId, changes);
        //issueList.markChangesAsCommitted();

        System.out.println(firestoreRepository.getDomainEventList());


    }
}
