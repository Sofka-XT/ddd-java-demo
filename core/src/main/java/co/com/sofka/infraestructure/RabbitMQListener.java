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

            if (issue.getType().contains("update")) {
                updateIssue(issue);
            } else {
                mongoTemplate.save(issue, IssueEntity.class.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void updateIssue(IssueEntity issue) {
        Query queryForUpdateIssue = new Query(Criteria.where("_id")
                .is(issue.getIssueId()));

        Update updateIssue = new Update();

        setAttributesForUpdate(issue, updateIssue);

        mongoTemplate.updateFirst(queryForUpdateIssue, updateIssue,
                IssueEntity.class, IssueEntity.class.toString());
    }

    private void setAttributesForUpdate(IssueEntity issue, Update updateIssue) {
        if (issue.getBasicInformation() != null)
            updateIssue.set("basicInformation", issue.getBasicInformation());

        if (issue.getPerson() != null)
            updateIssue.set("person", issue.getPerson());

        if (issue.getStatusProperty() != null)
            updateIssue.set("statusProperty", issue.getStatusProperty());

        if (issue.getPeriod() != null)
            updateIssue.set("period", issue.getPeriod());

        if (issue.getLabelList() != null)
            updateIssue.set("labelList", issue.getLabelList());

        if (issue.getType() != null)
            updateIssue.set("type", issue.getType());
    }

}