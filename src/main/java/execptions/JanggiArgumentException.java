package execptions;

public final class JanggiArgumentException extends IllegalArgumentException {

  private static final String PREFIX = "[ERROR] ";

  public JanggiArgumentException(final String message) {
    super(PREFIX + message);
  }
}
