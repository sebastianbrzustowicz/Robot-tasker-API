package pl.sebastianbrzustowicz.robotTaskerAPI.SingleWebSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import pl.sebastianbrzustowicz.robotTaskerAPI.repository.WebSocketSingleRoomHandlerRepository;

@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
public class WebSocketSingleRoomConfiguration implements WebSocketConfigurer {

    private final WebSocketSingleRoomHandlerRepository webSocketSingleRoomHandlerRepository;

    @Autowired
    public WebSocketSingleRoomConfiguration(WebSocketSingleRoomHandlerRepository webSocketSingleRoomHandlerRepository) {
        this.webSocketSingleRoomHandlerRepository = webSocketSingleRoomHandlerRepository;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(), "/websocket-single-room");
    }

    private WebSocketSingleRoomHandler webSocketHandler() {
        return new WebSocketSingleRoomHandler(webSocketSingleRoomHandlerRepository);
    }
}