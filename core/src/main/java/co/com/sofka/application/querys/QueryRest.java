package co.com.sofka.application.querys;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/")
public class QueryRest {

    @GetMapping("query/{query}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Map<String, Object>> getQuery(@PathVariable String query) {

    }

}
