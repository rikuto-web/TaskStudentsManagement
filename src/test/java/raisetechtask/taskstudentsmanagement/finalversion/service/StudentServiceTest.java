package raisetechtask.taskstudentsmanagement.finalversion.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static raisetechtask.taskstudentsmanagement.finalversion.enums.ApplicationStatusEnum.KARI_MOSIKOMI;


@ExtendWith(MockitoExtension.class) // Mockitoを使用するためのJUnit 5 Extension
class StudentServiceTest {

	// @Mock: モックオブジェクトを作成
	@Mock
	private StudentsRepository studentRepository; // 名前をrepositoryからstudentRepositoryに変更しました

	@Mock
	private CoursesRepository coursesRepository;

	@Mock
	private StudentConverter converter;

	@Mock
	private ApplicationStatusRepository applicationStatusRepository;

	// @InjectMocks: モックオブジェクトをServiceに自動的に注入
	@InjectMocks
	private StudentService studentService; // sutからstudentServiceに変更しました

	// 各テストメソッド実行前にServiceインスタンスを初期化（@InjectMocksが自動でやってくれるので不要ですが、残しておいても問題ありません）
	@BeforeEach
	void setup() {
		// studentService = new StudentService(studentRepository, coursesRepository, converter, applicationStatusRepository);
		// @InjectMocksを使用しているため、この行は通常不要です。
		// MockitoExtensionが自動的にstudentServiceのコンストラクタにモックを注入してくれます。
	}

	@Test
	@DisplayName("全受講生の詳細一覧検索が正しく行われ、リポジトリとコンバーターが適切に呼び出されること")
	void searchStudentList_リポジトリとコンバーターが適切に呼び出されること() {
		// Given (準備):
		// ダミーのデータを用意します。ここではIDを持つStudentオブジェクトを具体的に作成します。
		// Serviceはリポジトリが正しいデータを返すことを「信頼する」ため、ダミーデータで十分です。
		Student student1 = Student.builder().id(1).fullName("田中 太郎").build();
		Student student2 = Student.builder().id(2).fullName("鈴木 花子").build();
		List<Student> dummyStudentList = List.of(student1, student2);

		StudentCourse course1 = StudentCourse.builder().id(101).studentId(1).courseName("Java").build();
		StudentCourse course2 = StudentCourse.builder().id(102).studentId(2).courseName("Ruby").build();
		List<StudentCourse> dummyStudentCourseList = List.of(course1, course2);

		ApplicationStatus status1 = ApplicationStatus.builder().id(201).studentCourseId(101).status(ApplicationStatusEnum.HON_MOSIKOMI).build();
		ApplicationStatus status2 = ApplicationStatus.builder().id(202).studentCourseId(102).status(ApplicationStatusEnum.JUKOCHU).build();
		List<ApplicationStatus> dummyStatusList = List.of(status1, status2);

		// コンバーターが返すStudentDetailのリストもダミーで用意
		StudentDetail dummyStudentDetail1 = new StudentDetail(student1, List.of(new CourseApplicationDetail(course1, status1)));
		StudentDetail dummyStudentDetail2 = new StudentDetail(student2, List.of(new CourseApplicationDetail(course2, status2)));
		List<StudentDetail> expectedStudentDetails = List.of(dummyStudentDetail1, dummyStudentDetail2);

		// Mockの設定:
		// Serviceがリポジトリを呼び出したときに、事前に用意したダミーデータを返すように設定します。
		when(studentRepository.searchStudentsList()).thenReturn(dummyStudentList);
		when(coursesRepository.searchCourses()).thenReturn(dummyStudentCourseList);
		when(applicationStatusRepository.searchStudentCourseStatusList()).thenReturn(dummyStatusList);

		// Serviceがコンバーターを呼び出したときに、期待するStudentDetailのリストを返すように設定します。
		when(converter.convertStudentDetails(dummyStudentList, dummyStudentCourseList, dummyStatusList))
				.thenReturn(expectedStudentDetails);

		// When (実行):
		// テスト対象のServiceメソッドを実行します。
		List<StudentDetail> actualResult = studentService.searchStudentList();

		// Then (検証):
		// 戻り値が期待通りであること
		assertThat(actualResult).isNotNull();
		assertThat(actualResult).isEqualTo(expectedStudentDetails); // 厳密にequalsで比較する場合はStudentDetailにequals/hashCodeを実装してください

		// リポジトリメソッドが正確に1回ずつ呼び出されたことを検証します。
		verify(studentRepository, times(1)).searchStudentsList();
		verify(coursesRepository, times(1)).searchCourses();
		verify(applicationStatusRepository, times(1)).searchStudentCourseStatusList();

		// コンバーターメソッドが正しい引数で正確に1回呼び出されたことを検証します。
		verify(converter, times(1)).convertStudentDetails(dummyStudentList, dummyStudentCourseList, dummyStatusList);
	}


