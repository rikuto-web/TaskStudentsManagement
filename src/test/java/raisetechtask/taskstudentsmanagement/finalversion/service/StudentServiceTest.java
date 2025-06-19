package raisetechtask.taskstudentsmanagement.finalversion.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetechtask.taskstudentsmanagement.finalversion.converter.StudentConverter;
import raisetechtask.taskstudentsmanagement.finalversion.data.ApplicationStatus;
import raisetechtask.taskstudentsmanagement.finalversion.data.Course;
import raisetechtask.taskstudentsmanagement.finalversion.data.Student;
import raisetechtask.taskstudentsmanagement.finalversion.domain.StudentDetail;
import raisetechtask.taskstudentsmanagement.finalversion.repository.ApplicationStatusRepository;
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
import static raisetechtask.taskstudentsmanagement.finalversion.data.ApplicationStatusEnum.JUKOCHU;
import static raisetechtask.taskstudentsmanagement.finalversion.data.ApplicationStatusEnum.KARI_MOSIKOMI;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

	@Mock
	private StudentsRepository repository;

	@Mock
	private CoursesRepository coursesRepository;

	@Mock
	private StudentConverter converter;

	@Mock
	private ApplicationStatusRepository applicationStatusRepository;

	private StudentService sut;

	@BeforeEach
	void before() {
		sut = new StudentService(repository, coursesRepository, converter, applicationStatusRepository);

	}


	@Test
	void 受講生詳細の一覧検索_repositoryとconverterの処理が適切に呼び出せていること() {
		List<Student> studentList = getStudents();

		List<Course> courseList = getCourses();

		List<ApplicationStatus> statusList = getStatus();

		List<StudentDetail> expectedStudentDetails = new ArrayList<>();
		expectedStudentDetails.add(new StudentDetail());
		expectedStudentDetails.add(new StudentDetail());

		when(repository.searchStudentsList()).thenReturn(studentList);
		when(coursesRepository.searchCourses()).thenReturn(courseList);
		when(converter.convertStudentDetails(studentList, courseList, statusList)).thenReturn(expectedStudentDetails);
		//実行
		List<StudentDetail> actual = sut.searchStudentList();
		//検証
		assertNotNull(actual);
		verify(repository, times(1)).searchStudentsList();
		verify(coursesRepository, times(1)).searchCourses();
		verify(converter, times(1)).convertStudentDetails(studentList, courseList, statusList);
		Assertions.assertEquals(expectedStudentDetails, actual);
	}

	@Test
	void 受講生IDで受講生の詳細を検索処理() {
		int testId = 0;
		int testCpurseId = 0;
		Student student = new Student();
		student.setId(testId);
		List<Course> courseList = new ArrayList<>();
		ApplicationStatus status = new ApplicationStatus();
		status.setStudentCourseId(testCpurseId);

		when(repository.searchStudent(testId)).thenReturn(student);
		when(coursesRepository.searchStudentCourse(student.getId())).thenReturn(courseList);

		StudentDetail actual = sut.searchStudent(testId);
		StudentDetail expected = new StudentDetail(student, courseList, status);

		verify(repository, times(1)).searchStudent(testId);
		verify(coursesRepository, times(1)).searchStudentCourse(testId);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	void 受講生詳細の登録処理() {
		int testCpurseId = 0;
		Student student = new Student();
		student.setId(1);
		List<Course> courseList = new ArrayList<>();
		courseList.add(new Course());
		courseList.add(new Course());
		ApplicationStatus status = new ApplicationStatus();
		status.setStudentCourseId(testCpurseId);
		StudentDetail studentDetail = new StudentDetail(student, courseList, status);

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
		ApplicationStatus status = new ApplicationStatus();
		StudentDetail studentDetail = new StudentDetail(student, courseList, status);

		sut.updateStudent(studentDetail);

		verify(repository, times(1)).updateStudent(studentDetail.getStudent());
		verify(coursesRepository, times(studentDetail.getStudentCourseList().size())).updateStudentCourses(any(Course.class));

	}

	private static List<ApplicationStatus> getStatus() {
		ApplicationStatus status1 = new ApplicationStatus();
		status1.setStudentCourseId(999);
		status1.setId(222);
		status1.setStatus(KARI_MOSIKOMI);
		ApplicationStatus status2 = new ApplicationStatus();
		status2.setStudentCourseId(888);
		status2.setId(555);
		status2.setStatus(JUKOCHU);

		List<ApplicationStatus> statusList = new ArrayList<>();
		statusList.add(status1);
		statusList.add(status2);
		return statusList;
	}

	private static List<Course> getCourses() {
		Course course1 = new Course();
		course1.setStudentId(999);
		course1.setId(111);
		course1.setCourseName("Java");

		Course course2 = new Course();
		course1.setStudentId(888);
		course1.setId(333);
		course1.setCourseName("Ruby");

		List<Course> courseList = new ArrayList<>();
		courseList.add(course1);
		courseList.add(course2);
		return courseList;
	}

	private static List<Student> getStudents() {
		Student student1 = new Student();
		student1.setId(999);
		student1.setFullName("田中太郎");
		student1.setFurigana("タナカタロウ");
		student1.setEmailAddress("tgyhu@ghu.com");
		student1.setAddress("東京");
		student1.setAge(20);
		student1.setGender("男性");

		Student student2 = new Student();
		student1.setId(888);
		student1.setFullName("田中次郎");
		student1.setFurigana("タナカジロウ");
		student1.setEmailAddress("tgy@ghu.com");
		student1.setAddress("大阪");
		student1.setAge(55);
		student1.setGender("男性");

		List<Student> studentList = new ArrayList<>();
		studentList.add(student1);
		studentList.add(student2);
		return studentList;
	}
}