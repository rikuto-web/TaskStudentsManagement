package raisetechtask.taskstudentsmanagement.finalversion.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class ApplicationStatus {

	private int id;
	private int studentCourseId;
	private ApplicationStatusEnum status;
}
