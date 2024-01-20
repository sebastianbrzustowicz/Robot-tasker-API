package pl.sebastianbrzustowicz.robotTaskerAPI.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class WebSocketController {

    private static final Logger logger = Logger.getLogger(WebSocketController.class.getName());

    private final SimpMessagingTemplate template;
    private final Map<String, String> userToChannelMap = new HashMap<>();

    public WebSocketController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/vehicle/connect")
    public void connectVehicle(String vehicleId) {
        //logger.info("SERVER ENDPOINT REACHED. PAYLOAD: " + vehicleId);
        String channel = "/topic/vehicle-status/" + vehicleId;
        userToChannelMap.put(vehicleId, channel);
        String message = "User connected: " + vehicleId;
        this.template.convertAndSend(channel, message);
    }

    @MessageMapping("/vehicle/disconnect")
    public void disconnectVehicle(String vehicleId) {
        String channel = userToChannelMap.get(vehicleId);
        userToChannelMap.remove(vehicleId);
        String message = "User disconnected: " + vehicleId;
        this.template.convertAndSend(channel, message);
    }

    @MessageMapping("/vehicle/data")
    public void sendVehicleData(String vehicleData) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(vehicleData);
            String vehicleId = jsonNode.get("vehicleId").asText();

            String channel = "/topic/vehicle-data/" + vehicleId;
            //if (channel != null) {
            logger.info("Channel: " + channel);
            logger.info("Data: " + vehicleData);
            this.template.convertAndSend(channel, vehicleData);
            //}
        } catch (Exception e) {
            logger.info(" Error with parsing ");
        }
    }
}