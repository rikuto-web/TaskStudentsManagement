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
	@Schema(description = "申込状況ID（自動採番）", example = "1")
	private int id;

	/**
	 * この申込状況情報が紐づく受講生コースの一意なIDです。必須項目。
	 */
	@NotNull(message = "受講生コースIDは必須項目です。")
	@Schema(description = "紐づく受講生コースID", example = "101")
	private int studentCourseId;

	/**
	 * 申込状況のEnum値です（例: 仮申込、本申込）。必須項目。
	 */
	@NotNull(message = "申込状況は必須項目です。")
	@Schema(
			description = "申込状況（仮申込: KARI_MOSIKOMI、本申込: HON_MOSIKOMI、受講中: JUKOCHU、修了: JUKO_SHURYO）",
			example = "KARI_MOSIKOMI",
			allowableValues = {"KARI_MOSIKOMI", "HON_MOSIKOMI", "JUKOCHU", "JUKO_SHURYO"}
	)
	private ApplicationStatusEnum status;
}