package co.com.sofka.usecases;

import co.com.sofka.core.issue.IssueList;
import co.com.sofka.core.issue.events.IssueWithBasicInformationCreated;
import co.com.sofka.core.label.LabelList;
import co.com.sofka.core.label.values.LabelListId;
import co.com.sofka.domain.AggregateRootId;
import co.com.sofka.generic.AggregateRootRepository;
import co.com.sofka.generic.values.BasicInformationProperty;

import java.util.UUID;

public class LabelDeleteUseCase {
    public static void main( String[] args )
    {

        //Crear un issue
        AggregateRootRepository aggregateRootRepository = new AggregateRootRepository();
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
        aggregateRootRepository.saveEventsWithAn(anAggregateRootId, changes);
        //issueList.markChangesAsCommitted();



        //actualizar estado
        IssueList issueSaved = IssueList.from(anAggregateRootId, aggregateRootRepository.getEventsBy(anAggregateRootId));
        issueSaved.deleteLabelsBy(issueId);
        var changes2 = issueSaved.getUncommittedChanges();
        aggregateRootRepository.saveEventsWithAn(anAggregateRootId, changes2);
        //issueSaved.markChangesAsCommitted();

        System.out.println(aggregateRootRepository.getDomainEventList());
    }
}
