package raisetechtask.taskstudentsmanagement.finalversion.repository;

import org.apache.ibatis.annotations.Mapper;
import raisetechtask.taskstudentsmanagement.finalversion.data.StudentCourse;

import java.util.List;

/**
 * 受講生コーステーブルと紐づくrepositoryです。
 * 検索・登録・更新を行います。
 */
@Mapper
public interface CoursesRepository {

	/**
	 * コース情報の全件検索を行います。
	 *
	 * @return コース情報（全件）
	 */
	List<StudentCourse> searchCourses();

	/**
	 * 受講生IDに紐づくコース情報を検索します。
	 *
	 * @param studentId 受講生ID
	 * @return 受講生IDに紐づく受講生コース情報
	 */
	List<StudentCourse> searchStudentCourses(int studentId);

	/**
	 * 受講生IDと紐づいたコース情報の登録を行います。
	 *
	 * @param studentsStudentCourse 受講生のコース情報
	 */
	void registerStudentCourses(StudentCourse studentsStudentCourse);

	/**
	 * 受講生IDと紐づいたコース情報の更新を行います。
	 *
	 * @param studentStudentCourseList コース情報
	 */
	void updateStudentCourses(StudentCourse studentStudentCourseList);
}
