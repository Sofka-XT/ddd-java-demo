package co.com.sofka.infraestructure;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.bus.EventBus;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static co.com.sofka.infraestructure.RabbitConfiguration.topicExchangeName;

public class RabbitDomainEventBus implements EventBus {
    @Value("${rabbitmq.routingkey}")
    private String routingkey;


    private final RabbitTemplate rabbitTemplate;

    public RabbitDomainEventBus(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(DomainEvent event) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        final String eventSerialization = serializeEvent(mapper, event);
        rabbitTemplate.convertAndSend(topicExchangeName, routingkey, eventSerialization);
    }

    private String serializeEvent(ObjectMapper mapper, DomainEvent domainEvent) {
        final String eventSerialization;
        try {
            eventSerialization = mapper.writeValueAsString(domainEvent);
        } catch (JsonProcessingException e) {
            throw new EventMapperException();
        }
        return eventSerialization;
    }
}
