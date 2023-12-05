package com.example.api.service.internal;

import com.example.api.config.JwtConfig;
import com.example.api.dto.JwtDto;
import com.example.api.dto.LoginUserDto;
import com.example.api.dto.RegisterUserDto;
import com.example.api.model.User;
import com.example.api.repository.UserRepository;
import com.example.api.service.AuthUseCases;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
class AuthService implements AuthUseCases, UserDetailsService {

    private static final String USER_NOT_FOUND_ERROR_MESSAGE = "User not found";
    private static final String USER_ALREADY_EXISTS_ERROR_MESSAGE = "User already exists";
    private static final String PASSWORD_DOES_NOT_MATCH_ERROR_MESSAGE = "Password does not match";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;

    @Override
    public JwtDto login(LoginUserDto loginUserDto) {
        final var user = userRepository.findByUsername(loginUserDto.username())
                .orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND_ERROR_MESSAGE));
        checkPasswordsMatch(loginUserDto.password(), user.getPassword());
        final var jwt = createAndEncodeJwt(user);
        return new JwtDto(jwt);
    }

    @Override
    public void register(RegisterUserDto registerUserDto) {
        checkIfUserAlreadyExists(registerUserDto.username());
        final var encodedPassword = passwordEncoder.encode(registerUserDto.password());
        final var user = new User(registerUserDto.username(), encodedPassword);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERROR_MESSAGE));
    }

    private void checkPasswordsMatch(String password, String encodedPassword) {
        if (!passwordEncoder.matches(password, encodedPassword)) {
            throw new IllegalStateException(PASSWORD_DOES_NOT_MATCH_ERROR_MESSAGE);
        }
    }

    private String createAndEncodeJwt(User user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("authorities", user.getAuthorities())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))
                .signWith(jwtConfig.getSecretKey())
                .compact();
    }

    private void checkIfUserAlreadyExists(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalStateException(USER_ALREADY_EXISTS_ERROR_MESSAGE);
        }
    }

}
