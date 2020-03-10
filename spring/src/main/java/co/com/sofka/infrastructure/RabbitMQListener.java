package co.com.sofka.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class RabbitMQListener implements MessageListener {

    private final MongoTemplate mongoTemplate;

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

        Optional.of(issue.getType()).ifPresent(e ->  updateIssue.set("type", e));
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