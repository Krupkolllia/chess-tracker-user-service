package krupkoillia.chesstracker.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import krupkoillia.chesstracker.userservice.dto.LoginRequestDto;
import krupkoillia.chesstracker.userservice.dto.LoginResponseDto;
import krupkoillia.chesstracker.userservice.dto.RegistrationRequestDto;
import krupkoillia.chesstracker.userservice.dto.UserResponseDto;
import krupkoillia.chesstracker.userservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Register new user")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public UserResponseDto register(
            @RequestBody @Valid RegistrationRequestDto requestDto) {
        return authService.register(requestDto);
    }

    @Operation(summary = "Login")
    @PostMapping("/login")
    public LoginResponseDto login(
            @RequestBody @Valid LoginRequestDto requestDto) {
        return authService.login(requestDto);
    }

}
