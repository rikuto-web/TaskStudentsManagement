package raisetechtask.taskstudentsmanagement.finalversion.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetechtask.taskstudentsmanagement.finalversion.enums.ApplicationStatusEnum;

/**
 * application_statusテーブルと紐づいたdataです。
 */
@Schema(description = "申込状況")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class ApplicationStatus {

	/**
	 * 申込状況情報の一意の識別子です。
	 * データベースで自動採番されます。
	 */
	private int id;

	/**
	 * この申込状況情報が紐づく受講生コースの一意なIDです。必須項目。
	 */
	@NotNull(message = "受講生コースIDは必須項目です。")
	private int studentCourseId;

	/**
	 * 申込状況のEnum値です（例: 仮申込、本申込）。必須項目。
	 */
	@NotNull(message = "申込状況は必須項目です。")
	private ApplicationStatusEnum status;
}