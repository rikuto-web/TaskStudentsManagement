package raisetechtask.taskstudentsmanagement.finalversion.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 発生した非検査例外を補足して適切な例外処理を戻すための例外ハンドラークラスです。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 受講生情報が見つからない場合に例外を補足し適切なレスポンスを返します。
	 *
	 * @param ex StudentNotFoundException
	 * @return エラーメッセージと404エラーコード
	 */
	@ExceptionHandler(StudentNotFoundException.class)
	public ResponseEntity<String> handleStudentNotFoundException(StudentNotFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	/**
	 * 不正な引数が渡された場合に例外を補足し適切なレスポンスを返します。
	 *
	 * @param ex IllegalArgumentException
	 * @return エラーメッセージと400エラーコード
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	/**
	 * バリデーションエラーによる例外を補足し適切なレスポンスを返します。
	 *
	 * @param ex ConstraintViolationException
	 * @return エラーメッセージと400エラーコード
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<String> handleValidationException(ConstraintViolationException ex) {
		String errorMessage = ex.getConstraintViolations().stream()
				.map(ConstraintViolation::getMessage)
				.reduce((msg1, msg2) -> msg1 + ", " + msg2)
				.orElse("バリデーションエラーが発生しました。");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
	}

	/**
	 * その他想定外の例外をまとめて補足し適切なレスポンスを返します。
	 *
	 * @param ex Exception
	 * @return エラーメッセージと500エラーコード
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleAllExceptions(Exception ex) {
		return new ResponseEntity<>("システムエラーが発生しました。しばらく経ってから再度お試しください。" + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}