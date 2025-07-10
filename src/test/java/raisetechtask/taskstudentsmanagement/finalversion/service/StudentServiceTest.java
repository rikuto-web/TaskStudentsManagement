package raisetechtask.taskstudentsmanagement.finalversion.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import raisetechtask.taskstudentsmanagement.finalversion.converter.StudentConverter;
import raisetechtask.taskstudentsmanagement.finalversion.data.ApplicationStatus;
import raisetechtask.taskstudentsmanagement.finalversion.data.Student;
import raisetechtask.taskstudentsmanagement.finalversion.data.StudentCourse;
import raisetechtask.taskstudentsmanagement.finalversion.domain.CourseApplicationDetail;
import raisetechtask.taskstudentsmanagement.finalversion.domain.StudentDetail;
import raisetechtask.taskstudentsmanagement.finalversion.enums.ApplicationStatusEnum;
import raisetechtask.taskstudentsmanagement.finalversion.exception.StudentNotFoundException;
import raisetechtask.taskstudentsmanagement.finalversion.repository.ApplicationStatusRepository;
import raisetechtask.taskstudentsmanagement.finalversion.repository.CoursesRepository;
import raisetechtask.taskstudentsmanagement.finalversion.repository.StudentsRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static raisetechtask.taskstudentsmanagement.finalversion.enums.ApplicationStatusEnum.KARI_MOSIKOMI;


