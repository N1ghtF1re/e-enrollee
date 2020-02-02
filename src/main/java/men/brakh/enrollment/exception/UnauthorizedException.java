package men.brakh.enrollment.exception;

public class UnauthorizedException extends RuntimeException {
  public UnauthorizedException(final String message) {
    super(message);
  }

  public UnauthorizedException(final String message, final Throwable throwable) {
    super(message, throwable);
  }
}
