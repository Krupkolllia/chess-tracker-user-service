package krupkoillia.chesstracker.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequestDto(

        @NotBlank
        @Size(min = 8)
        String oldPassword,

        @NotBlank
        @Size(min = 8)
        String newPassword
) {}
