package krupkoillia.chesstracker.userservice.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import krupkoillia.chesstracker.userservice.exception.AuthenticatedUserNotFoundException;
import krupkoillia.chesstracker.userservice.exception.EmailAlreadyInUseException;
import krupkoillia.chesstracker.userservice.exception.WrongPasswordException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<ErrorResponse> handleWrongPassword(
            WrongPasswordException e, HttpServletRequest request
    ) {
        log.warn(
                "Password change failed: method={}, uri={}, exception={}",
                request.getMethod(),
                request.getRequestURI(),
                e.getClass().getSimpleName()
        );

        return buildResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(
            AuthenticationException e, HttpServletRequest request
    ) {
        log.warn(
                "Authentication failed: method={}, uri={}, exception={}",
                request.getMethod(),
                request.getRequestURI(),
                e.getClass().getSimpleName()
        );

        return buildResponse(
                HttpStatus.UNAUTHORIZED,
                "Invalid email or password"
        );
    }

    @ExceptionHandler(AuthenticatedUserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticatedUserNotFound(
            AuthenticatedUserNotFoundException e, HttpServletRequest request
    ) {
        log.error(
                "Authenticated user not found: method={}, uri={}, exception={}",
                request.getMethod(),
                request.getRequestURI(),
                e.getClass().getSimpleName(),
                e
        );

        return buildResponse(
                HttpStatus.UNAUTHORIZED,
                "Authentication is no longer valid"
        );
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyInUse(
            EmailAlreadyInUseException e, HttpServletRequest request
    ) {
        log.warn(
                "Registration rejected: method={}, uri={}, exception={}",
                request.getMethod(),
                request.getRequestURI(),
                e.getClass().getSimpleName()
        );

        return buildResponse(HttpStatus.CONFLICT, e.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        Map<String, List<String>> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                    FieldError::getField,
                    Collectors.mapping(
                        error -> Objects.requireNonNullElse(
                            error.getDefaultMessage(),
                            "Validation failed"
                        ),
                        Collectors.toList()
                    )
            ));

        log.warn("Request validation failed: errors={}", errors);

        ValidationErrorResponse errorResponse = new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                errors
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(
            Exception e, HttpServletRequest request
    ) {
        logError(e, request);

        return buildResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Internal server error"
        );
    }

    private ResponseEntity<ErrorResponse> buildResponse(
            HttpStatus status, String message
    ) {
        return ResponseEntity
            .status(status)
            .body(new ErrorResponse(
                    status.value(),
                    message,
                    Instant.now()
            ));
    }

    private void logError(Exception e, HttpServletRequest request) {
        log.error(
                "Unhandled exception: type={}, method={}, uri={}",
                e.getClass().getName(),
                request.getMethod(),
                request.getRequestURI(),
                e
        );
    }
}
