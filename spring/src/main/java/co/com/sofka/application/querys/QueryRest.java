package co.com.sofka.application.querys;

import co.com.sofka.core.issue.queries.QueryGetIssueById;
import co.com.sofka.core.issue.values.IssueId;
import co.com.sofka.infrastructure.IssueEntity;
import co.com.sofka.usecases.handlers.query.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "api/query/",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class QueryRest {

    private QueryHandler<IssueEntity> queryHandler;

    @Autowired
    public QueryRest(QueryHandler<IssueEntity> queryHandler) {
        this.queryHandler = queryHandler;
    }


    @GetMapping("getAllIssues")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Map<String, Object>> getAllIssues() {
        Map<String, Object> response = new HashMap<>();

        Collection<IssueEntity> issueList = queryHandler.findAll();

        response.put("data", issueList);
        response.put("message", "Issues list");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("getById/{issueId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Map<String, Object>> getIssueById(@PathVariable final QueryGetIssueById issueId) {
        Map<String, Object> response = new HashMap<>();

       IssueEntity issueList = queryHandler.findById(new IssueId(issueId.getIssueId()));

        response.put("data", issueList);
        response.put("message", "Issues");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
