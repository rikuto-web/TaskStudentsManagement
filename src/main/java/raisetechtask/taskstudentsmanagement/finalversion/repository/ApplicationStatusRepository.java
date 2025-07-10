package raisetechtask.taskstudentsmanagement.finalversion.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import raisetechtask.taskstudentsmanagement.finalversion.data.ApplicationStatus;
import raisetechtask.taskstudentsmanagement.finalversion.enums.ApplicationStatusEnum;

import java.util.List;

@Mapper

public interface ApplicationStatusRepository {

	/**
	 * 申込状況の検索（全件）を行います。
	 *
	 * @return 申込状況
	 */
	List<ApplicationStatus> searchStudentCourseStatusList();

	/**
	 * 受講生コースIDに紐づいた申込状況の検索を行います。
	 *
	 * @param studentCourseId 受講生コースID
	 * @return 受講生コースIDに紐づいた申込状況
	 */
	ApplicationStatus searchStudentCourseStatus(int studentCourseId);

	/**
	 * 受講生コースIDに紐づいた申込状況の登録を行います。
	 * 戻り値をintとし処理結果を行数で返します。
	 *
	 * @param studentCourseId 受講生コースID
	 * @param status          受講生コースIDに紐づいた申込状況
	 * @return 処理行数
	 */
	int registerStatus(@Param("studentCourseId") int studentCourseId, @Param("status") String status);


	/**
	 * 受講生コースIDに紐づいた申込状況の更新を行います。
	 * 戻り値をintとし処理結果を行数で返します。
	 *
	 * @param studentCourseId 受講生コースID
	 * @param Enumstatus      Enum文字の申込状況
	 * @return 処理行数
	 */
	int updateStatus(@Param("studentCourseId") int studentCourseId, @Param("status") ApplicationStatusEnum Enumstatus);

}
