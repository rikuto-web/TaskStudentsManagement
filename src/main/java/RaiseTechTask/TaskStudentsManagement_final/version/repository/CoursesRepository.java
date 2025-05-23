package RaiseTechTask.TaskStudentsManagement_final.version.repository;

import RaiseTechTask.TaskStudentsManagement_final.version.data.Course;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 受講生コーステーブルと紐づくrepositoryです。
 */
@Mapper
public interface CoursesRepository {

	/**
	 * コース情報の全件検索を行います。
	 *
	 * @return コース情報（全件）
	 */
	List<Course> searchCourses();

	/**
	 * 受講生IDに紐づくコース情報を検索します。
	 *
	 * @param studentId 受講生ID
	 * @return 受講生IDに紐づく受講生コース情報
	 */
	List<Course> searchStudentCourse(int studentId);

	/**
	 * 受講生IDと紐づいたコース情報の登録を行います。
	 *
	 * @param studentsCourse 受講生のコース情報
	 */
	void registerStudentCourses(Course studentsCourse);

	/**
	 * コース名の更新を行います。
	 *
	 * @param studentCourseList コース情報
	 */
	void updateStudentCourses(Course studentCourseList);
}
