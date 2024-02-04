package pl.sebastianbrzustowicz.robotTaskerAPI.SingleWebSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import pl.sebastianbrzustowicz.robotTaskerAPI.model.VehicleData;
import pl.sebastianbrzustowicz.robotTaskerAPI.repository.WebSocketSingleRoomHandlerRepository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        String[] lines = msg.split("\n");
        if (msg.startsWith("vehicleId: ") && msg.length() == 47) {
            String vehicleId = webSocketSingleRoomHandlerRepository.getLast36Chars(msg);
            Boolean isVehicleIdStored = webSocketSingleRoomHandlerRepository.createSessionVehicle(vehicleId);
            session.sendMessage(new TextMessage("vehicle session created: " + isVehicleIdStored.toString()));
            return;
        }

        if (msg.startsWith("CLIENT") && lines.length == 13) {
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

        if (msg.startsWith("VEHICLE") && lines.length == 7) {
            double roll = Double.parseDouble(lines[1]);
            double pitch = Double.parseDouble(lines[2]);
            double yaw = Double.parseDouble(lines[3]);
            double altitude = Double.parseDouble(lines[4]);
            boolean isClamp = Boolean.parseBoolean(lines[5]);
            vehicleData.saveSensorsValues(roll, pitch, yaw, altitude, isClamp);
            // Send actual desired values to vehicle
            session.sendMessage(new TextMessage(vehicleData.getDesiredFrame()));

            // saving data to file
            try {
                String filePath = "data.csv";
                saveToFile(filePath, vehicleData.getRolld(), vehicleData.getPitchd(), vehicleData.getYawd(), vehicleData.getAltituded(), roll, pitch, yaw, altitude, isClamp);
            } catch (IOException e) {
                System.err.println("Error during saving data to file: " + e.getMessage());
            }

            return;
        }

        session.sendMessage(new TextMessage("MESSAGE NOT CLASSIFIED"));
    }

    private static void saveToFile(String filePath, double rolld, double pitchd, double yawd,
                                   double altituded, double roll, double pitch, double yaw, double altitude, boolean isClamp) throws IOException {
        if (!Files.exists(Paths.get(filePath))) {
            Files.createFile(Paths.get(filePath));
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            // Save data in new line
            writer.write(rolld + ";" + pitchd + ";" + yawd + ";" +
                    altituded + ";" + roll + ";" + pitch + ";" +
                    yaw + ";" + altitude + ";" + isClamp);
            writer.newLine();
        }
    }

}