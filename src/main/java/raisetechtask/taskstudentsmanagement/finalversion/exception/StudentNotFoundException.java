package raisetechtask.taskstudentsmanagement.finalversion.exception;

// RuntimeException を継承することで、非チェック例外となります
public class StudentNotFoundException extends RuntimeException {
	public StudentNotFoundException(String message) {
		super(message);
	}
}