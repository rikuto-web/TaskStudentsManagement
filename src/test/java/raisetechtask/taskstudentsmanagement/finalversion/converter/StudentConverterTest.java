package raisetechtask.taskstudentsmanagement.finalversion.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raisetechtask.taskstudentsmanagement.finalversion.data.ApplicationStatus;
import raisetechtask.taskstudentsmanagement.finalversion.data.Student;
import raisetechtask.taskstudentsmanagement.finalversion.data.StudentCourse;
import raisetechtask.taskstudentsmanagement.finalversion.domain.StudentDetail;
import raisetechtask.taskstudentsmanagement.finalversion.enums.ApplicationStatusEnum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static raisetechtask.taskstudentsmanagement.finalversion.enums.ApplicationStatusEnum.JUKOCHU;

class StudentConverterTest {

	private StudentConverter converter;

	@BeforeEach
	void setUp() {
		converter = new StudentConverter();
	}

	@Test
	void 受講生情報とコース情報の呼びだしが空の状態でできていること() {
		List<Student> studentList = new ArrayList<>();
		List<StudentCourse> studentCourseList = new ArrayList<>();
		List<ApplicationStatus> statusList = new ArrayList<>();

		List<StudentDetail> actualStudentDetails = converter.convertStudentDetails(studentList, studentCourseList, statusList);

		assertNotNull(actualStudentDetails);
		assertTrue(actualStudentDetails.isEmpty());
	}

	@Test
	void 単一の受講生情報に紐づいたコース情報が空の状態で返ってきていること() {
		Student student = createStudent(999, "田中太郎", "タナカタロウ", "たなか", "rftgyhu@huji.com", "東京", 35, "男性", "", false);

		List<Student> studentList = new ArrayList<>();
		studentList.add(student);

		StudentDetail expectedDetail = new StudentDetail();
		expectedDetail.setStudent(student);
		expectedDetail.setStudentCourseList(new ArrayList<>());

		List<StudentDetail> expectedStudentDetails = new ArrayList<>();
		expectedStudentDetails.add(expectedDetail);
		List<StudentCourse> studentCourseList = new ArrayList<>();

		List<ApplicationStatus> statusList = new ArrayList<>();

		//実行
		List<StudentDetail> actualStudentDetails = converter.convertStudentDetails(studentList, studentCourseList, statusList);
		//検証
		assertEquals(expectedStudentDetails, actualStudentDetails);
	}

	@Test
	void 複数の受講生情報と受講生に紐づいた空のコース情報が返ってきていること() {
		Student student = createStudent(999, "田中太郎", "タナカタロウ", "たなか", "rftgyhu@huji.com", "東京", 35, "男性", "", false);
		Student secondStudent = createStudent(888, "田中ジロウ", "タナカジロウ", "じろう", "rgyu@huji.com", "沖縄", 40, "男性", "", false);
		List<Student> studentList = new ArrayList<>();
		studentList.add(student);
		studentList.add(secondStudent);
		List<StudentCourse> studentCourseList = new ArrayList<>();

		StudentDetail expectedDetailForStudent = new StudentDetail();
		expectedDetailForStudent.setStudent(student);
		expectedDetailForStudent.setStudentCourseList(new ArrayList<>());

		StudentDetail expectedDetailForStudent1 = new StudentDetail();
		expectedDetailForStudent1.setStudent(secondStudent);
		expectedDetailForStudent1.setStudentCourseList(new ArrayList<>());

		List<StudentDetail> expectedStudentDetails = new ArrayList<>();
		expectedStudentDetails.add(expectedDetailForStudent);
		expectedStudentDetails.add(expectedDetailForStudent1);

		List<ApplicationStatus> statusList = new ArrayList<>();

		//実行
		List<StudentDetail> actualStudentDetails = converter.convertStudentDetails(studentList, studentCourseList, statusList);
		//検証
		assertEquals(expectedStudentDetails, actualStudentDetails);
	}

	@Test
	void 受講生のリストと受講生コース情報のリストを渡したときに紐づかない受講生コース情報は除外されること() {
		Student student = createStudent(999, "田中太郎", "タナカタロウ", "たなか", "rftgyhu@huji.com", "東京", 35, "男性", "", false);
		StudentCourse studentCourse = createStudentCourse(999, 888, "Java", LocalDate.of(2025, 4, 1), LocalDate.of(2026, 4, 1));
		ApplicationStatus status = createApplicationStatus(7, 999, JUKOCHU);

		List<Student> studentList = List.of(student);
		List<StudentCourse> studentCourseList = List.of(studentCourse);
		List<ApplicationStatus> applicationStatusList = List.of(status);

		List<StudentDetail> actual = converter.convertStudentDetails(studentList, studentCourseList, applicationStatusList);

		assertThat(actual.getFirst().getStudent()).isEqualTo(student);
		assertThat(actual.getFirst().getStudentCourseList()).isEmpty();
	}

	// Studentオブジェクトを生成するヘルパーメソッド
	private Student createStudent(int id, String fullName, String furigana, String nickname, String emailAddress, String address, int age, String gender, String remark, boolean isDeleted) {
		return Student.builder()
				.id(id)
				.fullName(fullName)
				.furigana(furigana)
				.nickname(nickname)
				.emailAddress(emailAddress)
				.address(address)
				.age(age)
				.gender(gender)
				.remark(remark)
				.isDeleted(isDeleted)
				.build();
	}

	// StudentCourseオブジェクトを生成するヘルパーメソッド
	private StudentCourse createStudentCourse(int id, int studentId, String courseName, LocalDate courseStartDay, LocalDate courseCompletionDay) {
		return StudentCourse.builder()
				.id(id)
				.studentId(studentId)
				.courseName(courseName)
				.courseStartDay(courseStartDay)
				.courseCompletionDay(courseCompletionDay)
				.build();
	}

	// ApplicationStatusオブジェクトを生成するヘルパーメソッド
	private ApplicationStatus createApplicationStatus(int id, int studentCourseId, ApplicationStatusEnum status) {
		return ApplicationStatus.builder()
				.id(id)
				.studentCourseId(studentCourseId)
				.status(status)
				.build();
	}
}