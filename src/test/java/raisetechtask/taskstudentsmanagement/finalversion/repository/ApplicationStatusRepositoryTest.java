package raisetechtask.taskstudentsmanagement.finalversion.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import raisetechtask.taskstudentsmanagement.finalversion.data.ApplicationStatus;
import raisetechtask.taskstudentsmanagement.finalversion.data.ApplicationStatusEnum;
import raisetechtask.taskstudentsmanagement.finalversion.data.Course;
import raisetechtask.taskstudentsmanagement.finalversion.data.Student;
import raisetechtask.taskstudentsmanagement.finalversion.demoDate;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doNothing;
import static raisetechtask.taskstudentsmanagement.finalversion.data.ApplicationStatusEnum.fromDisplayValue;

@MybatisTest
@ExtendWith(MockitoExtension.class)
@ComponentScan(basePackages = "raisetechtask.taskstudentsmanagement.finalversion")
class ApplicationStatusRepositoryTest {


	@Autowired //実際にDBアクセスするので @Autowired
	private ApplicationStatusRepository statusSut;

	@Autowired
	public demoDate demoDate;

	@Mock
	private StudentsRepository studentSut;

	@Mock
	private CoursesRepository courseSut;


	@Test
	void 申し込み状況の全件検索が行えること() {
		List<ApplicationStatus> actual = statusSut.searchStudentCourseStatusList();
		assertThat(actual.size()).isEqualTo(5);
	}

	@Test
	void コースIDに紐づいた申し込み状況の検索_正常系() {
		int testCourseId = 101; // 実際に存在するIDを使用
		ApplicationStatusEnum expectedStatus = ApplicationStatusEnum.KARI_MOSIKOMI;
		// Act:
		ApplicationStatus actual = statusSut.searchStudentCourseStatus(testCourseId);
		// Assert:
		assertThat(actual).isNotNull();
		assertThat(actual.getStudentCourseId()).isEqualTo(testCourseId);
		assertThat(actual.getStatus()).isEqualTo(expectedStatus);
	}

	@Test
	void 存在しないコースIDで検索された場合Nullを返すこと() {
		int testCourseId = 987654321; // 確実に存在しないID
		// Act:
		ApplicationStatus actual = statusSut.searchStudentCourseStatus(testCourseId);
		// Assert:
		assertThat(actual).isNull();
	}

	@Test
	void 受講生IDとコースIDに紐づいた申込状況を登録することができること() {
		int testCourseId = 74;
		registedDemoStudent();//受講生の登録操作のみスルー　実体はなし
		registedDemoCourse();//受講生コースの登録操作のみスルー　実体はなし
		ApplicationStatus demoStatus = demoDate.createDefaultStatus();//ダミーの申込状況情報
		demoStatus.setStudentCourseId(testCourseId);//紐づいたコースIDを設定
		demoStatus.setStatus(ApplicationStatusEnum.HON_MOSIKOMI);//登録する申込状況の設定
		// Act
		int registedStatus = statusSut.registerStatus(demoStatus); // 申込状況のみＨ２に登録
		ApplicationStatus actual = statusSut.searchStudentCourseStatus(testCourseId);//登録後に検索して検証へ
		// Assert:
		assertThat(registedStatus).isEqualTo(1); // 登録成功件数
		assertThat(actual).isNotNull();
		assertThat(actual.getStudentCourseId()).isEqualTo(testCourseId);//作ったIDと登録後のIDが一致しているか
		assertThat(actual.getStatus()).isEqualTo(ApplicationStatusEnum.HON_MOSIKOMI);//登録したダミーの申込状況がEnumにある定数と一致するか
	}


	@Test
	void updateStatus_登録済み申し込み状況が更新できること() {
		ApplicationStatus demoStatus = getApplicationStatus();
		int registedStatus = statusSut.registerStatus(demoStatus);
		String updateStatus = ("受講中");
		ApplicationStatusEnum updateDemoStatus = fromDisplayValue(updateStatus);

		int update = statusSut.updateStatus(updateDemoStatus);
		ApplicationStatus actual = statusSut.searchStudentCourseStatus(demoStatus.getStudentCourseId());

		assertThat(update).isEqualTo(1);
		assertThat(actual.getStatus()).isEqualTo(updateStatus);
		assertThat(actual).isNotNull();
	}

	//ダミーの申込情報の登録処理
	private ApplicationStatus getApplicationStatus() {
		int testCourseId = 256;
		registedDemoStudent();
		registedDemoCourse();
		ApplicationStatus demoStatus = demoDate.createDefaultStatus();
		demoStatus.setStudentCourseId(testCourseId);
		demoStatus.setStatus(ApplicationStatusEnum.HON_MOSIKOMI);
		return demoStatus;
	}

	//ダミーの受講生情報の登録処理（Mock）
	private void registedDemoStudent() {
		Student demoStudent = demoDate.createDefaultStudent();
		demoStudent.setId(741);

		doNothing().when(studentSut).registerStudent(demoStudent);
		studentSut.registerStudent(demoStudent);
	}

	//ダミーのコース情報の登録処理（Mock）
	private void registedDemoCourse() {
		Course demoCourse = demoDate.createDefaultCourse();
		demoCourse.setId(74);

		doNothing().when(courseSut).registerStudentCourses(demoCourse);
		courseSut.registerStudentCourses(demoCourse);
	}

}