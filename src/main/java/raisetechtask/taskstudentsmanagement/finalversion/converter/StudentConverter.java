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
 * 受講生情報を受講生やコース情報、もしくはその逆の変換を行うコンバーターです
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
	 * @return 変換された {@code StudentDetail} のリスト
	 */
	public List<StudentDetail> convertStudentDetails(List<Student> studentList, List<StudentCourse> studentCourseList, List<ApplicationStatus> statusList) {
		List<StudentDetail> studentDetails = new ArrayList<>();

		for(Student student : studentList) {
			List<CourseApplicationDetail> courseApplicationDetails = new ArrayList<>();

			for(StudentCourse course : studentCourseList) {
				if(student.getId() == course.getStudentId()) {
					ApplicationStatus actualStatus = null; // 実際のApplicationStatusオブジェクト

					for(ApplicationStatus status : statusList) {
						if(course.getId() == status.getStudentCourseId()) {
							actualStatus = status; // 実際のStatusオブジェクトを代入
							break; // 見つかったらループを抜ける
						}
					}
					if(actualStatus == null) {
						throw new IllegalStateException("Course ID " + course.getId() + " に対応する申込ステータスが存在しません。");
					}
					courseApplicationDetails.add(new CourseApplicationDetail(course, actualStatus));
				}
			}
			studentDetails.add(new StudentDetail(student, courseApplicationDetails));
		}

		return studentDetails;
	}
}