package co.com.sofka.usecases;

import co.com.sofka.core.issue.IssueList;
import co.com.sofka.core.issue.events.IssueWithBasicInformationCreated;
import co.com.sofka.domain.AggregateRootId;
import co.com.sofka.generic.AggregateRootRepository;
import co.com.sofka.generic.values.BasicInformationProperty;
import co.com.sofka.generic.values.StatusProperty;;

public class IssueUpdateUseCase {

    public static void main( String[] args ) {
        //Crear un issue
        AggregateRootRepository aggregateRootRepository = new AggregateRootRepository();
        AggregateRootId anAggregateRootId = new AggregateRootId("uuid");
        IssueList issueList = new IssueList(anAggregateRootId);

        //accionar el comportamiento
        issueList.createIssueWithBasicInformation(new BasicInformationProperty("nuevo issue", ""));

        var changes = issueList.getUncommittedChanges();
        var issueId = ((IssueWithBasicInformationCreated)changes.get(0)).getIssueId();
        aggregateRootRepository.saveEventsWithAn(anAggregateRootId, changes);
        issueList.markChangesAsCommitted();

        //actualizar estado
        IssueList issueSaved = IssueList.from(anAggregateRootId, aggregateRootRepository.getEventsBy(anAggregateRootId));
        issueSaved.updateIssueStatusBy(issueId, StatusProperty.CLOSE);
        var changes2 = issueSaved.getUncommittedChanges();
        aggregateRootRepository.saveEventsWithAn(anAggregateRootId, changes2);
        issueSaved.markChangesAsCommitted();

        System.out.println(aggregateRootRepository.getDomainEventList());

/*
new ObjectMapper()
         .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
        .writeValueAsString(new IssueStatusUpdated(new IssueId("idIssue01"), StatusProperty.CLOSE));
 */
    }
}
