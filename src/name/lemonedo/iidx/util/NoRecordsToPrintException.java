package name.lemonedo.iidx.util;

public class NoRecordsToPrintException extends RuntimeException {

    public NoRecordsToPrintException() {}

    public NoRecordsToPrintException(String message) {
        super(message);
    }

    public NoRecordsToPrintException(Throwable cause) {
        super(cause);
    }

    public NoRecordsToPrintException(String message, Throwable cause) {
        super(message, cause);
    }
}
