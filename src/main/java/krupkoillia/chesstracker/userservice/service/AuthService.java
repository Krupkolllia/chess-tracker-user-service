package krupkoillia.chesstracker.userservice.service;

import java.util.Locale;
import krupkoillia.chesstracker.userservice.dto.LoginRequestDto;
import krupkoillia.chesstracker.userservice.dto.LoginResponseDto;
import krupkoillia.chesstracker.userservice.dto.RegistrationRequestDto;
import krupkoillia.chesstracker.userservice.dto.UserResponseDto;
import krupkoillia.chesstracker.userservice.exception.EmailAlreadyInUseException;
import krupkoillia.chesstracker.userservice.mapper.UserMapper;
import krupkoillia.chesstracker.userservice.model.User;
import krupkoillia.chesstracker.userservice.repository.UserRepository;
import krupkoillia.chesstracker.userservice.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Transactional
    public UserResponseDto register(RegistrationRequestDto requestDto) {
        String email = normalizeEmail(requestDto.email());

        if (userRepository.existsByEmail(requestDto.email())) {
            throw new EmailAlreadyInUseException(
                    "An account with this email already exists");
        }

        User user = userMapper.toModel(requestDto);

        user.setPassword(passwordEncoder.encode(requestDto.password()));

        userRepository.save(user);

        return userMapper.toDto(user);
    }

    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginRequestDto requestDto) {
        String email = normalizeEmail(requestDto.email());

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    email, requestDto.password()
            )
        );

        if (!(authentication.getPrincipal() instanceof User user)) {
            throw new IllegalStateException(
                    "Authentication principal is invalid");
        }

        String accessToken = jwtService.generateAccessToken(user.getId());

        return new LoginResponseDto(accessToken);
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }

}
