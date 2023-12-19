package org.shopping.server.api.controller.auth;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.shopping.server.api.controller.auth.AuthenticationController;
import org.shopping.server.api.model.LoginBody;
import org.shopping.server.api.model.LoginResponse;
import org.shopping.server.api.model.RegistrationBody;
import org.shopping.server.exception.UserAlreadyExistsException;
import org.shopping.server.model.LocalUser;
import org.shopping.server.service.UserService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private UserService userService;

    @Test
    void registerUser_Success() throws UserAlreadyExistsException {
        RegistrationBody registrationBody = createRegistrationBody();

        // Mocking the userService to return success
        when(userService.registerUser(any())).thenReturn(new LocalUser());

        // Performing the test
        ResponseEntity responseEntity = authenticationController.registerUser(registrationBody);

        // Verifying the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void registerUser_UserAlreadyExists() throws UserAlreadyExistsException {
        RegistrationBody registrationBody = createRegistrationBody();

        // Mocking the userService to throw UserAlreadyExistsException
        when(userService.registerUser(any())).thenThrow(UserAlreadyExistsException.class);

        // Performing the test
        ResponseEntity responseEntity = authenticationController.registerUser(registrationBody);

        // Verifying the response
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    }

    @Test
    void loginUser_Success() throws Exception {
        LoginBody loginBody = createLoginBody();

        // Mockowanie userService dla przypadku udanego logowania
        when(userService.loginUser(any())).thenReturn(Optional.of("fakeJWT"));

        // Wywołanie testowanej metody
        ResponseEntity<LoginResponse> responseEntity = authenticationController.loginUser(loginBody);

        // Weryfikacja odpowiedzi
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("fakeJWT", responseEntity.getBody().getJwt());
    }

    @Test
    void loginUser_BadRequest() throws Exception {
        LoginBody loginBody = createLoginBody();

        // Mockowanie userService dla przypadku braku użytkownika w bazie danych
        when(userService.loginUser(any())).thenReturn(Optional.empty());

        // Wywołanie testowanej metody
        ResponseEntity<LoginResponse> responseEntity = authenticationController.loginUser(loginBody);

        // Weryfikacja odpowiedzi
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    private RegistrationBody createRegistrationBody() {
        RegistrationBody registrationBody = new RegistrationBody();
        registrationBody.setUsername("testUser");
        registrationBody.setEmail("test@example.com");
        registrationBody.setPassword("testPassword");
        registrationBody.setFirstName("John");
        registrationBody.setLastName("Doe");
        return registrationBody;
    }

    private LoginBody createLoginBody() {
        LoginBody loginBody = new LoginBody();
        loginBody.setUsername("testUser");
        loginBody.setPassword("testPassword");
        return loginBody;
    }
}
