package co.com.sofka.infraestructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RabbitMQListener implements MessageListener {

    private IssueEntityRepository repository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public RabbitMQListener(IssueEntityRepository repository,
       MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    public void onMessage(Message message) {
        final ObjectMapper mapper = new ObjectMapper();
        String body = new String(message.getBody());
        try {


            IssueEntity issue = mapper.readValue(body, IssueEntity.class);

           // repository.save(issue);

            Query query1 = new Query(Criteria.where("_id")
                    .ne(issue.getIssueId().getUuid()));
            Update update1 = new Update();
            update1.set("basicInformation", issue.getBasicInformation());
            update1.set("person", issue.getPerson());
            update1.set("statusProperty", issue.getStatusProperty());
            update1.set("period", issue.getPeriod());
            update1.set("labelList", issue.getLabelList());
            mongoTemplate.updateFirst(query1, update1,
                    IssueEntity.class, IssueEntity.class.toString());

//            mongoTemplate.save(issue, IssueEntity.class.toString());

            //repository.save(issue);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}