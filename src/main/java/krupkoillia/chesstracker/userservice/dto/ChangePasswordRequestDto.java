package krupkoillia.chesstracker.userservice.dto;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequestDto(
    @NotBlank String oldPassword,
    @NotBlank String newPassword
) {}
