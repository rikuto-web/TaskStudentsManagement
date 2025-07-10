package raisetechtask.taskstudentsmanagement.finalversion.exception;

/**
 * RuntimeExceptionを継承した独自例外クラス
 * 受講生情報が見つからない場合に発生する非検査例外です
 */
public class StudentNotFoundException extends RuntimeException {

	/**
	 * * 受講生情報が見つからない場合に発生した非検査例外を補足します。
	 *
	 * @param message 例外メッセージ
	 */
	public StudentNotFoundException(String message) {
		super(message);
	}
}