package raisetechtask.taskstudentsmanagement.finalversion.domain;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetechtask.taskstudentsmanagement.finalversion.data.ApplicationStatus;
import raisetechtask.taskstudentsmanagement.finalversion.data.StudentCourse;

/**
 * 受講生コース情報と申込状況をまとめたDTOです
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CourseApplicationDetail {

	/**
	 * 受講生コース情報のオブジェクトです
	 * Validでの検証を行っています
	 */
	@Valid
	private StudentCourse studentCourse;

	/**
	 * 申込状況のオブジェクトです
	 * Validでの検証を行っています
	 */
	@Valid
	private ApplicationStatus applicationStatus;
}