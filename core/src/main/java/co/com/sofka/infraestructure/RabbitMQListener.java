package co.com.sofka.infraestructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.io.IOException;

public class RabbitMQListener implements MessageListener {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public RabbitMQListener(final MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void onMessage(final Message message) {
        final ObjectMapper mapper = new ObjectMapper();
        String body = new String(message.getBody());
        try {
            IssueEntity issue = mapper.readValue(body, IssueEntity.class);

            if (issue.getType().contains("update")) {
                updateIssue(issue);
            } else {
                mongoTemplate.save(issue);
            }

        } catch (IOException e) {
          throw new EventMapperException("the structure is incorrect");
        }

    }

    private void updateIssue(final IssueEntity issue) {
        Query queryForUpdateIssue = new Query(Criteria.where("_id")
                .is(issue.getIssueId()));

        Update updateIssue = new Update();

        setAttributesForUpdate(issue, updateIssue);

        mongoTemplate.updateFirst(queryForUpdateIssue, updateIssue,
                IssueEntity.class);
    }

    private void setAttributesForUpdate(final IssueEntity issue, final Update updateIssue) {
        setBasicInformation(issue, updateIssue);
        setPerson(issue, updateIssue);
        setStatus(issue, updateIssue);
        setPeriod(issue, updateIssue);
        setLabelList(issue, updateIssue);
        setType(issue, updateIssue);
    }

    private void setType(IssueEntity issue, Update updateIssue) {
        if (issue.getType() != null) {
            updateIssue.set("type", issue.getType());
        }
    }

    private void setLabelList(IssueEntity issue, Update updateIssue) {
        if (issue.getLabelList() != null) {
            updateIssue.set("labelList", issue.getLabelList());
        }
    }

    private void setPeriod(IssueEntity issue, Update updateIssue) {
        if (issue.getPeriod() != null) {
            updateIssue.set("period", issue.getPeriod());
        }
    }

    private void setStatus(IssueEntity issue, Update updateIssue) {
        if (issue.getStatusProperty() != null) {
            updateIssue.set("statusProperty", issue.getStatusProperty());
        }
    }

    private void setPerson(IssueEntity issue, Update updateIssue) {
        if (issue.getPerson() != null) {
            updateIssue.set("person", issue.getPerson());
        }
    }

    private void setBasicInformation(IssueEntity issue, Update updateIssue) {
        if (issue.getBasicInformation() != null) {
            updateIssue.set("basicInformation", issue.getBasicInformation());
        }
    }

}