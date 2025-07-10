package raisetechtask.taskstudentsmanagement.finalversion.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetechtask.taskstudentsmanagement.finalversion.data.Student;

import java.util.List;

/**
 * 受講生に関連した情報をまとめるためのDTOです。
 */
@Schema(description = "受講生詳細")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Data
public class StudentDetail {

	/**
	 * 受講生情報のオブジェクト
	 * Validでの検証を行っています
	 */
	@Valid
	private Student student;

	/**
	 * 受講生コース情報と申込状況をまとめたDTOを保持します。
	 * 受講生コース情報は受講生に対し:NのためListとしています。
	 * Validでの検証を行っています
	 */
	@Valid
	private List<CourseApplicationDetail> studentCourseList;
}

