package raisetechtask.taskstudentsmanagement.finalversion.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetechtask.taskstudentsmanagement.finalversion.converter.StudentConverter;
import raisetechtask.taskstudentsmanagement.finalversion.data.Course;
import raisetechtask.taskstudentsmanagement.finalversion.data.Student;
import raisetechtask.taskstudentsmanagement.finalversion.domain.StudentDetail;
import raisetechtask.taskstudentsmanagement.finalversion.repository.CoursesRepository;
import raisetechtask.taskstudentsmanagement.finalversion.repository.StudentsRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

	@Mock
	private StudentsRepository repository;

	@Mock
	private CoursesRepository coursesRepository;

	@Mock
	private StudentConverter converter;

	private StudentService sut;

	@BeforeEach
	void before() {
		sut = new StudentService(repository, coursesRepository, converter);

	}


	@Test
	void 受講生詳細の一覧検索_repositoryとconverterの処理が適切に呼び出せていること() {
		List<Student> studentList = new ArrayList<>();
		List<Course> courseList = new ArrayList<>();
		when(repository.searchStudentsList()).thenReturn(studentList);
		when(coursesRepository.searchCourses()).thenReturn(courseList);

		List<StudentDetail> actual = sut.searchStudentList();

		assertNotNull(actual);
		verify(repository, times(1)).searchStudentsList();
		verify(coursesRepository, times(1)).searchCourses();
		verify(converter, times(1)).convertStudentDetails(studentList, courseList);
	}

	@Test
	void 受講生IDで受講生の詳細を検索処理() {
		int testId = 0;
		Student student = new Student();
		student.setId(testId);
		List<Course> courseList = new ArrayList<>();
		when(repository.searchStudent(testId)).thenReturn(student);
		when(coursesRepository.searchStudentCourse(student.getId())).thenReturn(courseList);

		StudentDetail actual = sut.searchStudent(testId);
		StudentDetail expected = new StudentDetail(student, courseList);

		verify(repository, times(1)).searchStudent(testId);
		verify(coursesRepository, times(1)).searchStudentCourse(testId);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	void 受講生詳細の登録処理() {
		Student student = new Student();
		student.setId(1);
		List<Course> courseList = new ArrayList<>();
		courseList.add(new Course());
		courseList.add(new Course());
		StudentDetail studentDetail = new StudentDetail(student, courseList);

		//呼びだしたときに具体的になにをするか
		doAnswer(invocationOnMock -> {
			Student argument = invocationOnMock.getArgument(0);//呼びだされたときに最初の引数を取得する
			argument.setId(999);//取得した引数にあるIDを変更する（DB登録後更新されるため）
			return null;//repositoryがvoidのためnullを返す
		}).when(repository).registerStudent(any(Student.class));
		//呼びだしたときなにもしない
		doNothing().when(coursesRepository).registerStudentCourses(any(Course.class));//書かなくてもいいが可読性のため

		StudentDetail actualResultDetail = sut.registerStudent(studentDetail);

		verify(repository, times(1)).registerStudent(student);
		verify(coursesRepository, times(courseList.size())).registerStudentCourses(any(Course.class));
		Assertions.assertEquals(studentDetail, actualResultDetail);
		Assertions.assertEquals(999, actualResultDetail.getStudent().getId());
	}

	@Test
	void 受講生詳細の更新作業() {
		Student student = new Student();
		List<Course> courseList = new ArrayList<>();
		StudentDetail studentDetail = new StudentDetail(student, courseList);

		sut.updateStudent(studentDetail);

		verify(repository, times(1)).updateStudent(studentDetail.getStudent());
		verify(coursesRepository, times(studentDetail.getStudentCourseList().size())).updateStudentCourses(any(Course.class));

	}
}