package pl.sebastianbrzustowicz.robotTaskerAPI.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // Message broker configuration (STOMP)
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Enable a simple broker to send messages to clients subscribing to specific topics
        config.enableSimpleBroker("/topic");

        // Set the prefix for the target endpoints of the application
        config.setApplicationDestinationPrefixes("/app");
    }

    // Registration of endpoints for the STOMP protocol
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Add "/websocket-endpoint" endpoint with SockJS support
        registry
                .addEndpoint("/websocket-endpoint")
                .setAllowedOriginPatterns("*")  // Enabling access from all sources
                .withSockJS();
    }

}
