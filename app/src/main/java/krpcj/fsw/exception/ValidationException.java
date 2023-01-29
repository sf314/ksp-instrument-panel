package krpcj.fsw.exception;

public class ValidationException extends RuntimeException {
    
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(Throwable t) {
        super(t);
    }

    public ValidationException(String message, Throwable t) {
        super(message, t);
    }
}
