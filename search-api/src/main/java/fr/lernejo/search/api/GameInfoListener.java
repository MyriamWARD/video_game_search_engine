package fr.lernejo.search.api;

import org.elasticsearch.client.RequestOptions;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;

import java.io.IOException;

import static fr.lernejo.search.api.AmqpConfiguration.GAME_INFO_QUEUE;

@Component
public class GameInfoListener {
        private final RestHighLevelClient restHighLevelClient;

        @Autowired
        public GameInfoListener(RestHighLevelClient restHighLevelClient) {
            this.restHighLevelClient = restHighLevelClient;
        }

        @RabbitListener(queues = GAME_INFO_QUEUE)
        public void onMessage(byte[] message, @Header ("game_id") String id) throws IOException {
            IndexRequest indexRequest = new IndexRequest("games").id(id).source(message, XContentType.JSON);
            this.restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            }
        }
