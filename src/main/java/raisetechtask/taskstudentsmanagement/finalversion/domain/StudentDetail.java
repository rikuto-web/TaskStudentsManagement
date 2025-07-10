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

@Schema(description = "受講生詳細")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Data
public class StudentDetail {

	@Valid
	private Student student;

	@Valid
	private List<CourseApplicationDetail> studentCourseList;

}

