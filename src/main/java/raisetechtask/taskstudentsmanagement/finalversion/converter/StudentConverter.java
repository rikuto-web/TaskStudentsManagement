package raisetechtask.taskstudentsmanagement.finalversion.converter;

import org.springframework.stereotype.Component;
import raisetechtask.taskstudentsmanagement.finalversion.data.ApplicationStatus;
import raisetechtask.taskstudentsmanagement.finalversion.data.Course;
import raisetechtask.taskstudentsmanagement.finalversion.data.Student;
import raisetechtask.taskstudentsmanagement.finalversion.domain.StudentDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 受講生城砦を受講生やコース情報、もしくはその逆の変換を行うコンバーターです
 */
@Component
public class StudentConverter {
	/**
	 * 受講生に紐づく受講生コース情報をマッピングする
	 * 受講生コース情報は受講生に対し手複数存在するのでループを回して受講生詳細情報を組み立てる
	 * 。
	 *
	 * @param studentList 受講生一覧
	 * @param courseList  受講生コース情報のリスト
	 * @return 受講生詳細情報のリスト
	 */
	public List<StudentDetail> convertStudentDetails(List<Student> studentList, List<Course> courseList, List<ApplicationStatus> statusList) {
		List<StudentDetail> studentDetails = new ArrayList<>();

		studentList.forEach(student -> {
			StudentDetail studentDetail = new StudentDetail();
			studentDetail.setStudent(student);

			// 1. 受講生に紐づくコースを全て抽出
			List<Course> convertStudentCourses = courseList.stream()
					.filter(course -> student.getId() == (course.getStudentId()))
					.collect(Collectors.toList());
			studentDetail.setStudentCourseList(convertStudentCourses);

			// 2. StudentDetail に設定する単一の ApplicationStatus を決定するロジック
			//    ここでは、もし学生にコースが1つでもあれば、その最初のコースのStatusを設定します。
			//    もしコースがなければ、Statusはnullになります。
			ApplicationStatus representativeStatus = null;

			if ( ! convertStudentCourses.isEmpty() ) {
				Course firstCourse = convertStudentCourses.getFirst();
				Optional<ApplicationStatus> foundStatus = statusList.stream()
						.filter(status -> firstCourse.getId() == status.getStudentCourseId())
						.findFirst();

				if ( foundStatus.isPresent() ) {
					representativeStatus = foundStatus.get();
				}
			}
			// 決定した単一のStatusをStudentDetailに設定
			studentDetail.setStatus(representativeStatus);

			studentDetails.add(studentDetail);
		});
		return studentDetails;
	}
}