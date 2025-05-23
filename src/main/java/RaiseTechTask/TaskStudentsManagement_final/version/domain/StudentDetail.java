package RaiseTechTask.TaskStudentsManagement_final.version.domain;

import RaiseTechTask.TaskStudentsManagement_final.version.data.Course;
import RaiseTechTask.TaskStudentsManagement_final.version.data.Student;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetail {

	@Valid
	private Student student;

	@Valid
	private List<Course> studentCourseList;
}

