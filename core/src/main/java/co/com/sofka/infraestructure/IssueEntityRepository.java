package co.com.sofka.infraestructure;

import co.com.sofka.core.issue.values.IssueId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface IssueEntityRepository extends MongoRepository<IssueEntity, IssueId> {

}
