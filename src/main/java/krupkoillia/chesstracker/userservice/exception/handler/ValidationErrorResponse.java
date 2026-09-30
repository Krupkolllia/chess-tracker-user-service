package krupkoillia.chesstracker.userservice.exception.handler;

import java.util.List;
import java.util.Map;

public record ValidationErrorResponse(
        int status,
        String message,
        Map<String, List<String>> errors
) {}
