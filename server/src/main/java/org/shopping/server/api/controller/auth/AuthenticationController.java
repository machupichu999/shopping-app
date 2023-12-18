package org.shopping.server.api.controller.auth;

import jakarta.validation.Valid;
import org.shopping.server.api.model.LoginBody;
import org.shopping.server.api.model.LoginResponse;
import org.shopping.server.api.model.RegistrationBody;
import org.shopping.server.api.model.ProfileInfoBody;
import org.shopping.server.exception.UserAlreadyExistsException;
import org.shopping.server.model.LocalUser;
import org.shopping.server.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegistrationBody registrationBody) {
        try {
            userService.registerUser(registrationBody);
            return ResponseEntity.ok().build();
        } catch (UserAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginBody loginBody) {
        Optional<String> jwtOp = userService.loginUser(loginBody);

        if (jwtOp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            LoginResponse response = new LoginResponse();
            response.setJwt(jwtOp.get());
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/me")
    public LocalUser getLoggedInUserProfile(@AuthenticationPrincipal LocalUser user) {
        return user;
    }

    @DeleteMapping("/me")
    public ResponseEntity deleteUser(@AuthenticationPrincipal LocalUser user) {
        userService.deleteUser(user.getId());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/me")
    public ResponseEntity updateUser(
            @AuthenticationPrincipal LocalUser user,
            @Valid @RequestBody ProfileInfoBody profileInfoBody
    ) {
        userService.updateUser(user, profileInfoBody);
        return ResponseEntity.ok().build();
    }
}
