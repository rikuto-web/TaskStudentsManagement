package raisetechtask.taskstudentsmanagement.finalversion.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raisetechtask.taskstudentsmanagement.finalversion.data.ApplicationStatus;
import raisetechtask.taskstudentsmanagement.finalversion.data.Course;
import raisetechtask.taskstudentsmanagement.finalversion.data.Student;
import raisetechtask.taskstudentsmanagement.finalversion.domain.StudentDetail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static raisetechtask.taskstudentsmanagement.finalversion.data.ApplicationStatusEnum.JUKOCHU;

class StudentConverterTest {

	private StudentConverter converter;

	@BeforeEach
	void StudentConverterTest() {
		converter = new StudentConverter();
	}


	@Test
	void 受講生情報とコース情報の呼びだしが空の状態でできていること() {
		List<Student> studentList = new ArrayList<>();
		List<Course> courseList = new ArrayList<>();
		List<ApplicationStatus> statusList = new ArrayList<>();

		List<StudentDetail> actualStudentDetails = converter.convertStudentDetails(studentList, courseList, statusList);

		assertNotNull(actualStudentDetails);
		assertTrue(actualStudentDetails.isEmpty());
	}

	@Test
	void 単一の受講生情報に紐づいたコース情報が空の状態で返ってきていること() {
		Student student = new Student(999, "田中太郎", "タナカタロウ", "たなか", "rftgyhu@huji.com", "東京", 35, "男性", "", false);

		List<Student> studentList = new ArrayList<>();
		studentList.add(student);

		StudentDetail expectedDetail = new StudentDetail();
		expectedDetail.setStudent(student);
		expectedDetail.setStudentCourseList(new ArrayList<>());

		List<StudentDetail> expectedStudentDetails = new ArrayList<>();
		expectedStudentDetails.add(expectedDetail);
		List<Course> courseList = new ArrayList<>();

		List<ApplicationStatus> statusList = new ArrayList<>();

		//実行
		List<StudentDetail> actualStudentDetails = converter.convertStudentDetails(studentList, courseList, statusList);
		//検証
		assertEquals(expectedStudentDetails, actualStudentDetails);
	}

	@Test
	void 複数の受講生情報と受講生に紐づいた空のコース情報が返ってきていること() {
		Student student = new Student(999, "田中太郎", "タナカタロウ", "たなか", "rftgyhu@huji.com", "東京", 35, "男性", "", false);
		Student student1 = new Student(888, "田中ジロウ", "タナカジロウ", "じろう", "rgyu@huji.com", "沖縄", 40, "男性", "", false);
		List<Student> studentList = new ArrayList<>();
		studentList.add(student);
		studentList.add(student1);
		List<Course> courseList = new ArrayList<>();

		StudentDetail expectedDetailForStudent = new StudentDetail();
		expectedDetailForStudent.setStudent(student);
		expectedDetailForStudent.setStudentCourseList(new ArrayList<>());

		StudentDetail expectedDetailForStudent1 = new StudentDetail();
		expectedDetailForStudent1.setStudent(student1);
		expectedDetailForStudent1.setStudentCourseList(new ArrayList<>());

		List<StudentDetail> expectedStudentDetails = new ArrayList<>();
		expectedStudentDetails.add(expectedDetailForStudent);
		expectedStudentDetails.add(expectedDetailForStudent1);

		List<ApplicationStatus> statusList = new ArrayList<>();

		//実行
		List<StudentDetail> actualStudentDetails = converter.convertStudentDetails(studentList, courseList, statusList);
		//検証
		assertEquals(expectedStudentDetails, actualStudentDetails);
	}

	@Test
	void 受講生のリストと受講生コース情報のリストを渡したときに紐づかない受講生コース情報は除外されること() {
		Student student = new Student(999, "田中太郎", "タナカタロウ", "たなか", "rftgyhu@huji.com", "東京", 35, "男性", "", false);
		Course course = new Course(999, 888, "Java", LocalDate.now(), LocalDate.now().plusYears(1));
		ApplicationStatus status = new ApplicationStatus(7, 999, JUKOCHU);

		List<Student> studentList = List.of(student);
		List<Course> courseList = List.of(course);
		List<ApplicationStatus> applicationStatusList = List.of(status);

		List<StudentDetail> actual = converter.convertStudentDetails(studentList, courseList, applicationStatusList);

		assertThat(actual.getFirst().getStudent()).isEqualTo(student);
		assertThat(actual.getFirst().getStudentCourseList()).isEmpty();
	}
}