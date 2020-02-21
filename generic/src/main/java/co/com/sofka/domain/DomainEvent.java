package co.com.sofka.domain;

import java.time.Instant;
import java.util.UUID;

public class DomainEvent {
    private Long versionType;
    public final Instant when;
    public final UUID uuid;
    public final String type;


    public DomainEvent(String type) {
        this.type = type;
        this.when = Instant.now();
        this.uuid = UUID.randomUUID();
        this.versionType = 1L;
    }

    public void nextVersionType(Long versionType){
        this.versionType = versionType + 1;
    }

    public Long currentVersionType(){
        return versionType;
    }
}
