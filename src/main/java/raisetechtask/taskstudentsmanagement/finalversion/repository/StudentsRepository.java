package raisetechtask.taskstudentsmanagement.finalversion.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import raisetechtask.taskstudentsmanagement.finalversion.data.Student;

import java.util.List;

/**
 * 受講生テーブルと紐づくrepositoryです。
 * 受講生の検索・登録・更新を行います。
 */
@Mapper
public interface StudentsRepository {

	/**
	 * 受講生の全件検索を行います。
	 *
	 * @return 受講生一覧
	 */
	List<Student> searchStudentsList();

	/**
	 * 単独の受講生の検索を行います。
	 *
	 * @param id 　受講生ID
	 * @return 受講生
	 */
	Student searchStudent(int id);

	/**
	 * 受講生の登録を行います。IDに関しては自動採番を行います。
	 *
	 * @param student 個人情報
	 */
	void registerStudent(Student student);

	/**
	 * 受講生情報の更新を行います。
	 *
	 * @param student 個人情報
	 */
	void updateStudent(Student student);

	/**
	 * 性別を指定して受講生を検索します。
	 *
	 * @param gender 検索したい性別（例: "男性", "女性"）
	 * @return 該当する受講生の一覧
	 */
	//
	List<Student> searchStudentsByGender(@Param("gender") String gender);
}
