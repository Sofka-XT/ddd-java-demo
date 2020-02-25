package co.com.sofka.usecase;

import co.com.sofka.business.UseCaseHandler;
import co.com.sofka.domain.DomainEvent;
import co.com.sofka.generic.values.BasicInformationProperty;
import co.com.sofka.usecases.IssueCreateUseCase;
import org.junit.Test;

import java.util.concurrent.Flow;

public class IssueCreateUseCaseExecutorTest {

    @Test
    public void createIssue(){

        UseCaseHandler.SimplePublisher pub = UseCaseHandler.getInstance()
                .execute(new IssueCreateUseCase(),
                        new IssueCreateUseCase.Request("uuid", new BasicInformationProperty("title", ""))
                );

        pub.subscribe(new Flow.Subscriber<DomainEvent>() {
            @Override
            public void onSubscribe(Flow.Subscription subscription) {
            }

            @Override
            public void onNext(DomainEvent domainEvent) {
                System.out.println("onNext sub1");
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

}
