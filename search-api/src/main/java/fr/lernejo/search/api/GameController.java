package fr.lernejo.search.api;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class GameController {
    private final RestHighLevelClient restHighLevelClient;
    public GameController(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }
    @GetMapping("/api/games")
    public ArrayList<Object> searchGames(@RequestParam(name = "query") String searchQuery) throws IOException {
        ArrayList<Object> resultList = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest()
            .source(SearchSourceBuilder.searchSource().query(new QueryStringQueryBuilder(searchQuery)));
        this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT)
            .getHits().forEach(hit -> resultList.add(hit.getSourceAsMap()));
        return resultList;
    }
}