@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

	@Mock
	private StudentsRepository studentRepository;

	@Mock
	private CoursesRepository coursesRepository;

	@Mock
	private StudentConverter converter;

	@Mock
	private ApplicationStatusRepository applicationStatusRepository;

	@InjectMocks
	private StudentService studentService;

	@Test
	void searchStudentList_リポジトリとコンバーターが適切に呼び出されること() {
		List<Student> dummyStudentList = List.of(
				Student.builder().id(1).fullName("田中 太郎").build(),
				Student.builder().id(2).fullName("鈴木 花子").build()
		);
		List<StudentCourse> dummyStudentCourseList = List.of(
				StudentCourse.builder().id(101).studentId(1).courseName("Java").build(),
				StudentCourse.builder().id(102).studentId(2).courseName("Ruby").build()
		);
		List<ApplicationStatus> dummyStatusList = List.of(
				ApplicationStatus.builder().id(201).studentCourseId(101).status(ApplicationStatusEnum.HON_MOSIKOMI).build(),
				ApplicationStatus.builder().id(202).studentCourseId(102).status(ApplicationStatusEnum.JUKOCHU).build()
		);

		StudentDetail expectedStudentDetail1 = new StudentDetail(Student.builder().id(1).build(), List.of());
		StudentDetail expectedStudentDetail2 = new StudentDetail(Student.builder().id(2).build(), List.of());
		List<StudentDetail> expectedServiceResult = List.of(expectedStudentDetail1, expectedStudentDetail2);

		when(studentRepository.searchStudentsList()).thenReturn(dummyStudentList);
		when(coursesRepository.searchCourses()).thenReturn(dummyStudentCourseList);
		when(applicationStatusRepository.searchStudentCourseStatusList()).thenReturn(dummyStatusList);
		when(converter.convertStudentDetails(dummyStudentList, dummyStudentCourseList, dummyStatusList))
				.thenReturn(expectedServiceResult);

		List<StudentDetail> actualResult = studentService.searchStudentList();

		verify(studentRepository, times(1)).searchStudentsList();
		verify(coursesRepository, times(1)).searchCourses();
		verify(applicationStatusRepository, times(1)).searchStudentCourseStatusList();
		verify(converter, times(1)).convertStudentDetails(dummyStudentList, dummyStudentCourseList, dummyStatusList);
	}


	@Test
	void searchStudent_指定したIDの受講生詳細が取得できること() {
		int targetStudentId = 999;
		Student dummyStudent = Student.builder().id(targetStudentId).fullName("テスト 太郎").build();

		StudentCourse dummyCourse = StudentCourse.builder().id(101).studentId(targetStudentId).courseName("テストコース").build();
		ApplicationStatus dummyStatus = ApplicationStatus.builder().id(201).studentCourseId(101).status(KARI_MOSIKOMI).build();

		List<StudentCourse> dummyCourseList = List.of(dummyCourse);

		StudentDetail expectedServiceResult = new StudentDetail(dummyStudent, List.of(new CourseApplicationDetail(dummyCourse, dummyStatus)));

		when(studentRepository.searchStudent(targetStudentId)).thenReturn(dummyStudent);
		when(coursesRepository.searchStudentCourses(targetStudentId)).thenReturn(dummyCourseList);
		when(applicationStatusRepository.searchStudentCourseStatus(dummyCourse.getId())).thenReturn(dummyStatus);

		StudentDetail actualResult = studentService.searchStudent(targetStudentId);

		assertThat(actualResult).isNotNull();
		assertThat(actualResult.getStudent()).isEqualTo(dummyStudent);
		assertThat(actualResult.getStudentCourseList()).hasSize(1);
		assertThat(actualResult.getStudentCourseList().get(0).getStudentCourse()).isEqualTo(dummyCourse);
		assertThat(actualResult.getStudentCourseList().get(0).getApplicationStatus()).isEqualTo(dummyStatus);

		verify(studentRepository, times(1)).searchStudent(targetStudentId);
		verify(coursesRepository, times(1)).searchStudentCourses(targetStudentId);
		verify(applicationStatusRepository, times(1)).searchStudentCourseStatus(dummyCourse.getId());
		verify(converter, never()).convertStudentDetails(any(), any(), any());
	}


	@Test
	void searchStudent_存在しないIDの場合に例外がスローされること() {
		int nonExistentStudentId = 9999;

		when(studentRepository.searchStudent(nonExistentStudentId)).thenReturn(null);

		assertThatThrownBy(() -> studentService.searchStudent(nonExistentStudentId))
				.isInstanceOf(StudentNotFoundException.class)
				.hasMessage("指定された受講生" + nonExistentStudentId + "が見つかりませんでした。");

		verify(studentRepository, times(1)).searchStudent(nonExistentStudentId);
		verify(coursesRepository, never()).searchStudentCourses(anyInt());
		verify(applicationStatusRepository, never()).searchStudentCourseStatus(anyInt());
	}


	@Test
	void registerStudent_新規受講生とコースが登録できること_ミニマムバージョン() {
		Student newStudentInput = Student.builder()
				.fullName("テストテスト")
				.furigana("テストテスト")
				.emailAddress("testtest@example.com")
				.build();

		StudentCourse newCourseInput = StudentCourse.builder()
				.courseName("Python入門")
				.courseStartDay(LocalDate.of(2000, 1, 1))
				.courseCompletionDay(LocalDate.of(2001, 1, 1))
				.build();

		ApplicationStatus newStatusInput = ApplicationStatus.builder()
				.status(ApplicationStatusEnum.HON_MOSIKOMI)
				.build();

		CourseApplicationDetail courseDetailToRegister = new CourseApplicationDetail(newCourseInput, newStatusInput);
		StudentDetail studentDetailToRegister = new StudentDetail(newStudentInput, List.of(courseDetailToRegister));

		final int expectedStudentIdAfterRegistration = 10;
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Student student = invocation.getArgument(0);
				student.setId(expectedStudentIdAfterRegistration);
				return null;
			}
		}).when(studentRepository).registerStudent(any(Student.class));

		final int expectedCourseIdAfterRegistration = 1001;
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				StudentCourse course = invocation.getArgument(0);
				course.setId(expectedCourseIdAfterRegistration);
				course.setStudentId(expectedStudentIdAfterRegistration);
				course.setCourseStartDay(LocalDate.now());
				course.setCourseCompletionDay(LocalDate.now().plusYears(1));
				return null;
			}
		}).when(coursesRepository).registerStudentCourses(any(StudentCourse.class));

		studentService.registerStudent(studentDetailToRegister);

		verify(studentRepository, times(1)).registerStudent(newStudentInput);
		verify(coursesRepository, times(1)).registerStudentCourses(newCourseInput);
		verify(applicationStatusRepository, times(1)).registerStatus(eq(expectedCourseIdAfterRegistration), eq(KARI_MOSIKOMI.name()));
	}
}