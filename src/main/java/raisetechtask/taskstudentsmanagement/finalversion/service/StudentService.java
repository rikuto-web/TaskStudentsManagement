package raisetechtask.taskstudentsmanagement.finalversion.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetechtask.taskstudentsmanagement.finalversion.converter.StudentConverter;
import raisetechtask.taskstudentsmanagement.finalversion.data.ApplicationStatus;
import raisetechtask.taskstudentsmanagement.finalversion.data.ApplicationStatusEnum;
import raisetechtask.taskstudentsmanagement.finalversion.data.Course;
import raisetechtask.taskstudentsmanagement.finalversion.data.Student;
import raisetechtask.taskstudentsmanagement.finalversion.domain.StudentDetail;
import raisetechtask.taskstudentsmanagement.finalversion.repository.ApplicationStatusRepository;
import raisetechtask.taskstudentsmanagement.finalversion.repository.CoursesRepository;
import raisetechtask.taskstudentsmanagement.finalversion.repository.StudentsRepository;

import java.time.LocalDate;
import java.util.List;

import static raisetechtask.taskstudentsmanagement.finalversion.data.ApplicationStatusEnum.KARI_MOSIKOMI;


/**
 * 受講生情報を取り扱うサービスです
 * 受講生の検索や登録・更新を行います
 */
@Service
public class StudentService {

	private final StudentsRepository studentRepository;
	private final CoursesRepository coursesRepository;
	private final StudentConverter converter;
	private final ApplicationStatusRepository applicationStatusRepository;

	public StudentService(StudentsRepository studentRepository, CoursesRepository coursesRepository, StudentConverter converter, ApplicationStatusRepository applicationStatusRepository) {
		this.studentRepository = studentRepository;
		this.coursesRepository = coursesRepository;
		this.converter = converter;
		this.applicationStatusRepository = applicationStatusRepository;
	}


	/**
	 * 受講生コース情報を登録する際の初期情報を設定する。
	 * コース情報の詳細内容と、申込助教を仮申込で登録を行う。
	 *
	 * @param studentCourse 受講生コース情報
	 * @param student       　受講生
	 * @param status        　申込状況
	 */
	void initStudentsCourse(Course studentCourse, Student student, ApplicationStatus status) {
		LocalDate now = LocalDate.now();
		studentCourse.setStudentId(student.getId());
		studentCourse.setCourseStartDay(now);
		studentCourse.setCourseCompletionDay(now.plusYears(1));

		status.setStatus(KARI_MOSIKOMI);
	}

	/**
	 * 受講生の詳細一覧検索を行います。
	 * 条件検索を行うので、条件指定は行いません。
	 * 受講生情報と受講生ID（PK）に紐づいたコース情報と申込状況を取得したリスト
	 *
	 * @return 受講生一覧（全件）
	 */
	@Transactional
	public List<StudentDetail> searchStudentList() {
		List<Student> studentList = studentRepository.searchStudentsList();
		List<Course> courseList = coursesRepository.searchCourses();
		List<ApplicationStatus> statusList = applicationStatusRepository.searchStudentCourseStatusList();
		return converter.convertStudentDetails(studentList, courseList, statusList);//ToDo converterクラス修正
	}

	/**
	 * 受講生IDで受講生の詳細を検索処理を行います。。
	 * IDに紐づく受講生情報を取得したあと、その受講生に紐づく受講生コース情報と申込状況を取得して設定します。
	 *
	 * @param id 　受講生ID
	 * @return 受講生
	 */
	@Transactional
	public StudentDetail searchStudent(int id) {
		Student student = studentRepository.searchStudent(id);//受講生のID検索
		List<Course> courseList = coursesRepository.searchStudentCourse(student.getId());//受講生IDに紐づいたコース検索
		if(courseList.isEmpty()) {
			return null; // 想定していないがコースがなければnullを返す
		}
		int courseId = courseList.getFirst().getId();//Listにある最初のIDを取得

		ApplicationStatus status = applicationStatusRepository.searchStudentCourseStatus(courseId);//intで取得したコースIDを渡す
		return new StudentDetail(student, courseList, status);
	}

	/**
	 * 受講生詳細の登録処理を行います。
	 * コース情報は、受講生ＩＤと紐づけて登録します。
	 * 申込状況はコースIDと紐づけて登録します
	 * 受講開始日と修了予定日と申込状況（仮申込）を自動設定して登録します。
	 *
	 * @param studentDetail 受講生情報
	 * @return 登録情報を付与した受講生詳細
	 */
	@Transactional
	public StudentDetail registerStudent(StudentDetail studentDetail) {
		Student student = studentDetail.getStudent();
		studentRepository.registerStudent(student);//受講生の登録

		studentDetail.getStudentCourseList().forEach(studentCourse -> {  //コース情報の登録をする際に申込状況も登録
			ApplicationStatus status = new ApplicationStatus();//ApplicationStatusをインスタンス化
			initStudentsCourse(studentCourse, student, status);
			coursesRepository.registerStudentCourses(studentCourse);
			status.setStudentCourseId(studentCourse.getId());//コース情報を登録したときに自動採番されたIDをStatusに設定
			applicationStatusRepository.registerStatus(status);//コースIDに紐づいた申込状況の登録
		});
		return studentDetail;
	}

	/**
	 * 受講生詳細の更新作業を行います。
	 * 受講生と受講生IDと紐づいたコース情報を更新します。
	 * コース情報に紐づいた申込状況の更新も行う。
	 *
	 * @param studentDetail 受講生情報とコース情報
	 */
	@Transactional
	public void updateStudent(StudentDetail studentDetail) {
		studentRepository.updateStudent(studentDetail.getStudent());
		for(Course studentCourse : studentDetail.getStudentCourseList()) {
			coursesRepository.updateStudentCourses(studentCourse);

			ApplicationStatusEnum updateStatus = studentDetail.getStatus().getStatus();//studentDetailにあるstatusにあるEnum型のstatus
			int courseId = studentCourse.getId();
			applicationStatusRepository.searchStudentCourseStatus(courseId);
			applicationStatusRepository.updateStatus(courseId, updateStatus); //コースIDとstatusをセットして更新
		}
	}
}