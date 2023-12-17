package pl.sebastianbrzustowicz.CrudApp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String userId;
    private String login;
    private String password;
    private String email;
    private int phoneNumber;
    private String role;
    private LocalDate accCreated;

    public User(int id, String login, String password, String email, int phoneNumber, String vehicleId, String role, LocalDate accCreated) {
        this.userId = generateRandomId();
        this.login = login;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.accCreated = accCreated;
    }

    private String generateRandomId() {
        // Generating random UUID
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    // Getting user's variables
    public String getUserId() { return this.userId; }
    public String getLogin() { return this.login; }
    public String getPassword() { return this.password; }
    public String getEmail() { return this.email; }
    public int getPhoneNumber() { return this.phoneNumber; }
    public String getRole() { return this.role; }
    public LocalDate getAccCreated() { return this.accCreated; }

    // Setting user's variables
    public void setLogin(String newLogin) { this.login = newLogin; }
    public void setPassword(String newPassword) { this.password = newPassword; }
    public void setEmail(String newEmail) { this.email = newEmail; }
    public void setPhoneNumber(int newPhoneNumber) { this.phoneNumber = newPhoneNumber; }

    // Adding user's variables data
    //public void addVehicle(String vehicleId) { this.vehicles.add(vehicleId); }

    // Removing user's variables data
    //public void deleteVehicle(String vehicleId) { this.vehicles.remove(vehicleId); }

}
