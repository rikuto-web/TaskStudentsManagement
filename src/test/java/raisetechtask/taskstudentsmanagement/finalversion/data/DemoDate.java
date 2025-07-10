package raisetechtask.taskstudentsmanagement.finalversion.data;

import org.springframework.boot.test.context.TestComponent;
import raisetechtask.taskstudentsmanagement.finalversion.domain.CourseApplicationDetail;
import raisetechtask.taskstudentsmanagement.finalversion.domain.StudentDetail;
import raisetechtask.taskstudentsmanagement.finalversion.enums.ApplicationStatusEnum;

import java.time.LocalDate;
import java.util.List;

/**
 * テスト用のダミーデータを生成するためのクラスです。
 * 必ずrepositoryでは使用しないこと
 */
@TestComponent
public class DemoDate {


	/**
	 * ダミーの申込状況データ（登録・更新前状態）を生成します。
	 * デフォルトではstudentCourseIdが0、ステータスが「本申し込み」となります。
	 *
	 * @return 申込状況のダミーデータ
	 */
	public ApplicationStatus createDefaultStatus() {
		return ApplicationStatus.builder()
				.studentCourseId(0)
				.status(ApplicationStatusEnum.HON_MOSIKOMI)
				.build();
	}

	/**
	 * ダミーの受講生情報を生成します。
	 * デフォルトの氏名、フリガナ、ニックネーム、メールアドレスなどが設定されます。
	 *
	 * @return 受講生情報のダミーデータ
	 */
	public Student createDefaultStudent() {
		return Student.builder()
				.id(10)
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

	/**
	 * ダミーのコース情報を生成します。
	 * デフォルトのコース名、開始日、完了日が設定されます。
	 *
	 * @return コース情報のダミーデータ
	 */
	public StudentCourse createDefaultCourse() {
		return StudentCourse.builder()
				.studentId(0)
				.courseName("テストコース")
				.courseStartDay(LocalDate.now())
				.courseCompletionDay(LocalDate.now().plusMonths(3))
				.build();
	}

	/**
	 * 特定のIDとステータスを持つCourseApplicationDetailを生成します。
	 * 必要に応じて、紐づくStudentCourseもカスタマイズできます。
	 *
	 * @param courseId  コースID
	 * @param studentId 生徒ID
	 * @param status    申込状況
	 * @return CourseApplicationDetailのダミーデータ
	 */
	public CourseApplicationDetail createCourseApplicationDetail(int courseId, int studentId, ApplicationStatusEnum status) {
		StudentCourse studentCourse = StudentCourse.builder()
				.id(courseId)
				.studentId(studentId)
				.courseName("コース" + courseId) // コース名をIDに基づいて生成
				.courseStartDay(LocalDate.now())
				.courseCompletionDay(LocalDate.now().plusMonths(3))
				.build();

		ApplicationStatus applicationStatus = ApplicationStatus.builder()
				.id(courseId * 100) // 適当なIDを割り当て
				.studentCourseId(courseId)
				.status(status)
				.build();

		return new CourseApplicationDetail(studentCourse, applicationStatus);
	}

	/**
	 * 特定のIDと氏名、特定のコース申込詳細を持つStudentDetailを生成します。
	 *
	 * @param studentId     生徒ID
	 * @param fullName      氏名
	 * @param courseDetails コース申込詳細のリスト
	 * @return StudentDetailのダミーデータ
	 */
	public StudentDetail createStudentDetail(int studentId, String fullName, List<CourseApplicationDetail> courseDetails) {
		Student student = Student.builder()
				.id(studentId)
				.fullName(fullName)
				.furigana("フリガナ" + studentId)
				.nickname("ニックネーム" + studentId)
				.emailAddress("student" + studentId + "@example.com")
				.address("住所" + studentId)
				.age(20 + studentId)
				.gender((studentId % 2 == 0) ? "女性" : "男性")
				.remark("備考" + studentId)
				.isDeleted(false)
				.build();

		return new StudentDetail(student, courseDetails);
	}
}