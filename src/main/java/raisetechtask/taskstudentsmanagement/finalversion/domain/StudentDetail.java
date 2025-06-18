package raisetechtask.taskstudentsmanagement.finalversion.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetechtask.taskstudentsmanagement.finalversion.data.ApplicationStatus;
import raisetechtask.taskstudentsmanagement.finalversion.data.Course;
import raisetechtask.taskstudentsmanagement.finalversion.data.Student;

import java.util.List;

@Schema(description = "受講生詳細")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class StudentDetail {

	@Valid
	private Student student;

	@Valid
	private List<Course> studentCourseList;

	@Valid
	private ApplicationStatus status;
}

