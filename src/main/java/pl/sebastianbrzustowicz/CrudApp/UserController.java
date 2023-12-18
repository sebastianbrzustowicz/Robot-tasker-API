package pl.sebastianbrzustowicz.CrudApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("")
public class UserController {

    @Autowired
    UserRepository userRepository;

    // RETURN: rows affected
    @PostMapping("/register")
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

    // RETURN: logged in == 1 else 0
    @PostMapping("/login")
    public String loginUser(@RequestBody Map<String, String> UserLogin) {
        String email = UserLogin.get("email");
        String password = UserLogin.get("password");
        boolean loginSuccess = userRepository.loginUser(email, password);
        if (loginSuccess) {
            String identifier = userRepository.getUserUUID(email, password);
            return "Logged in, your UUID is: " + identifier; }
        else { return "Login failed"; }
    }

}
