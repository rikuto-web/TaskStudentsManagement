package raisetechtask.taskstudentsmanagement.finalversion.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生")
@Getter
@Setter
public class Student {
	@PositiveOrZero
	private int id;
	private String fullName;
	private String furigana;
	private String nickname;
	private String emailAddress;
	private String address;
	private int age;
	private String gender;
	private String remark;
	private boolean deleted;
}
