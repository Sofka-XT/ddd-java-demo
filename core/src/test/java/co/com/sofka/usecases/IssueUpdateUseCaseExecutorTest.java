package co.com.sofka.usecases;

import co.com.sofka.business.UseCaseHandler;
import co.com.sofka.generic.values.BasicInformationProperty;
import co.com.sofka.generic.values.StatusProperty;
import co.com.sofka.infraestructure.SubscriberFirestore;
import org.junit.jupiter.api.Test;


public class IssueUpdateUseCaseExecutorTest {

    @Test
    void updateAnIssue() {

        String uuid = "uuid";
        UseCaseHandler.SimplePublisher pub = UseCaseHandler.getInstance()
                .execute(new IssueUpdateUseCase(),
                        new IssueUpdateUseCase.Request(
                                uuid,
                                new BasicInformationProperty("title", ""),
                                StatusProperty.OPEN)
                );

      //  pub.subscribe(new SubscriberFirestore(uuid));

    }
}
