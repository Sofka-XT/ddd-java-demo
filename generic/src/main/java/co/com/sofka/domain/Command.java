package co.com.sofka.domain;

import java.util.UUID;

public abstract class Command {
    public final UUID uuid;
    public final String type;

    public Command(final String type) {
        this.type = type;
        this.uuid = UUID.randomUUID();
    }
}
