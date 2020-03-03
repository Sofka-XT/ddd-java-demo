package co.com.sofka.application.commands;

public class CommandException extends RuntimeException {

    public CommandException() {
    }

    public CommandException(final String message) {
        super(message);
    }

}
