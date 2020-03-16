package co.com.sofka.infrastructure;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface DataRepository<R, T> extends MongoRepository<R, T>  {
}
