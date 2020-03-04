package co.com.sofka.infraestructure;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import static co.com.sofka.generic.helpers.Constants.QUEUE_NAME;


@Configuration
public class RabbitConfiguration {


    @Bean
    Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    MessageListenerContainer messageListenerContainer(final ConnectionFactory connectionFactory,
                                                      final MongoTemplate mongoTemplate) {

        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        simpleMessageListenerContainer.setQueues(queue());
        simpleMessageListenerContainer.setMessageListener(new RabbitMQListener(mongoTemplate));
        return simpleMessageListenerContainer;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(final ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public RabbitMQListener rabbitMQListener(final MongoTemplate mongoTemplate){
        return new RabbitMQListener(mongoTemplate);
    }

}
