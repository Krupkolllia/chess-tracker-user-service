package krupkoillia.chesstracker.userservice.dto;

public record UserResponseDto(
        Long id,
        String username,
        String email
) {}
