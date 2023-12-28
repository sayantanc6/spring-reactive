package dummy.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class AppExceptionHandler {
	
	@Autowired
	ErrorInfo error;

	@ExceptionHandler(ProductNotFoundException.class)
	public Mono<ResponseEntity<ErrorInfo>> productnotfound(ProductNotFoundException exception) {
		 error.setErrorMessage(exception.getMessage());
         error.setTimestamp(LocalDateTime.now());
         error.setErrorCode(HttpStatus.NOT_FOUND.value());
         return Mono.just(new ResponseEntity<ErrorInfo>(error, HttpStatus.NOT_FOUND));
	}
	
	@ExceptionHandler(Exception.class)
	public Mono<ResponseEntity<ErrorInfo>> generalError(Exception exception) {
		 error.setErrorMessage(exception.getMessage()); 
         error.setTimestamp(LocalDateTime.now());
         error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
         return Mono.just(new ResponseEntity<ErrorInfo>(error, HttpStatus.INTERNAL_SERVER_ERROR));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Mono<ResponseEntity<ErrorInfo>> argNotValid(MethodArgumentNotValidException exception) {
			error.setErrorMessage(exception.getBindingResult().getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(", ")));
			error.setErrorCode(HttpStatus.BAD_REQUEST.value());
		    error.setTimestamp(LocalDateTime.now());
			return Mono.just(new ResponseEntity<ErrorInfo>(error, HttpStatus.BAD_REQUEST));
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public Mono<ResponseEntity<ErrorInfo>> pathNotValid(ConstraintViolationException exception) {
		error.setErrorCode(HttpStatus.BAD_REQUEST.value());
		error.setErrorMessage(exception.getConstraintViolations().stream().map(x -> x.getMessage()).collect(Collectors.joining(", ")));
		error.setTimestamp(LocalDateTime.now());
		return Mono.just(new ResponseEntity<ErrorInfo>(error, HttpStatus.BAD_REQUEST));
	}
           }
