package pl.sebastianbrzustowicz.CrudApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/rest")
public class UserController {

    @Autowired
    UserRepository userRepository;

    // RETURN: rows affected
    @PostMapping("/user/register")
    public int registerUser(@RequestBody User newUser) {
        boolean isExisting = userRepository.existsByEmail(newUser.getEmail());
        if (!isExisting) { return userRepository.registerUser(newUser); }
        else { return 0; }
    }

    // RETURN: rows affected
    @DeleteMapping("/user/delete/{userId}")
    public int deleteUser(@PathVariable UUID userId) {
        return userRepository.deleteUser(userId);
    }

    // RETURN: String status with UUID if successed
    @PostMapping("/user/login")
    public String loginUser(@RequestBody Map<String, String> UserLogin) {
        String email = UserLogin.get("email");
        String password = UserLogin.get("password");
        boolean loginSuccess = userRepository.loginUser(email, password);
        if (loginSuccess) {
            String identifier = userRepository.getUserUUID(email, password);
            return "Logged in, your UUID is: " + identifier; }
        else { return "Login failed"; }
    }

    @PostMapping("/vehicle/register")
    public String registerVehicle(@RequestBody Map<String, String> UUIDs) {
        String userId = UUIDs.get("userId");
        String vehicleId = UUIDs.get("vehicleId");
        int registerSuccess = userRepository.registerVehicle(userId, vehicleId);
        if (registerSuccess == 1) { return "Registration successful"; }
        else { return "Registration failed"; }
    }

    // RETURN: List of vehicles
    @GetMapping("/vehicle/information")
    public List<Vehicle> vehicleInformation(@RequestParam String userId) {
        return userRepository.vehicleInformation(userId);
    }

    @PostMapping("/vehicle/delete")
    public String deleteVehicle(@RequestBody Map<String, String> requestBody) {
        String vehicleId = requestBody.get("vehicleId");
        int isVehicleDeleted = userRepository.deleteVehicle(vehicleId);
        if (isVehicleDeleted == 1) { return "Deleted vehicle with ID: " + vehicleId; }
        else { return "Cannot delete"; }
    }

    @PatchMapping("/user/changedata")
    public String changeUserData(@RequestBody User updatedUser) {
        String userId = updatedUser.getUserId();
        String newPassword = updatedUser.getPassword();

        int count = userRepository.userExists(userId);

        if (count > 0) {
            userRepository.changeData(userId, newPassword);
            return "Password updated successfully";
        } else {
            return "User not found";
        }
    }

    @PutMapping("/admin/changedata")
    public String changeUserDataByAdmin(@RequestBody User updatedUser) {
        String userId = updatedUser.getUserId();
        String newPassword = updatedUser.getPassword();

        int count = userRepository.userExists(userId);

        if (count > 0) {
            userRepository.changeDataByAdmin(updatedUser);
            return "Password updated successfully";
        } else {
            return "User not found";
        }
    }

    @PostMapping("/vehicle/custom/register")
    public String registerCustomVehicle(@RequestBody Vehicle customVehicle) {
        int registerSuccess = userRepository.registerCustomVehicle(customVehicle);
        if (registerSuccess == 1) { return "Registration successful"; }
        else { return "Registration failed"; }
    }

}
