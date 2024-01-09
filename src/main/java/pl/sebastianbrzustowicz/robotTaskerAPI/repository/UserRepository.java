package pl.sebastianbrzustowicz.robotTaskerAPI.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.sebastianbrzustowicz.robotTaskerAPI.model.User;
import pl.sebastianbrzustowicz.robotTaskerAPI.model.Vehicle;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, Integer.class, email))
                .map(count -> count > 0)
                .orElse(false);
    }

    public int registerUser(User newUser) {
        String sql = "INSERT INTO users (userId, login, password, email, phoneNum, role, accCreated) " +
                "VALUES (UUID(), ?, ?, ?, ?, ?, CONVERT_TZ(NOW(), 'UTC', 'Europe/Warsaw'));";
        return jdbcTemplate.update(sql,
                newUser.getLogin(), newUser.getPassword(), newUser.getEmail(), newUser.getPhoneNumber(), "user"
        );
    }

    public int deleteUser(UUID userId) {
        return jdbcTemplate.update("DELETE FROM users WHERE userId=?", userId.toString());
    }

    public boolean loginUser(String email, String password) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ? AND password = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, email, password);
        return count == 1;
    }

    public String getUserUUID(String email, String password) {
        String sql = "SELECT userId FROM users WHERE email = ? AND password = ?";
        return jdbcTemplate.queryForObject(sql, String.class, email, password);
    }

    public int registerVehicle(String userId, String vehicleId) {
        String sql = """
                UPDATE vehicles
                SET
                  `userId` = ?,
                  `registrationTime` = CONVERT_TZ(NOW(), 'UTC', 'Europe/Warsaw')
                WHERE
                  `vehicleId` = ?;""";
        return jdbcTemplate.update(sql, userId, vehicleId);
    }

    public List<Vehicle> vehicleInformation(String userId) {
        String sql = "SELECT * FROM vehicles WHERE userId = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, new BeanPropertyRowMapper<>(Vehicle.class));
    }

    public int deleteVehicle(String vehicleId) {
        String sql = """
                UPDATE vehicles
                SET
                  `userId` = null,
                  `registrationTime` = null
                WHERE
                  `vehicleId` = ?;""";
        return jdbcTemplate.update(sql, vehicleId);
    }

    public int userExists(String userId) {
        String sql = "SELECT COUNT(*) FROM users WHERE userId = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, userId);
    }

    public int changeData(String userId, String newPassword) {
        String sql = "UPDATE users SET `password` = ? WHERE userId = ?";
        return jdbcTemplate.update(sql, newPassword, userId);
    }

    public int changeDataByAdmin(User updatedUser) {
        String sql = """
                UPDATE users
                SET
                  `login` = ?,
                  `password` = ?,
                  `email` = ?,
                  `phoneNum` = ?,
                  `role` = ?,
                  `accCreated` = ?
                WHERE
                  `userId` = ?;""";
        return jdbcTemplate.update(sql, updatedUser.getLogin(), updatedUser.getPassword(), updatedUser.getEmail(),
                updatedUser.getPhoneNumber(), updatedUser.getRole(), updatedUser.getAccCreated(), updatedUser.getUserId());
    }

    public int registerCustomVehicle(Vehicle customVehicle) {
        String sql = "INSERT INTO vehicles (vehicleId, userId, vehicleName, vehicleType, registrationTime) " +
                "VALUES (?, ?, ?, ?, CONVERT_TZ(NOW(), 'UTC', 'Europe/Warsaw'));";
        return jdbcTemplate.update(sql, customVehicle.getVehicleId(), customVehicle.getUserId(),
                customVehicle.getVehicleName(), customVehicle.getVehicleType()
        );
    }

}
