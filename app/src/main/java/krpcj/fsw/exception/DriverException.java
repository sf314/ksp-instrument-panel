package krpcj.fsw.exception;

public class DriverException extends RuntimeException {

    public DriverException(String message) {
        super(message);
    }

    public DriverException(Throwable t) {
        super(t);
    }

    public DriverException(String message, Throwable t) {
        super(message, t);
    }
}
