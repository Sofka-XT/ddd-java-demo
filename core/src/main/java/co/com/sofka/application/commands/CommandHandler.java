package co.com.sofka.application.commands;

@FunctionalInterface
public interface CommandHandler<T> {
    void execute(T command);

}



