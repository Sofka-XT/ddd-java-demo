package co.com.sofka.usecases.handlers.quieries;

public class QueryHandlerException extends RuntimeException {

    public QueryHandlerException() {
    }

    public QueryHandlerException(final String message) {
        super(message);
    }
}
