package raisetechtask.taskstudentsmanagement.finalversion.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CostomException extends Exception {

	@ExceptionHandler(TestException.class)
	public ResponseEntity<String> handleTestException(TestException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}
}
