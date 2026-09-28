package krupkoillia.chesstracker.userservice.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Objects;

public record RegistrationRequestDto(

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 8)
        String password,

        @NotBlank
        @Size(min = 8)
        String confirmPassword,

        @NotBlank
        @Size(min = 3, max = 24)
        String displayName
) {

    @AssertTrue(message = "Passwords do not match")
    public boolean isPasswordsMatch() {
        return Objects.equals(password, confirmPassword);
    }

}
