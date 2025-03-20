package execptions;

public class JanggiArgumentException extends IllegalArgumentException {
    private static final String PREFIX = "[ERROR] ";

    public JanggiArgumentException(String message) {
        super(PREFIX + message);
    }
}
