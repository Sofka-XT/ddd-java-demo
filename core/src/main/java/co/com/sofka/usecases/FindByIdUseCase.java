package co.com.sofka.usecases;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.generic.values.BasicInformationProperty;

import java.util.List;

public class FindByIdUseCase extends UseCase<FindByIdUseCase.Request, FindByIdUseCase.Response> {




    @Override
    protected void executeUseCase(Request request) {

    }

    public static class Request implements UseCase.RequestValues {
        private String uuid;
        private BasicInformationProperty basicInformation;

        public Request(final String uuid, final BasicInformationProperty basicInformation) {
            this.uuid = uuid;
            this.basicInformation = basicInformation;
        }
    }

    public static class Response implements UseCase.PubEvents {

        @Override
        public List<DomainEvent> getDomainEvents() {
            return null;
        }
    }
}
