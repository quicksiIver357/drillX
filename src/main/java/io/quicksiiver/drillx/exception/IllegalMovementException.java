package main.java.io.quicksiiver.drillx.exception;

public class IllegalMovementException extends RuntimeException {
    public IllegalMovementException() {
        super();
    }
    public IllegalMovementException(String message) {
        super(message);
    }
    public IllegalMovementException(String message, Throwable cause) {
        super(message, cause);
    }
}