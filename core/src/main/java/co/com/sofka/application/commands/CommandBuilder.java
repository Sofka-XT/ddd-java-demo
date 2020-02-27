package co.com.sofka.application.commands;


import co.com.sofka.business.generic.UseCase;

import java.util.HashMap;
import java.util.Map;

@Deprecated
public final class CommandBuilder {
    private Map<String, CommandHandler> commandHandler;

    private CommandBuilder(Map<String, CommandHandler> commandHandler) {
        this.commandHandler = commandHandler;
    }

    public Map<String, CommandHandler> useCases() {
        return commandHandler;
    }


    public static final class Builder {

        private Map<String, CommandHandler> commandHandler;

        public Builder() {
            this.commandHandler = new HashMap<>();
        }

        public Builder registerUseCase(String type, CommandHandler handler) {
            commandHandler.put(type, handler);
            return this;
        }

        public CommandBuilder build() {
            return new CommandBuilder(commandHandler);
        }
    }
}
