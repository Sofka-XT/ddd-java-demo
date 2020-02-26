package co.com.sofka.usecases;

import co.com.sofka.business.UseCaseHandler;
import co.com.sofka.core.label.values.LabelListId;
import co.com.sofka.generic.values.BasicInformationProperty;
import co.com.sofka.infraestructure.SubscriberFirestoreRepository;
import org.junit.jupiter.api.Test;

class LabelAddToIssueUseExecutorTest {

    @Test
    void addLabelToIssue() {

        String uuid = "uuid";
        UseCaseHandler.SimplePublisher pub = UseCaseHandler.getInstance()
                .execute(new LabelAddToIssueUseCase(),
                        new LabelAddToIssueUseCase.Request(
                                uuid,
                                new BasicInformationProperty("title", ""),
                                new LabelListId("test"),
                                "Black",
                                "Test")
                );

        pub.subscribe(new SubscriberFirestoreRepository(uuid));

    }
}