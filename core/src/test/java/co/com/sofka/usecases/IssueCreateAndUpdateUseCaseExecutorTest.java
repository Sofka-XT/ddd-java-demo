package co.com.sofka.usecases;

import co.com.sofka.business.UseCaseHandler;
import co.com.sofka.core.issue.values.PersonProperty;
import co.com.sofka.generic.values.BasicInformationProperty;
import co.com.sofka.generic.values.PeriodProperty;
import co.com.sofka.generic.values.StatusProperty;
import co.com.sofka.infraestructure.SubscriberFirestoreRepository;
import org.junit.Test;

import java.util.Date;

public class IssueCreateAndUpdateUseCaseExecutorTest {

        @Test
        public void createAndUpdateIssue() {

            String uuid = "uuid";
            UseCaseHandler.SimplePublisher pub = UseCaseHandler.getInstance()
                    .execute(new IssueCreateAndUpdateUseCase(),
                            new IssueCreateAndUpdateUseCase.Request(
                                    uuid,
                                    new BasicInformationProperty("title", ""),
                                    new PeriodProperty(new Date(), new Date()),
                                    new PersonProperty("Lisbey"),
                                    StatusProperty.OPEN)
                    );

            pub.subscribe(new SubscriberFirestoreRepository(uuid));

        }
}