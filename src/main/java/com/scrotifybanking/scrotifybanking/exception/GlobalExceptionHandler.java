package com.scrotifybanking.scrotifybanking.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Global exception handler.
 *
 * @author manatara
 * @version 1.0
 * @since 27 -11-2019
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Customize the response for MethodArgumentNotValidException.
     * <p>
     * This method delegates to {@link #handleExceptionInternal}.
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", new Date());
        response.put("status", status.value());
        response.put("errors", errors);
        return new ResponseEntity<>(response, headers, status);
    }

	/**
	 * @ExceptionHandler(value = {CustomException.class, Exception.class}) public
	 * final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest
	 * request) { List<String> details = new ArrayList<>();
	 * details.add(ex.getLocalizedMessage()); ApiError error = new
	 * ApiError(ex.getMessage(), HttpStatus.NOT_FOUND); return new
	 * ResponseEntity(error, HttpStatus.BAD_REQUEST); }
	 */
    @ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> globalExceptionHandler(Exception exception, WebRequest request) {
		 ErrorResponse errorResponse = new ErrorResponse();
		  errorResponse.setMessage(exception.getMessage());
		  errorResponse.setStatusCode(404);
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

	}
}
