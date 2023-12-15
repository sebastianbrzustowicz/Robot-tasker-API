package pl.sebastianbrzustowicz.CrudApp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String login;
    private String password;
    private String email;
    private int phoneNumber;
    private List<String> vehicles = new ArrayList<>();

    public User(int id, String login, String password, String email, int phoneNumber, String vehicleId) {
        this.id = generateRandomId();
        this.login = login;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.vehicles.add(vehicleId);
    }

    private Integer generateRandomId() {
        // Generating random id number
        Random random = new Random();
        return random.nextInt(1000000000);
    }

    // Getting user's variables
    public int getId() { return this.id; }
    public String getLogin() { return this.login; }
    public String getPassword() { return this.password; }
    public String getEmail() {
        return this.email;
    }
    public int getPhoneNumber() { return this.phoneNumber; }
    public List<String> getVehicles() { return vehicles; }

    // Setting user's variables
    public void setLogin(String newLogin) { this.login = newLogin; }
    public void setPassword(String newPassword) { this.password = newPassword; }
    public void setEmail(String newEmail) { this.email = newEmail; }
    public void setPhoneNumber(int newPhoneNumber) { this.phoneNumber = newPhoneNumber; }

    // Adding user's variables data
    public void addVehicle(String vehicleId) { this.vehicles.add(vehicleId); }

    // Removing user's variables data
    public void deleteVehicle(String vehicleId) { this.vehicles.remove(vehicleId); }

}
