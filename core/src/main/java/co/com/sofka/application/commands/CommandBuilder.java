package co.com.sofka.application.commands;


import co.com.sofka.business.generic.UseCase;

import java.util.HashMap;
import java.util.Map;

@Deprecated
public final class CommandBuilder {
    private Map<String, UseCase> useCases;

    private CommandBuilder(Map<String, UseCase> useCases) {
        this.useCases = useCases;
    }

    public Map<String, UseCase> useCases() {
        return useCases;
    }


    public static final class Builder {

        private Map<String, UseCase> useCases;

        public Builder() {
            this.useCases = new HashMap<>();
        }

        public Builder registerUseCase(String type, UseCase useCase) {
            useCases.put(type, useCase);
            return this;
        }

        public CommandBuilder build() {
            return new CommandBuilder(useCases);
        }
    }
}
