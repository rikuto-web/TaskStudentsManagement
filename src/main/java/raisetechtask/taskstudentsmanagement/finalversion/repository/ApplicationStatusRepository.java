package raisetechtask.taskstudentsmanagement.finalversion.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import raisetechtask.taskstudentsmanagement.finalversion.data.ApplicationStatus;
import raisetechtask.taskstudentsmanagement.finalversion.data.ApplicationStatusEnum;

import java.util.List;

@Mapper

public interface ApplicationStatusRepository {

	//申し込み状況の全件検索
	List<ApplicationStatus> searchStudentCourseStatusList();

	//コースIDに紐づいた申し込み状況の検索
	ApplicationStatus searchStudentCourseStatus(int studentCourseId);

	//申し込み状況の登録処理_戻り値をintにすることで整数で成功・失敗の判断を行う
	int registerStatus(ApplicationStatus applicationStatus);

	//申し込み状況の更新処理_戻り値をintにすることで整数で成功・失敗の判断を行う
	int updateStatus(@Param("studentCourseId") int studentCourseId, @Param("status") ApplicationStatusEnum Enumstatus);

}
