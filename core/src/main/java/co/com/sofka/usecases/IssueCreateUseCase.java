package co.com.sofka.usecases;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.core.issue.IssueList;
import co.com.sofka.core.issue.values.IssueListId;
import co.com.sofka.generic.values.BasicInformationProperty;

public class IssueCreateUseCase extends UseCase<IssueCreateUseCase.Request, ResponseEvents> {

    @Override
    protected void executeUseCase(final Request requestValues) {

        IssueListId issueListId = new IssueListId(requestValues.uuid);
        IssueList issueList = new IssueList(issueListId);

        issueList.createIssueWithBasicInformation(requestValues.basicInformation);
        emit().onSuccess(new ResponseEvents(issueList.getUncommittedChanges()));
        issueList.markChangesAsCommitted();

    }

    public static class Request implements UseCase.RequestValues {
        private String uuid;
        private BasicInformationProperty basicInformation;

        public Request(final String uuid, final BasicInformationProperty basicInformation) {
            this.uuid = uuid;
            this.basicInformation = basicInformation;
        }
    }


}
