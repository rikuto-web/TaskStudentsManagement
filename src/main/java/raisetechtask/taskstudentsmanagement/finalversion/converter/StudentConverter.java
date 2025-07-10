package raisetechtask.taskstudentsmanagement.finalversion.converter;

import org.springframework.stereotype.Component;
import raisetechtask.taskstudentsmanagement.finalversion.data.ApplicationStatus;
import raisetechtask.taskstudentsmanagement.finalversion.data.Student;
import raisetechtask.taskstudentsmanagement.finalversion.data.StudentCourse;
import raisetechtask.taskstudentsmanagement.finalversion.domain.CourseApplicationDetail;
import raisetechtask.taskstudentsmanagement.finalversion.domain.StudentDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * 受講生情報に対して受講生コース情報や申込状況を一意のidで識別し変換処理を行います。
 */
@Component
public class StudentConverter {

	/**
	 * 複数のリストデータ（受講生、受講生コース、申込状況）を組み合わせて、
	 * 受講生の詳細情報（StudentDetail）のリストを作成します。
	 *
	 * @param studentList       全ての受講生のリスト
	 * @param studentCourseList 全ての受講生コースのリスト
	 * @param statusList        全ての申込状況のリスト
	 * @return 変換された受講生のリスト
	 */
	public List<StudentDetail> convertStudentDetails(List<Student> studentList,
	                                                 List<StudentCourse> studentCourseList,
	                                                 List<ApplicationStatus> statusList) {

		return buildStudentDetails(studentList, studentCourseList, statusList);
	}

	/**
	 * 受講生リストを基に、各受講生に紐づくコースと申込状況を統合し、StudentDetailのリストを構築します。
	 * このメソッドは、個々の受講生に紐づくコース詳細と申込状況を組み合わせます。
	 *
	 * @param studentList       全ての受講生のリスト
	 * @param studentCourseList 全ての受講生コースのリスト
	 * @param statusList        全ての申込状況のリスト
	 * @return 構築されたStudentDetailのリスト
	 */
	private static List<StudentDetail> buildStudentDetails(List<Student> studentList,
	                                                       List<StudentCourse> studentCourseList,
	                                                       List<ApplicationStatus> statusList) {
		List<StudentDetail> studentDetails = new ArrayList<>();

		for(Student student : studentList) {
			List<CourseApplicationDetail> courseApplicationDetails = new ArrayList<>();

			buildCourseApplicationDetailsForStudent(studentCourseList, statusList, student, courseApplicationDetails);
			studentDetails.add(new StudentDetail(student, courseApplicationDetails));
		}
		return studentDetails;
	}

	/**
	 * 特定の受講生に紐づく全ての受講生コースとそれに対応する申込状況を抽出し、
	 * CourseApplicationDetailのリストとして構築します。
	 *
	 * @param studentCourseList        全ての受講生コースのリスト
	 * @param statusList               全ての申込状況のリスト
	 * @param student                  処理対象の受講生
	 * @param courseApplicationDetails 構築されたCourseApplicationDetailを追加するリスト
	 * @throws IllegalStateException 対応する申込状況が見つからない場合
	 */
	private static void buildCourseApplicationDetailsForStudent(List<StudentCourse> studentCourseList,
	                                                            List<ApplicationStatus> statusList,
	                                                            Student student,
	                                                            List<CourseApplicationDetail> courseApplicationDetails) {
		for(StudentCourse course : studentCourseList) {
			if(student.getId() == course.getStudentId()) {
				ApplicationStatus actualStatus = null;

				actualStatus = findApplicationStatusForCourse(statusList, course, actualStatus);
				if(actualStatus == null) {
					throw new IllegalStateException("指定されたコースID " + course.getId() + " に対応する申込状況が存在しません。");
				}
				courseApplicationDetails.add(new CourseApplicationDetail(course, actualStatus));
			}
		}
	}

	/**
	 * 特定の受講生コースIDに紐づく申込状況をリストから検索し返します。
	 *
	 * @param statusList   全ての申込状況のリスト
	 * @param course       検索対象の受講生コース
	 * @param actualStatus 検索結果を格納するための現在の申込状況オブジェクト（変更されます）
	 * @return 見つかった申込状況、またはnull（見つからない場合）
	 */
	private static ApplicationStatus findApplicationStatusForCourse(List<ApplicationStatus> statusList,
	                                                                StudentCourse course,
	                                                                ApplicationStatus actualStatus) {
		for(ApplicationStatus status : statusList) {
			if(course.getId() == status.getStudentCourseId()) {
				actualStatus = status;
				break;
			}
		}
		return actualStatus;
	}
}