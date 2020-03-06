package co.com.sofka.infraestructure;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;


@Configuration
public class RabbitConfiguration {

    @Bean
    @Qualifier("issue_status_updated")
    Queue queueIssueStatusUpdated() {
        return new Queue("issue_status_updated", false);
    }

    @Bean
    @Qualifier("issue_with_basic_information_created")
    Queue queueIssueWithBasicInformationCreated() {
        return new Queue("issue_with_basic_information_created", false);
    }


    @Bean
    MessageListenerContainer messageListenerContainer(final ConnectionFactory connectionFactory,
                                                      final MongoTemplate mongoTemplate) {

        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        simpleMessageListenerContainer.setQueues(queueIssueStatusUpdated(), queueIssueWithBasicInformationCreated());
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
