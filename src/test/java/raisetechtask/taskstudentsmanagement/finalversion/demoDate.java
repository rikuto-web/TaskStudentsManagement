package raisetechtask.taskstudentsmanagement.finalversion;

import org.springframework.stereotype.Component;
import raisetechtask.taskstudentsmanagement.finalversion.data.ApplicationStatus;
import raisetechtask.taskstudentsmanagement.finalversion.data.ApplicationStatusEnum;
import raisetechtask.taskstudentsmanagement.finalversion.data.Course;
import raisetechtask.taskstudentsmanagement.finalversion.data.Student;

import java.time.LocalDate;

@Component
public class demoDate {


	// ダミーデータ_申込状況_登録更新前
	public ApplicationStatus createDefaultStatus() {
		return ApplicationStatus.builder()
				.studentCourseId(0)
				.status(ApplicationStatusEnum.HON_MOSIKOMI)
				.build();
	}

	// ダミーデータ_受講生情報
	public Student createDefaultStudent() {
		return Student.builder()
				.fullName("デフォルト太郎")
				.furigana("デフォルトタロウ")
				.nickname("デモニック")
				.emailAddress("default.test@example.com")
				.address("東京都千代田区")
				.age(25)
				.gender("男性")
				.remark("デフォルトデータ")
				.isDeleted(false)
				.build();
	}

	// ダミーデータ_コース情報
	public Course createDefaultCourse() {
		return Course.builder()
				.studentId(0)
				.courseName("テストコース")
				.courseStartDay(LocalDate.now())
				.courseCompletionDay(LocalDate.now().plusMonths(3))
				.build();
	}
}
