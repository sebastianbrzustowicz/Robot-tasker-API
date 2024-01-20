package pl.sebastianbrzustowicz.robotTaskerAPI.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WebSocketSingleRoomHandlerRepository {

    private final JdbcTemplate jdbcTemplate;
    private String vehicleId;

    @Autowired
    public WebSocketSingleRoomHandlerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getLast36Chars(String msg) {
        String result = "";

        if (msg.length() >= 36) {
            result = msg.substring(msg.length() - 36);
        } else {
            System.out.println("A message of inappropriate length.");
        }
        return result;
    }

    public boolean createSessionVehicle(String vehicleId) {
        this.vehicleId = vehicleId;

        return true;
    }
}