package pl.sebastianbrzustowicz.robotTaskerAPI.SingleWebSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import pl.sebastianbrzustowicz.robotTaskerAPI.model.VehicleData;
import pl.sebastianbrzustowicz.robotTaskerAPI.repository.WebSocketHandlerRepository;

import java.io.IOException;

@Component
public class WebSocketHandler extends AbstractWebSocketHandler {

    private final WebSocketHandlerRepository webSocketHandlerRepository;
    private VehicleData vehicleData;

    @Autowired
    public WebSocketHandler(WebSocketHandlerRepository webSocketHandlerRepository) {
        this.webSocketHandlerRepository = webSocketHandlerRepository;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String msg = String.valueOf(message.getPayload());
        System.out.println("MESSAGE RECEIVED: " + msg);
        if (msg.startsWith("vehicleId: ") && msg.length() == 47) {
            String vehicleId = webSocketHandlerRepository.getLast36Chars(msg);
            Boolean isVehicleIdStored = webSocketHandlerRepository.createSessionVehicle(vehicleId);
            session.sendMessage(new TextMessage(isVehicleIdStored.toString()));
            return;
        }


        session.sendMessage(new TextMessage("Runtime message"));
    }
}