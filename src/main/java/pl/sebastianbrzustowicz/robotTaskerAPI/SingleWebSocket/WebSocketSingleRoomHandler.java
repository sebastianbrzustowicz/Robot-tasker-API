package pl.sebastianbrzustowicz.robotTaskerAPI.SingleWebSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import pl.sebastianbrzustowicz.robotTaskerAPI.model.VehicleData;
import pl.sebastianbrzustowicz.robotTaskerAPI.repository.WebSocketSingleRoomHandlerRepository;

import java.io.IOException;

@Component
public class WebSocketSingleRoomHandler extends AbstractWebSocketHandler {

    private final WebSocketSingleRoomHandlerRepository webSocketSingleRoomHandlerRepository;
    // vehicleData singleton
    VehicleData vehicleData = VehicleData.getInstance();

    @Autowired
    public WebSocketSingleRoomHandler(WebSocketSingleRoomHandlerRepository webSocketSingleRoomHandlerRepository) {
        this.webSocketSingleRoomHandlerRepository = webSocketSingleRoomHandlerRepository;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String msg = String.valueOf(message.getPayload());
        System.out.println("MESSAGE RECEIVED:\n" + msg);
        if (msg.startsWith("vehicleId: ") && msg.length() == 47) {
            String vehicleId = webSocketSingleRoomHandlerRepository.getLast36Chars(msg);
            Boolean isVehicleIdStored = webSocketSingleRoomHandlerRepository.createSessionVehicle(vehicleId);
            session.sendMessage(new TextMessage("vehicle session created: " + isVehicleIdStored.toString()));
            return;
        }

        if (msg.startsWith("CLIENT")) {
            String[] lines = msg.split("\n");
            String vehicleId = lines[1];
            int mode = Integer.parseInt(lines[2]);
            int vtol = Integer.parseInt(lines[3]);
            int x = Integer.parseInt(lines[4]);
            int y = Integer.parseInt(lines[5]);
            int altitude = Integer.parseInt(lines[6]);
            int yaw = Integer.parseInt(lines[7]);
            boolean camTrig = Boolean.parseBoolean(lines[8]);
            boolean camTog = Boolean.parseBoolean(lines[9]);
            int camPitch = Integer.parseInt(lines[10]);
            boolean clamp = Boolean.parseBoolean(lines[11]);
            vehicleData.saveDesiredValues(vehicleId, mode, vtol, x, y, altitude, yaw, camTrig, camTog, camPitch, clamp);
            // Send actual data from sensors
            session.sendMessage(new TextMessage(vehicleData.getSensorsFrame()));
            return;
        }

        if (msg.startsWith("VEHICLE")) {
            String[] lines = msg.split("\n");
            int altitude = Integer.parseInt(lines[1]);
            vehicleData.saveSensorsValues(altitude);
            // Send actual desired values to vehicle
            session.sendMessage(new TextMessage(vehicleData.getDesiredFrame()));
            return;
        }

        session.sendMessage(new TextMessage("MESSAGE NOT CLASSIFIED"));
    }
}