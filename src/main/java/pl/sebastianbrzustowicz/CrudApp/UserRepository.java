package pl.sebastianbrzustowicz.CrudApp;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
                UPDATE crudapp.vehicles
                SET
                  `userId` = ?,
                  `registrationTime` = CONVERT_TZ(NOW(), 'UTC', 'Europe/Warsaw')
                WHERE
                  `vehicleId` = ?;""";
        return jdbcTemplate.update(sql, userId, vehicleId);
    }

}
