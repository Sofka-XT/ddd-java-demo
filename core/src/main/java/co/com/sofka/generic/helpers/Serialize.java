package co.com.sofka.generic.helpers;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.EventMapperException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class Serialize {


    public static String serializeEvent(final ObjectMapper mapper, final DomainEvent domainEvent) {
        try {
            return mapper.writeValueAsString(domainEvent);
        } catch (JsonProcessingException e) {
            throw new EventMapperException();
        }
    }
}
