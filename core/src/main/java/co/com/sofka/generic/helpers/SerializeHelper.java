package co.com.sofka.generic.helpers;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infrastructure.EventMapperException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

public class SerializeHelper {

    public static String serializeEvent(final ObjectMapper mapper, final DomainEvent domainEvent) {
        try {
            return mapper.writeValueAsString(domainEvent);
        } catch (JsonProcessingException e) {
            throw new EventMapperException();
        }
    }

}
