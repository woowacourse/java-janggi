package exceptions;

public final class JanggiGameRuleWarningException extends RuntimeException {

    private static final String PREFIX = "[WARNING] ";

    public JanggiGameRuleWarningException(final String message) {
        super(PREFIX + message);
    }
}
