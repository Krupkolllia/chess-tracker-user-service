package krupkoillia.chesstracker.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangeDisplayNameRequestDto(

        @NotBlank
        @Size(min = 3, max = 24)
        String displayName

) {}
