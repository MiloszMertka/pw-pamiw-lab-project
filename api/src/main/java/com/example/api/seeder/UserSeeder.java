package com.example.api.seeder;

import com.example.api.model.User;
import com.example.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSeeder implements Seeder {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void seed(int objectsToSeed) {
        if (objectsToSeed != 1) {
            throw new IllegalArgumentException("Cannot other than one user");
        }

        final var encodedPassword = passwordEncoder.encode("password");
        final var user = new User("admin", encodedPassword);
        userRepository.save(user);
    }

}
