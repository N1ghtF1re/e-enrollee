package men.brakh.enrollment.application.errorhandling;


import lombok.val;
import men.brakh.enrollment.exception.BadRequestException;
import men.brakh.enrollment.exception.ResourceNotFoundException;
import men.brakh.enrollment.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorControllerAdvisor extends ResponseEntityExceptionHandler {
  private final Logger logger = LoggerFactory.getLogger("ControllerAdvisor");

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Object> handleResourceNotFoundException(
      final ResourceNotFoundException ex,
      final WebRequest request
  ) {
    return processUnhandledException(ex, request, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<Object> handleBadRequestException(
      final BadRequestException ex,
      final WebRequest request
  ) {
    return processUnhandledException(ex, request, HttpStatus.BAD_REQUEST);
  }


  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<Object> handleUnauthorizedException(
      final UnauthorizedException ex,
      final WebRequest request
  ) {
    return processUnhandledException(ex, request, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleAnyException(
      final Exception ex,
      final WebRequest request
  ) {
    return processUnhandledException(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ResponseEntity<Object> processUnhandledException(
      final Exception ex,
      final WebRequest request,
      final HttpStatus status
  ) {

    logger.error("Unhandled error", ex);

    val error = ErrorResponse.builder()
        .message(ex.getMessage())
        .build();

    return new ResponseEntity<>(error, status);
  }


}
