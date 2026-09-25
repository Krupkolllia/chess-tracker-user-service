package krupkoillia.chesstracker.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import krupkoillia.chesstracker.userservice.dto.ChangePasswordRequestDto;
import krupkoillia.chesstracker.userservice.dto.UserResponseDto;
import krupkoillia.chesstracker.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Users management")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get current user")
    @GetMapping("/me")
    public UserResponseDto getMe() {
        return userService.getMe();
    }

    @Operation(summary = "Change username")
    @PutMapping("/me/username")
    public UserResponseDto changeUsername(@RequestBody @NotBlank String username) {
        return userService.changeUsername(username);
    }

    @Operation(summary = "Change password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/me/password")
    public void changePassword(@RequestBody @Valid ChangePasswordRequestDto requestDto) {
        userService.changePassword(requestDto);
    }

    @Operation(summary = "Delete user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/me")
    public void deleteMe() {
        userService.delete();
    }

}