	@Test
	@DisplayName("指定したIDの受講生詳細が取得できること")
	void searchStudent_指定したIDの受講生詳細が取得できること() {
		// Given (準備):
		int targetStudentId = 999;
		// 特定のIDを持つダミーの学生、コース、ステータスを作成
		Student dummyStudent = Student.builder().id(targetStudentId).fullName("テスト 太郎").build();
		StudentCourse dummyCourse = StudentCourse.builder().id(101).studentId(targetStudentId).courseName("テストコース").build();
		ApplicationStatus dummyStatus = ApplicationStatus.builder().id(201).studentCourseId(101).status(KARI_MOSIKOMI).build();

		List<StudentCourse> dummyCourseList = List.of(dummyCourse); // 1つのコースを持つリスト

		// Serviceメソッドが最終的に返すStudentDetailを直接準備
		CourseApplicationDetail dummyCourseApplicationDetail = new CourseApplicationDetail(dummyCourse, dummyStatus);
		StudentDetail expectedStudentDetail = new StudentDetail(dummyStudent, List.of(dummyCourseApplicationDetail));

		// Mockの設定:
		// Serviceがリポジトリを呼び出したときに、事前に用意したダミーデータを返すように設定します。
		when(studentRepository.searchStudent(targetStudentId)).thenReturn(dummyStudent);
		when(coursesRepository.searchStudentCourses(targetStudentId)).thenReturn(dummyCourseList);
		when(applicationStatusRepository.searchStudentCourseStatus(dummyCourse.getId())).thenReturn(dummyStatus); // コースIDで特定のステータスを返す

		// When (実行):
		StudentDetail actualResult = studentService.searchStudent(targetStudentId);

		// Then (検証):
		// 戻り値が期待通りであること
		assertThat(actualResult).isNotNull();
		assertThat(actualResult.getStudent().getId()).isEqualTo(expectedStudentDetail.getStudent().getId());
		assertThat(actualResult.getStudent().getFullName()).isEqualTo(expectedStudentDetail.getStudent().getFullName());
		assertThat(actualResult.getStudentCourseList()).hasSize(1);
		assertThat(actualResult.getStudentCourseList().get(0).getStudentCourse().getCourseName())
				.isEqualTo(expectedStudentDetail.getStudentCourseList().get(0).getStudentCourse().getCourseName());
		assertThat(actualResult.getStudentCourseList().get(0).getApplicationStatus().getStatus())
				.isEqualTo(expectedStudentDetail.getStudentCourseList().get(0).getApplicationStatus().getStatus());

		// リポジトリメソッドが正確に1回ずつ呼び出されたことを検証します。
		verify(studentRepository, times(1)).searchStudent(targetStudentId);
		verify(coursesRepository, times(1)).searchStudentCourses(targetStudentId);
		verify(applicationStatusRepository, times(1)).searchStudentCourseStatus(dummyCourse.getId()); // コースIDで呼び出されることを検証

		// コンバーターメソッドはsearchStudent()では呼び出されないため、呼び出されていないことを確認
		verify(converter, never()).convertStudentDetails(any(), any(), any());
	}


