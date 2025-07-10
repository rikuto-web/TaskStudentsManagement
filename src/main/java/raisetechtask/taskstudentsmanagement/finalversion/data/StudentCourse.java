package raisetechtask.taskstudentsmanagement.finalversion.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * students_coursesテーブルと紐づいたdataです
 */
@Schema(description = "受講生コース情報")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class StudentCourse {

	/**
	 * 受講生コース情報の一意の識別子です。
	 * DBにて自動採番で設定されます。
	 */
	private int id;

	/**
	 * 受講生コース情報に紐づいた受講生の一意なIDです。必須項目。
	 */
	@NotNull(message = "受講生IDは必須項目です。")
	private int studentId;

	/**
	 * 受講するコースの名称です。必須項目で、空白は許容されません。最大100文字。
	 */
	@NotBlank(message = "コース名称は必須項目です。")
	@Size(max = 50, message = "コース名称は50文字以内で入力してください。")
	private String courseName;

	/**
	 * コースの開始日です。必須項目で、現在または未来の日付である必要があります。
	 */
	@NotNull(message = "コース開始日は必須項目です。")
	@FutureOrPresent(message = "コース開始日は現在または未来の日付である必要があります。")
	private LocalDate courseStartDay;

	/**
	 * コースの修了予定日です。必須項目で、現在または未来の日付である必要があります。
	 */
	@NotNull(message = "コース修了予定日は必須項目です。")
	@FutureOrPresent(message = "コース修了予定日は現在または未来の日付である必要があります。")
	private LocalDate courseCompletionDay;
}
