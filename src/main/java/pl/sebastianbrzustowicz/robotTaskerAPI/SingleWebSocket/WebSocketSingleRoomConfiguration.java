package pl.sebastianbrzustowicz.robotTaskerAPI.SingleWebSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import pl.sebastianbrzustowicz.robotTaskerAPI.repository.WebSocketHandlerRepository;

@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketConfigurer {

    private final WebSocketHandlerRepository webSocketHandlerRepository;

    @Autowired
    public WebSocketConfiguration(WebSocketHandlerRepository webSocketHandlerRepository) {
        this.webSocketHandlerRepository = webSocketHandlerRepository;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(), "/websocket");
    }

    private WebSocketHandler webSocketHandler() {
        return new WebSocketHandler(webSocketHandlerRepository);
    }
}