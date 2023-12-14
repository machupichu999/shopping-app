package org.shopping.server.service;

import org.shopping.server.api.model.RegistrationBody;
import org.shopping.server.model.LocalUser;
import org.shopping.server.model.dao.LocalUserDAO;
import org.springframework.stereotype.Service;
import org.shopping.server.exception.UserAlreadyExistsException;

@Service
public class UserService {

    private LocalUserDAO localUserDAO;

    public UserService(LocalUserDAO localUserDAO) {
        this.localUserDAO = localUserDAO;
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

        // TODO: ENCRYPT PASSWORD
        user.setPassword(registrationBody.getPassword());
        return localUserDAO.save(user);
    }
}