	@Test
	@DisplayName("存在しない受講生IDの場合にStudentNotFoundExceptionがスローされること")
	void searchStudent_存在しないIDの場合に例外がスローされること() {
		// Given (準備):
		int nonExistentStudentId = 9999;

		// Mockの設定:
		// リポジトリがnullを返すように設定し、学生が見つからないシナリオを模倣します。
		when(studentRepository.searchStudent(nonExistentStudentId)).thenReturn(null);

		// When & Then (実行と検証):
		// 特定の例外がスローされることを検証します。
		assertThatThrownBy(() -> studentService.searchStudent(nonExistentStudentId))
				.isInstanceOf(StudentNotFoundException.class) // StudentNotFoundExceptionがスローされること
				.hasMessage("Student with ID " + nonExistentStudentId + " not found."); // 例外メッセージも検証

		// リポジトリメソッドが正確に1回呼び出されたことを検証します。
		verify(studentRepository, times(1)).searchStudent(nonExistentStudentId);
		// 学生が見つからないため、コースやステータスのリポジトリは呼び出されないことを検証します。
		verify(coursesRepository, never()).searchStudentCourses(anyInt());
		verify(applicationStatusRepository, never()).searchStudentCourseStatus(anyInt());
	}


	@Test
	@DisplayName("新規受講生とコースが適切に登録されること")
	void registerStudent_新規受講生とコースが登録できること() {
		// Given (準備):
		// 登録する新しい受講生とコースのデータを作成します。
		// IDはDBで自動生成されることを想定し、ここでは設定しません。
		Student newStudent = Student.builder()
				.id(10)
				.fullName("新垣結衣")
				.furigana("アラガキユイ")
				.emailAddress("aragaki.yui@example.com")
				.build();

		StudentCourse newCourse = StudentCourse.builder()
				.courseName("Python入門")
				.build();
		ApplicationStatus newStatus = ApplicationStatus.builder()
				.status(KARI_MOSIKOMI)
				.build();

		// 登録用StudentDetailを作成
		CourseApplicationDetail courseDetailToRegister = new CourseApplicationDetail(newCourse, newStatus);
		StudentDetail studentDetailToRegister = new StudentDetail(newStudent, List.of(courseDetailToRegister));

		// Mockの設定:
		// studentRepository.registerStudent()が呼び出された際に、引数として渡されたStudentオブジェクトのIDを設定するようにモックします。
		// これはServiceが登録後にDBから割り当てられたIDを受け取る挙動をシミュレートします。
		doNothing().when(studentRepository).registerStudent(any(Student.class));
		// registerStudentCoursesとregisterStatusはvoidメソッドなので、何もしないように設定 (デフォルトでdoNothing)
		// doNothing().when(coursesRepository).registerStudentCourses(any(StudentCourse.class));
		// doNothing().when(applicationStatusRepository).registerStatus(anyInt(), any(String.class));


		// When (実行):
		StudentDetail actualRegisteredDetail = studentService.registerStudent(studentDetailToRegister);

		// Then (検証):
		// 戻り値のStudentDetailがnullではないこと
		assertThat(actualRegisteredDetail).isNotNull();
		// 登録後にService内で設定されたIDが正しいこと（モックで設定したIDと一致すること）
		assertThat(actualRegisteredDetail.getStudent().getId()).isEqualTo(10);
		assertThat(actualRegisteredDetail.getStudent().getFullName()).isEqualTo("新垣結衣");
		assertThat(actualRegisteredDetail.getStudentCourseList()).hasSize(1);
		assertThat(actualRegisteredDetail.getStudentCourseList().get(0).getStudentCourse().getStudentId()).isEqualTo(10);
		assertThat(actualRegisteredDetail.getStudentCourseList().get(0).getApplicationStatus().getStatus()).isEqualTo(KARI_MOSIKOMI);


		// リポジトリメソッドが正確に呼び出されたことを検証します。
		// registerStudentが1回、正しいStudentオブジェクトで呼び出されたこと
		verify(studentRepository, times(1)).registerStudent(newStudent); // newStudentオブジェクトが渡されたことを検証

	}
}