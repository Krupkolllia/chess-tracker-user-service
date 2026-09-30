package krupkoillia.chesstracker.userservice.exception;

public class AuthenticatedUserNotFoundException extends IllegalStateException {

    public AuthenticatedUserNotFoundException(String message) {
        super(message);
    }
}
