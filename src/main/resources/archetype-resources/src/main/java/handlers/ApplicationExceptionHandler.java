#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.handlers;

import ${package}.responses.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class handles application exceptions
 */
@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return prepareHandle(ex, request, status, status.getReasonPhrase(), ex.getMessage());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {
        return prepareHandle(ex, request, ex.getStatus(), ex.getStatus().getReasonPhrase(), ex.getReason());
    }
	
	@ExceptionHandler({HttpClientErrorException.class})
    public ResponseEntity<Object> handleHttpClientErrorException(HttpClientErrorException ex, WebRequest request) {
        return prepareHandle(ex, request, ex.getStatusCode(), ex.getStatusText(), ex.getMessage());
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> messages = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            messages.put(error.getField(), error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            messages.put(error.getObjectName(), error.getDefaultMessage());
        }
        return prepareHandle(ex, request, status, status.getReasonPhrase(), messages);
    }

    /**
     * Handles the given exception from the given web request
     *
     * @param e,       the exception to handle
     * @param request, the web request
     * @param status,  the http status code to handle for the exception
     * @param error,   the http error message
     * @param message, the exception message
     * @return the response entity to push back
     */
    private ResponseEntity<Object> prepareHandle(Exception e, WebRequest request, HttpStatus status, String error, Object message) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        LOG.error("Error handled from request {}: {}", path, message, e);
        return handleExceptionInternal(e, getErrorAttributes((ServletWebRequest) request, status, error, message),
                new HttpHeaders(), status, request);
    }

    /**
     * Convert the given error data into a formatted error response
     *
     * @param requestAttributes, the request data that has generated the error
     * @param status,            the http status code to handle
     * @param error,             the http status error message
     * @param message,           the error message
     * @return the error response
     */
    private ErrorResponse getErrorAttributes(ServletWebRequest requestAttributes, HttpStatus status, String error, Object message) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setStatus(status.value());
        errorResponse.setError(error);
        errorResponse.setMessage(message);
        errorResponse.setPath(requestAttributes.getRequest().getRequestURI());
        return errorResponse;
    }

}
