package org.shopping.server.service;

import org.shopping.server.api.model.LoginBody;
import org.shopping.server.api.model.RegistrationBody;
import org.shopping.server.model.LocalUser;
import org.shopping.server.model.dao.LocalUserDAO;
import org.springframework.stereotype.Service;
import org.shopping.server.exception.UserAlreadyExistsException;

import java.util.Optional;

@Service
public class UserService {

    private final LocalUserDAO localUserDAO;
    private final EncryptionService encryptionService;
    private final JWTService jwtService;

    public UserService(LocalUserDAO localUserDAO, EncryptionService encryptionService, JWTService jwtService) {
        this.localUserDAO = localUserDAO;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
    }

    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException {
        if (
                localUserDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent() ||
                localUserDAO.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()
        ) {
            throw new UserAlreadyExistsException();
        }

        LocalUser user = new LocalUser();
        user.setUsername(registrationBody.getUsername());
        user.setEmail(registrationBody.getEmail());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));

        return localUserDAO.save(user);
    }

    public String loginUser(LoginBody loginBody) {
        Optional<LocalUser> opUser = localUserDAO.findByUsernameIgnoreCase(loginBody.getUsername());

        if (opUser.isPresent()) {
            LocalUser user = opUser.get();

            if (encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
                return jwtService.generateJWT(user);
            }
        }

        return null;
    }
}
