package org.yaremax.springsecurityjwtpractice.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.yaremax.springsecurityjwtpractice.auth.records.LoginRequest;
import org.yaremax.springsecurityjwtpractice.auth.records.AuthenticationResponse;
import org.yaremax.springsecurityjwtpractice.auth.records.RegisterRequest;
import org.yaremax.springsecurityjwtpractice.jwt.JwtService;
import org.yaremax.springsecurityjwtpractice.user.Role;
import org.yaremax.springsecurityjwtpractice.user.UserDetailsImpl;
import org.yaremax.springsecurityjwtpractice.user.UserEntity;
import org.yaremax.springsecurityjwtpractice.user.UserService;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        UserEntity userEntity = UserEntity.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();
        userService.addUser(userEntity);

        String jwtToken = jwtService.generateToken(new UserDetailsImpl(userEntity));
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse login(LoginRequest request) {
        // will also check if username and password are correct
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password())
        );

        UserEntity userEntity = userService.findUserByEmail(request.email());

        String jwtToken = jwtService.generateToken(new UserDetailsImpl(userEntity));
        return new AuthenticationResponse(jwtToken);
    }
}
