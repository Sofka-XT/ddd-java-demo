package co.com.sofka.usecases;

import co.com.sofka.business.UseCaseHandler;
import co.com.sofka.core.label.values.LabelListId;
import co.com.sofka.generic.values.BasicInformationProperty;
import co.com.sofka.infraestructure.SubscriberFirestore;
import org.junit.jupiter.api.Test;

class LabelDeleteUseExecutorTest {

    @Test
    void deleteLabelFromIssue() {

        String uuid = "uuid";
        UseCaseHandler.SimplePublisher pub = UseCaseHandler.getInstance()
                .execute(new LabelDeleteUseCase(),
                        new LabelDeleteUseCase.Request(
                                uuid,
                                new BasicInformationProperty("title", ""),
                                new LabelListId("test"),
                                "Black",
                                "Test")
                );

       // pub.subscribe(new SubscriberFirestore(uuid));

    }
}