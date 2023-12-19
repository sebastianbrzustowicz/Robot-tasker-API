package pl.sebastianbrzustowicz.CrudApp;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // Konfiguracja brokera wiadomości (STOMP)
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Włącz prosty broker umożliwiający wysyłanie wiadomości do klientów subskrybujących konkretne tematy
        config.enableSimpleBroker("/topic");

        // Ustaw prefix dla docelowych punktów końcowych aplikacji
        config.setApplicationDestinationPrefixes("/app");
    }

    // Rejestracja punktów końcowych dla protokołu STOMP
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Dodaj punkt końcowy "/websocket-endpoint" z obsługą SockJS
        registry
                .addEndpoint("/websocket-endpoint")
                .setAllowedOriginPatterns("*")  // Umożliwienie dostępu od wszystkich źródeł
                .withSockJS();
    }

}
