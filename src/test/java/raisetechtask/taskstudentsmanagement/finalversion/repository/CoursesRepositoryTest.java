package raisetechtask.taskstudentsmanagement.finalversion.repository;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetechtask.taskstudentsmanagement.finalversion.data.Student;
import raisetechtask.taskstudentsmanagement.finalversion.data.StudentCourse;

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
		List<StudentCourse> actual = sut.searchCourses();
		assertThat(actual.size()).isEqualTo(5);
	}

	@Test
	void 受講生IDに紐づいたコース情報の検索が行えること() {
		Student studentToRegister = createDefaultStudent();
		studentToRegister.setId(1);
		studentSut.registerStudent(studentToRegister);

		StudentCourse studentCourseToRegister = createDefaultCourse();
		sut.registerStudentCourses(studentCourseToRegister);


		List<StudentCourse> actualCours = sut.searchStudentCourses(studentCourseToRegister.getStudentId());

		StudentCourse foundStudentCourse = actualCours.getFirst();
		assertThat(actualCours)
				.isNotNull()
				.hasSize(1);
	}


	@Test
	void コース情報の登録が行えること() {
		Student studentForCourse = createDefaultStudent();
		studentForCourse.setId(999);
		studentSut.registerStudent(studentForCourse);

		StudentCourse studentCourseToRegister = createDefaultCourse();
		sut.registerStudentCourses(studentCourseToRegister);

		List<StudentCourse> allCours = sut.searchCourses();
		assertThat(allCours.size()).isEqualTo(6);
	}

	@Test
	void コース名の更新が行えること() {
		Student studentForUpdate = createDefaultStudent();
		studentForUpdate.setId(999);
		studentSut.registerStudent(studentForUpdate);

		StudentCourse initialStudentCourse = createDefaultCourse();
		sut.registerStudentCourses(initialStudentCourse);

		List<StudentCourse> coursesFromDb = sut.searchStudentCourses(initialStudentCourse.getStudentId());
		assertThat(coursesFromDb).hasSize(1);
		StudentCourse studentCourseToUpdate = coursesFromDb.getFirst();

		String updatedCourseName = "更新後のテストコース名";
		studentCourseToUpdate.setCourseName(updatedCourseName);

		sut.updateStudentCourses(studentCourseToUpdate);

		List<StudentCourse> updatedCoursesFromDb = sut.searchStudentCourses(initialStudentCourse.getStudentId());
		assertThat(updatedCoursesFromDb).hasSize(1);
		assertThat(updatedCoursesFromDb.getFirst().getCourseName()).isEqualTo(updatedCourseName);

	}

	//ヘルパーメソッド
	private StudentCourse createDefaultCourse() {
		return StudentCourse.builder()
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