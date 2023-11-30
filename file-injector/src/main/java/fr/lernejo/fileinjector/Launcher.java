package fr.lernejo.fileinjector;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Launcher {
    public static void main(String[] args) throws IOException {
        try (AbstractApplicationContext springContext = new AnnotationConfigApplicationContext(Launcher.class)) {
            if (args.length > 0) {
                ObjectMapper objectMapper = new ObjectMapper();
                List<GameInfo> Gameinfo = Arrays.asList(objectMapper.readValue(Paths.get(args[0]).toFile(), GameInfo[].class));
                RabbitTemplate rabbitTemplate = springContext.getBean(RabbitTemplate.class);
                for (GameInfo gameInfo : Gameinfo) {
                    rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                    rabbitTemplate.convertAndSend("game_info", "", message -> {
                        message.getMessageProperties().getHeaders().put("game_id", gameInfo.id());
                        return message;});
                }
            }
        } catch (IOException err) {
            throw new IOException(err);
        }
    }
}
