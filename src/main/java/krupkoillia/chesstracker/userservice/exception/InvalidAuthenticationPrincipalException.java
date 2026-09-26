package krupkoillia.chesstracker.userservice.exception;

public class InvalidAuthenticationPrincipalException extends RuntimeException {

    public InvalidAuthenticationPrincipalException(String message) {
        super(message);
    }
}
