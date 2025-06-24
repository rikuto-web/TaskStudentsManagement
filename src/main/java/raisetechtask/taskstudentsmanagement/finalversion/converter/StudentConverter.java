package raisetechtask.taskstudentsmanagement.finalversion.converter;

import org.springframework.stereotype.Component;
import raisetechtask.taskstudentsmanagement.finalversion.data.ApplicationStatus;
import raisetechtask.taskstudentsmanagement.finalversion.data.Course;
import raisetechtask.taskstudentsmanagement.finalversion.data.Student;
import raisetechtask.taskstudentsmanagement.finalversion.domain.StudentDetail;

import java.util.ArrayList;
import java.util.List;
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

			List<Course> convertStudentCourses = courseList.stream()
					.filter(course -> student.getId() == (course.getStudentId()))//IDが一致するか確認
					.collect(Collectors.toList());//一致したものをList化
			studentDetail.setStudentCourseList(convertStudentCourses);//Detailにセット

			ApplicationStatus representativeStatus = null;

			if(! convertStudentCourses.isEmpty()) {  //上でList化したコース情報が空ではない場合（正常）　仮に空だった場合nullとなる
				Course firstCourse = convertStudentCourses.getFirst();
				representativeStatus = statusList.stream()
						.filter(status -> firstCourse.getId() == status.getStudentCourseId())//IDが一致するか
						.findFirst()
						//コースIDに一致する申込状況がない場合は例外を投げる
						.orElseThrow(() -> new IllegalArgumentException("対象のステータスが見つかりません。コースID: " + firstCourse.getId()));
			}
			studentDetail.setStatus(representativeStatus);
			studentDetails.add(studentDetail);
		});
		return studentDetails;
	}
}
