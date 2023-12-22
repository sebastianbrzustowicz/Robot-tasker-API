package pl.sebastianbrzustowicz.CrudApp;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

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

}