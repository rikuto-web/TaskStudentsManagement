/*package raisetechtask.taskstudentsmanagement.finalversion.repository;

import org.junit.jupiter.api.BeforeEach;
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
	private CoursesRepository coursesSut;
	@Autowired
	private StudentsRepository studentSut;

	@Test
	void コース情報の全件検索が行えること() {
		List<StudentCourse> actual = coursesSut.searchCourses();
		assertThat(actual).isNotNull();
		assertThat(actual.size()).isEqualTo(5);
	}

	@Test
	void 受講生IDに紐づいたコース情報の検索が行えること() {
		Student newStudent = getNewStudent();
		studentSut.registerStudent(newStudent);
		int studentId = newStudent.getId();

		StudentCourse newCourse = StudentCourse.builder()
				.studentId(studentId)
				.courseName("テストコース")
				.courseStartDay(LocalDate.of(2025, 7, 1))
				.courseCompletionDay(LocalDate.of(2025, 7, 1).plusMonths(3))
				.build();
		coursesSut.registerStudentCourses(newCourse);

		List<StudentCourse> actualCours = coursesSut.searchStudentCourses(newCourse.getStudentId());

		assertThat(actualCours).isNotNull();
		assertThat(actualCours).hasSize(1);
	}


	@Test
	void コース情報の登録が行えること() {
		Student studentForCourse = getNewSecondStudent();
		studentForCourse.setId(999);
		studentSut.registerStudent(studentForCourse);

		StudentCourse studentCourseToRegister = createDefaultCourse();
		coursesSut.registerStudentCourses(studentCourseToRegister);

		List<StudentCourse> allCours = coursesSut.searchCourses();
		assertThat(allCours.size()).isEqualTo(6);
	}

	@Test
	void コース名の更新が行えること() {
		Student studentForUpdate = getNewSecondStudent();
		studentForUpdate.setId(999);
		studentSut.registerStudent(studentForUpdate);

		StudentCourse initialStudentCourse = createDefaultCourse();
		coursesSut.registerStudentCourses(initialStudentCourse);

		List<StudentCourse> coursesFromDb = coursesSut.searchStudentCourses(initialStudentCourse.getStudentId());
		assertThat(coursesFromDb).hasSize(1);
		StudentCourse studentCourseToUpdate = coursesFromDb.getFirst();

		String updatedCourseName = "更新後のテストコース名";
		studentCourseToUpdate.setCourseName(updatedCourseName);

		coursesSut.updateStudentCourses(studentCourseToUpdate);

		List<StudentCourse> updatedCoursesFromDb = coursesSut.searchStudentCourses(initialStudentCourse.getStudentId());
		assertThat(updatedCoursesFromDb).hasSize(1);
		assertThat(updatedCoursesFromDb.getFirst().getCourseName()).isEqualTo(updatedCourseName);

	}

	@BeforeEach
	void setupCommonTestData() {
		Student newStudent = Student.builder()
				.fullName("テスト太郎")
				.furigana("テストタロウ")
				.nickname("テス太")
				.emailAddress("test.taro@example.com")
				.address("東京都中央区")
				.age(28)
				.gender("男性")
				.remark("共通テスト用学生1")
				.isDeleted(false)
				.build();
		studentSut.registerStudent(newStudent);

		StudentCourse newStudentCourse = StudentCourse.builder()
				.studentId(newStudent.getId())
				.courseName("Web開発基礎")
				.courseStartDay(LocalDate.of(2025, 4, 1))
				.courseCompletionDay(LocalDate.of(2025, 6, 30))
				.build();
		coursesSut.registerStudentCourses(newStudentCourse);

		StudentCourse newStudentSecondCourse = StudentCourse.builder()
				.studentId(newStudent.getId())
				.courseName("データサイエンス入門")
				.courseStartDay(LocalDate.of(2025, 7, 1))
				.courseCompletionDay(LocalDate.of(2025, 9, 30))
				.build();
		coursesSut.registerStudentCourses(newStudentCourse);

		// --- 2. 2人目の学生とそのコースを登録 ---
		registeredStudent2 = Student.builder()
				.fullName("テスト花子")
				.furigana("テストハナコ")
				.nickname("ハナ")
				.emailAddress("test.hanako@example.com")
				.address("大阪府大阪市")
				.age(22)
				.gender("女性")
				.remark("共通テスト用学生2")
				.isDeleted(false)
				.build();
		studentRepository.registerStudent(registeredStudent2);

		registeredCourseForStudent2 = StudentCourse.builder()
				.studentId(registeredStudent2.getId())
				.courseName("AIプログラミング")
				.courseStartDay(LocalDate.of(2025, 5, 10))
				.courseCompletionDay(LocalDate.of(2025, 11, 10))
				.build();
		coursesRepository.registerStudentCourses(registeredCourseForStudent2);

		// 必要に応じて他の学生やコース、申込状況データもここで準備
	}
}

 */