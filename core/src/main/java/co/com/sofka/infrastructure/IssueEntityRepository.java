package co.com.sofka.infrastructure;

import co.com.sofka.core.issue.values.IssueId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IssueEntityRepository extends MongoRepository<IssueEntity, IssueId> {

}
