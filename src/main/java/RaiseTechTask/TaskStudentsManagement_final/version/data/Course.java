package RaiseTechTask.TaskStudentsManagement_final.version.data;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Course {
	@PositiveOrZero
	private int id;
	private int studentId;
	private String courseName;
	private LocalDate courseStartDay;
	private LocalDate courseCompletionDay;
}
