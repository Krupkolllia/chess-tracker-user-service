package krupkoillia.chesstracker.userservice.dto;

public record UserResponseDto(
        Long id,
        String email,
        String displayName
) {}
