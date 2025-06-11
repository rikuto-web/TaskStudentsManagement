package raisetechtask.taskstudentsmanagement.finalversion.repository;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetechtask.taskstudentsmanagement.finalversion.data.Course;
import raisetechtask.taskstudentsmanagement.finalversion.data.Student;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
class CoursesRepositoryTest {

	@Autowired
	private CoursesRepository sut;
	@Autowired
	private StudentsRepository studentSut;

	@Test
	void コース情報の全件検索が行えること() {
		List<Course> actual = sut.searchCourses();
		assertThat(actual.size()).isEqualTo(5);
	}

	@Test
	void 受講生IDに紐づいたコース情報の検索が行えること() {
		Student studentToRegister = createDefaultStudent();
		studentToRegister.setId(999);
		studentSut.registerStudent(studentToRegister);

		Course courseToRegister = createDefaultCourse();
		sut.registerStudentCourses(courseToRegister);


		List<Course> actualCourses = sut.searchStudentCourse(courseToRegister.getStudentId());

		Course foundCourse = actualCourses.getFirst();
		assertThat(actualCourses).isNotNull();
	}


	@Test
	void コース情報の登録が行えること() {
		Student studentForCourse = createDefaultStudent();
		studentForCourse.setId(999);
		studentSut.registerStudent(studentForCourse);

		Course courseToRegister = createDefaultCourse();
		sut.registerStudentCourses(courseToRegister);

		List<Course> allCourses = sut.searchCourses();
		assertThat(allCourses.size()).isEqualTo(6);
	}

	@Test
	void コース名の更新が行えること() {
		Student studentForUpdate = createDefaultStudent();
		studentForUpdate.setId(999);
		studentSut.registerStudent(studentForUpdate);

		Course initialCourse = createDefaultCourse();
		sut.registerStudentCourses(initialCourse);

		List<Course> coursesFromDb = sut.searchStudentCourse(initialCourse.getStudentId());
		assertThat(coursesFromDb).hasSize(1);
		Course courseToUpdate = coursesFromDb.getFirst();

		String updatedCourseName = "更新後のテストコース名";
		courseToUpdate.setCourseName(updatedCourseName);

		sut.updateStudentCourses(courseToUpdate);

		List<Course> updatedCoursesFromDb = sut.searchStudentCourse(initialCourse.getStudentId());
		assertThat(updatedCoursesFromDb).hasSize(1);
	}

	//ヘルパーメソッド
	private Course createDefaultCourse() {
		return Course.builder()
				.id(999)
				.studentId(999)
				.courseName("テストコース")
				.courseStartDay(LocalDate.now())
				.courseCompletionDay(LocalDate.now().plusMonths(3))
				.build();
	}

	private Student createDefaultStudent() {
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

}