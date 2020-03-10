package co.com.sofka.infrastructure;

import com.google.gson.Gson;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Map;

public class RabbitMQListener implements MessageListener {

    private final MongoTemplate mongoTemplate;

    public RabbitMQListener(final MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void onMessage(final Message message) {
        Gson gson = new Gson();
        String body = new String(message.getBody());
        Map<String, Object> map = gson.fromJson(body, Map.class);
        IssueEntity issue = gson.fromJson(gson.toJson(map), IssueEntity.class);
        if (issue.getType().contains("update")) {
            updateIssue(map);
        } else {
            mongoTemplate.save(issue);
        }
    }

    private void updateIssue(Map<String, Object> map) {
        Query queryForUpdateIssue = new Query(Criteria.where("_id")
                .is(map.get("issueId")));

        Update updateIssue = new Update();

        for (Map.Entry<String, Object> attribute : map.entrySet()) {
            if (!attribute.getKey().equals("issueId")) {
                updateIssue.set(attribute.getKey(), attribute.getValue());
            }
        }
        mongoTemplate.updateFirst(queryForUpdateIssue, updateIssue,
                IssueEntity.class);

    }

}