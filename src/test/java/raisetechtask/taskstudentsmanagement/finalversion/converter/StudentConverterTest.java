package raisetechtask.taskstudentsmanagement.finalversion.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raisetechtask.taskstudentsmanagement.finalversion.data.Course;
import raisetechtask.taskstudentsmanagement.finalversion.data.Student;
import raisetechtask.taskstudentsmanagement.finalversion.domain.StudentDetail;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

		List<StudentDetail> actualStudentDetails = converter.convertStudentDetails(studentList, courseList);

		assertNotNull(actualStudentDetails);
		assertTrue(actualStudentDetails.isEmpty());
	}

	@Test
	void 単一の受講生情報に紐づいたコース情報が空の状態() {
		Student student = new Student();
		student.setId(999);
		student.setFullName("田中太郎");
		student.setFurigana("タナカタロウ");
		student.setNickname("たなか");
		student.setEmailAddress("rftgyhu@huji.com");
		student.setAddress("東京");
		student.setAge(35);
		student.setGender("男性");
		List<Student> studentList = new ArrayList<>();
		studentList.add(student);

		List<Course> courseList = new ArrayList<>();

		StudentDetail expectedDetail = new StudentDetail();
		expectedDetail.setStudent(student);
		expectedDetail.setStudentCourseList(new ArrayList<>());

		List<StudentDetail> expectedStudentDetails = new ArrayList<>();
		expectedStudentDetails.add(expectedDetail);
		//実行
		List<StudentDetail> actualStudentDetails = converter.convertStudentDetails(studentList, courseList);
		//検証
		assertEquals(expectedStudentDetails, actualStudentDetails);
	}

	void 複数の受講生情報に紐づいたコース情報が空の状態() {
		Student student = new Student();
		student.setId(999);
		student.setFullName("田中太郎");
		student.setFurigana("タナカタロウ");
		student.setNickname("たなか");
		student.setEmailAddress("rftgyhu@huji.com");
		student.setAddress("東京");
		student.setAge(35);
		student.setGender("男性");

		Student student1 = new Student();
		student1.setId(888);
		student1.setFullName("田中ジロウ");
		student1.setFurigana("タナカジロウ");
		student1.setNickname("じろう");
		student1.setEmailAddress("rgyu@huji.com");
		student1.setAddress("沖縄");
		student1.setAge(40);
		student1.setGender("男性");

		List<Student> studentList = new ArrayList<>();
		studentList.add(student);
		studentList.add(student1);

		List<Course> courseList = new ArrayList<>();

		StudentDetail expectedDetailForStudent = new StudentDetail();
		expectedDetailForStudent.setStudent(student);
		expectedDetailForStudent.setStudentCourseList(new ArrayList<>());

		StudentDetail expectedDetailForStudent1 = new StudentDetail();
		expectedDetailForStudent1.setStudent(student1);
		expectedDetailForStudent.setStudentCourseList(new ArrayList<>());

		List<StudentDetail> expectedStudentDetails = new ArrayList<>();
		expectedStudentDetails.add(expectedDetailForStudent);
		expectedStudentDetails.add(expectedDetailForStudent1);
		//実行
		List<StudentDetail> actualStudentDetails = converter.convertStudentDetails(studentList, courseList);
		//検証
		assertEquals(expectedStudentDetails, actualStudentDetails);
	}
}