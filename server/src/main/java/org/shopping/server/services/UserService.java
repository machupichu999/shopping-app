package org.shopping.server.services;

import org.shopping.server.models.User;
import org.shopping.server.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User addUser(User user) {
        return repository.save(user);
    }

    public List<User> findAllUsers() {
        return (List<User>) repository.findAll();
    }

    public User findUserById(Integer id) {
        Optional<User> existingUserOptional = repository.findById(id);

        if (existingUserOptional.isEmpty()) {
            throw new IllegalArgumentException("User with id " + id + " not found");
        }

        return existingUserOptional.get();
    }

    public User updateUser(Integer id, Map<String, Object> updates) {
        Optional<User> existingUserOptional = repository.findById(id);

        if (existingUserOptional.isEmpty()) {
            throw new IllegalArgumentException("User with id " + id + " not found");
        }

        User existingUser = existingUserOptional.get();

        updates.forEach((key, value) -> {
            switch (key) {
                case "firstName":
                    existingUser.setFirstName((String) value);
                    break;
                case "lastName":
                    existingUser.setLastName((String) value);
                case "email":
                    existingUser.setEmail((String) value);
                    break;
                default:
                    break;
            }
        });

        return repository.save(existingUser);
    }

    public void deleteUser(Integer id) {
        repository.deleteById(id);
    }
}
