package raisetechtask.taskstudentsmanagement.finalversion.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Schema(description = "受講生コース情報")
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
