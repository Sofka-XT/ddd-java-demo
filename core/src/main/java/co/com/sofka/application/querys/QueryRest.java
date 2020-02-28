package co.com.sofka.application.querys;

import co.com.sofka.core.issue.quieries.QueryGetIssueListById;
import co.com.sofka.domain.generic.AggregateRootId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/")
public class QueryRest {

    QueryHandler<QueryGetIssueListById> queryHandler;

    @Autowired
    public QueryRest(QueryHandler<QueryGetIssueListById> queryHandler) {
        this.queryHandler = queryHandler;
    }

    @GetMapping("query/getById/{query}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Map<String, Object>> queryGetIssueListById(@PathVariable QueryGetIssueListById query) {
        Map<String, Object> response = new HashMap<>();

        Collection<QueryGetIssueListById> issueList = queryHandler.findById(new AggregateRootId(query.getAggregateRoodId()));

        response.put("data",issueList);
        response.put("message","Issues list");

        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
    }

}
