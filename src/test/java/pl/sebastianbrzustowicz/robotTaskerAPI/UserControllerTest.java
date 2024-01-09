package pl.sebastianbrzustowicz.robotTaskerAPI;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sebastianbrzustowicz.robotTaskerAPI.controller.UserController;
import pl.sebastianbrzustowicz.robotTaskerAPI.model.User;
import pl.sebastianbrzustowicz.robotTaskerAPI.model.Vehicle;
import pl.sebastianbrzustowicz.robotTaskerAPI.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @Test
    public void testRegisterUser_SuccessfulRegistration() {
        // Given
        User newUser = new User(UUID.randomUUID().toString(), "loginTest", "pwTest",
                "test@example.com", 666777888, "user", "dateExample" );
        Mockito.when(userRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
        Mockito.when(userRepository.registerUser(Mockito.any(User.class))).thenReturn(1);

        // When
        int result = userController.registerUser(newUser);
        int expected = 1;
        // Then
        assertEquals(expected, result);
    }

    @Test
    public void testRegisterUser_FailedRegistration() {
        // Given
        User newUser = new User(UUID.randomUUID().toString(), "loginTest", "pwTest",
                "test@example.com", 666777888, "user", "dateExample" );
        Mockito.when(userRepository.existsByEmail(Mockito.anyString())).thenReturn(true);
        Mockito.when(userRepository.registerUser(Mockito.any(User.class))).thenReturn(0);

        // When
        int result = userController.registerUser(newUser);
        int expected = 0;
        // Then
        assertEquals(expected, result);
    }

    @Test
    public void testLoginUser_SuccessfulLogin() {
        // Given
        Mockito.when(userRepository.loginUser(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        Mockito.when(userRepository.getUserUUID(Mockito.anyString(), Mockito.anyString())).thenReturn("123456789");
        String email = "test@example.com";
        String password = "pwTest";
        Map<String, String> userLogin = new HashMap<>();
        userLogin.put("email", email);
        userLogin.put("password", password);

        // When
        String result = userController.loginUser(userLogin);
        String expected = "Logged in, your UUID is: 123456789";
        // Then
        assertEquals(expected, result);
    }

    @Test
    public void testLoginUser_FailedLogin() {
        // Given
        Mockito.when(userRepository.loginUser(Mockito.anyString(), Mockito.anyString())).thenReturn(false);
        Mockito.when(userRepository.getUserUUID(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
        String email = "test@example.com";
        String password = "pwTest";
        Map<String, String> userLogin = new HashMap<>();
        userLogin.put("email", email);
        userLogin.put("password", password);

        // When
        String result = userController.loginUser(userLogin);
        String expected = "Login failed";
        // Then
        assertEquals(expected, result);
    }

    @Test
    public void testRegisterVehicle_SuccessfulRegistration() {
        // Given
        Mockito.when(userRepository.registerVehicle(Mockito.anyString(), Mockito.anyString())).thenReturn(1);
        String userId = "123456789";
        String vehicleId = "987654321";
        Map<String, String> UUIDs = new HashMap<>();
        UUIDs.put("userId", userId);
        UUIDs.put("vehicleId", vehicleId);

        // When
        String result = userController.registerVehicle(UUIDs);
        String expected = "Registration successful";
        // Then
        assertEquals(expected, result);
    }

    @Test
    public void testRegisterVehicle_FailedRegistration() {
        // Given
        Mockito.when(userRepository.registerVehicle(Mockito.anyString(), Mockito.anyString())).thenReturn(0);
        String userId = "123456789";
        String vehicleId = "987654321";
        Map<String, String> UUIDs = new HashMap<>();
        UUIDs.put("userId", userId);
        UUIDs.put("vehicleId", vehicleId);

        // When
        String result = userController.registerVehicle(UUIDs);
        String expected = "Registration failed";
        // Then
        assertEquals(expected, result);
    }

    @Test
    public void testDeleteVehicle_SuccessfulDeletion() {
        // Given
        Mockito.when(userRepository.deleteVehicle(Mockito.anyString())).thenReturn(1);
        String vehicleId = "123456789";
        Map<String, String> data = new HashMap<>();
        data.put("vehicleId", vehicleId);

        // When
        String result = userController.deleteVehicle(data);
        String expected = "Deleted vehicle with ID: " + vehicleId;
        // Then
        assertEquals(expected, result);
    }

    @Test
    public void testDeleteVehicle_FailedDeletion() {
        // Given
        Mockito.when(userRepository.deleteVehicle(Mockito.anyString())).thenReturn(0);
        String vehicleId = "123456789";
        Map<String, String> data = new HashMap<>();
        data.put("vehicleId", vehicleId);

        // When
        String result = userController.deleteVehicle(data);
        String expected = "Cannot delete";
        // Then
        assertEquals(expected, result);
    }

    @Test
    public void testChangeUserData_SuccessfulChange() {
        // Given
        Mockito.when(userRepository.userExists(Mockito.anyString())).thenReturn(1);
        Mockito.when(userRepository.changeData(Mockito.anyString(), Mockito.anyString())).thenReturn(1);
        User updatedUser = new User(UUID.randomUUID().toString(), "loginTest", "pwTest",
                "test@example.com", 666777888, "user", "dateExample" );

        // When
        String result = userController.changeUserData(updatedUser);
        String expected = "Password updated successfully";
        // Then
        assertEquals(expected, result);
    }

    @Test
    public void testChangeUserData_FailedChange() {
        // Given
        Mockito.when(userRepository.userExists(Mockito.anyString())).thenReturn(0);
        Mockito.when(userRepository.changeData(Mockito.anyString(), Mockito.anyString())).thenReturn(1);
        User updatedUser = new User(UUID.randomUUID().toString(), "loginTest", "pwTest",
                "test@example.com", 666777888, "user", "dateExample" );

        // When
        String result = userController.changeUserData(updatedUser);
        String expected = "User not found";
        // Then
        assertEquals(expected, result);
    }

    @Test
    public void testChangeUserDataByAdmin_SuccessfulChange() {
        // Given
        Mockito.when(userRepository.userExists(Mockito.anyString())).thenReturn(1);
        Mockito.when(userRepository.changeDataByAdmin(Mockito.any(User.class))).thenReturn(1);
        User updatedUser = new User(UUID.randomUUID().toString(), "loginTest", "pwTest",
                "test@example.com", 666777888, "user", "dateExample" );

        // When
        String result = userController.changeUserDataByAdmin(updatedUser);
        String expected = "Password updated successfully";
        // Then
        assertEquals(expected, result);
    }

    @Test
    public void testChangeUserDataByAdmin_FailedChange() {
        // Given
        Mockito.when(userRepository.userExists(Mockito.anyString())).thenReturn(0);
        Mockito.when(userRepository.changeDataByAdmin(Mockito.any(User.class))).thenReturn(0);
        User updatedUser = new User(UUID.randomUUID().toString(), "loginTest", "pwTest",
                "test@example.com", 666777888, "user", "dateExample" );

        // When
        String result = userController.changeUserDataByAdmin(updatedUser);
        String expected = "User not found";
        // Then
        assertEquals(expected, result);
    }

    @Test
    public void testRegisterCustomVehicle_SuccessfulRegister() {
        // Given
        Mockito.when(userRepository.registerCustomVehicle(Mockito.any(Vehicle.class))).thenReturn(1);
        Vehicle customVehicle = new Vehicle(UUID.randomUUID().toString(), UUID.randomUUID().toString(), "Name",
                "Type", "Time" );

        // When
        String result = userController.registerCustomVehicle(customVehicle);
        String expected = "Registration successful";
        // Then
        assertEquals(expected, result);
    }

    @Test
    public void testRegisterCustomVehicle_FailedRegister() {
        // Given
        Mockito.when(userRepository.registerCustomVehicle(Mockito.any(Vehicle.class))).thenReturn(0);
        Vehicle customVehicle = new Vehicle(UUID.randomUUID().toString(), UUID.randomUUID().toString(), "Name",
                "Type", "Time" );

        // When
        String result = userController.registerCustomVehicle(customVehicle);
        String expected = "Registration failed";
        // Then
        assertEquals(expected, result);
    }

}
