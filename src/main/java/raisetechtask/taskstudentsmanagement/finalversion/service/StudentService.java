package raisetechtask.taskstudentsmanagement.finalversion.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.ArrayList;
import java.util.List;

import static raisetechtask.taskstudentsmanagement.finalversion.enums.ApplicationStatusEnum.KARI_MOSIKOMI;


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
	 * @param studentId     　受講生
	 * @param status        　申込状況
	 */
	private void initStudentsCourse(StudentCourse studentCourse, int studentId, ApplicationStatus status) {
		LocalDate now = LocalDate.now();
		studentCourse.setStudentId(studentId);
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
		List<StudentCourse> studentCourseList = coursesRepository.searchCourses(); // 全コース取得
		List<ApplicationStatus> statusList = applicationStatusRepository.searchStudentCourseStatusList(); // 全ステータス取得
		return converter.convertStudentDetails(studentList, studentCourseList, statusList);
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
		Student student = studentRepository.searchStudent(id);

		if(student == null) {
			throw new StudentNotFoundException("Student with ID " + id + " not found.");
		}

		List<StudentCourse> studentCourseList = coursesRepository.searchStudentCourses(student.getId());

		List<CourseApplicationDetail> courseApplicationDetails = new ArrayList<>();
		for(StudentCourse studentCourse : studentCourseList) {
			ApplicationStatus status = applicationStatusRepository.searchStudentCourseStatus(studentCourse.getId());
			if(status == null) {
				throw new IllegalStateException("Course ID " + studentCourse.getId() + " にステータスが存在しません。");
			}
			courseApplicationDetails.add(new CourseApplicationDetail(studentCourse, status));
		}
		return new StudentDetail(student, courseApplicationDetails);
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
		studentRepository.registerStudent(student); // ここで student オブジェクトにIDが設定される

		// ⭐ 登録された学生のIDをローカル変数に取得する
		//    これにより、forEachループ内のラムダ式で常に有効なIDが利用できる
		final int registeredStudentId = student.getId();

		studentDetail.getStudentCourseList().forEach(courseApplicationDetail -> {
			StudentCourse studentCourse = courseApplicationDetail.getStudentCourse();
			ApplicationStatus status = courseApplicationDetail.getApplicationStatus();

			// initStudentsCourse の呼び出しも変更
			initStudentsCourse(studentCourse, registeredStudentId, status); // ⭐ 変更箇所

			coursesRepository.registerStudentCourses(studentCourse);
			// studentCourse.getId() は coursesRepository.registerStudentCourses の後に利用可能になるはず
			status.setStudentCourseId(studentCourse.getId());
			applicationStatusRepository.registerStatus(status.getStudentCourseId(), status.getStatus().name());
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
		for(CourseApplicationDetail courseApplicationDetail : studentDetail.getStudentCourseList()) {
			StudentCourse studentCourse = courseApplicationDetail.getStudentCourse();
			ApplicationStatus appStatus = courseApplicationDetail.getApplicationStatus();
			if(appStatus == null) {
				throw new IllegalStateException("申込ステータスが null です。Course ID: " + courseApplicationDetail.getStudentCourse().getId());
			}
			ApplicationStatusEnum updateStatus = appStatus.getStatus();

			coursesRepository.updateStudentCourses(studentCourse);
			applicationStatusRepository.updateStatus(studentCourse.getId(), updateStatus);
		}
	}

	public List<Student> searchStudentsByGender(String gender) {
		return studentRepository.searchStudentsByGender(gender);
	}
}