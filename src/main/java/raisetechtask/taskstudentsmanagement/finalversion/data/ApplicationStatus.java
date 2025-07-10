package raisetechtask.taskstudentsmanagement.finalversion.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetechtask.taskstudentsmanagement.finalversion.enums.ApplicationStatusEnum;

@Schema(description = "申込状況")
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
