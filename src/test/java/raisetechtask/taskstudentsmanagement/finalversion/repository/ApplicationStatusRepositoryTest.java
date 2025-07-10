/*
package raisetechtask.taskstudentsmanagement.finalversion.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import raisetechtask.taskstudentsmanagement.finalversion.data.ApplicationStatus;
import raisetechtask.taskstudentsmanagement.finalversion.data.DemoData;
import raisetechtask.taskstudentsmanagement.finalversion.data.Student;
import raisetechtask.taskstudentsmanagement.finalversion.data.StudentCourse;
import raisetechtask.taskstudentsmanagement.finalversion.enums.ApplicationStatusEnum;

import static org.assertj.core.api.Assertions.assertThat;
import static raisetechtask.taskstudentsmanagement.finalversion.enums.ApplicationStatusEnum.KARI_MOSIKOMI;
import static raisetechtask.taskstudentsmanagement.finalversion.enums.ApplicationStatusEnum.inputDisplayValue;

@MybatisTest
@ComponentScan(basePackages = "raisetechtask.taskstudentsmanagement.finalversion")
class ApplicationStatusRepositoryTest {

	@Autowired
	public DemoData demoData;

	@Autowired
	private StudentsRepository studentSut;

	@Autowired
	private CoursesRepository courseSut;

	@Autowired
	private ApplicationStatusRepository applicationStatusRepository;

	@BeforeEach
	void setup() {
		ApplicationStatus status = new ApplicationStatus();
		status.setStudentCourseId(123);
		status.setStatus(KARI_MOSIKOMI);
		applicationStatusRepository.registerStatus(status.getStudentCourseId(), status.getStatus().name());
	}

	@Test
	void 申し込み状況の全件検索ができること() {
		var result = applicationStatusRepository.searchStudentCourseStatusList();
		assertThat(result).hasSize(6);
	}

	@Test
	void コースIDに紐づいた申し込み状況の検索_正常系() {
		int testCourseId = 101;
		ApplicationStatusEnum expectedStatus = ApplicationStatusEnum.KARI_MOSIKOMI;
		// Act:
		ApplicationStatus actual = applicationStatusRepository.searchStudentCourseStatus(testCourseId);
		// Assert:
		assertThat(actual).isNotNull();
		assertThat(actual.getStudentCourseId()).isEqualTo(testCourseId);
		assertThat(actual.getStatus()).isEqualTo(expectedStatus);
	}

	@Test
	void 存在しないコースIDで検索された場合Nullを返すこと() {
		int testCourseId = 987654321; // 確実に存在しないID
		// Act:
		ApplicationStatus actual = applicationStatusRepository.searchStudentCourseStatus(testCourseId);
		// Assert:
		assertThat(actual).isNull();
	}

	@Test
	void 受講生IDとコースIDに紐づいた申込状況を登録することができること() {
		int testCourseId = 74; // 新規登録用のID
		registedDemoStudent();// 受講生の登録操作のみスルー (Mock)
		registedDemoCourse();// 受講生コースの登録操作のみスルー (Mock)
		ApplicationStatus demoStatus = demoData.createDefaultStatus();// ダミーの申込状況情報
		demoStatus.setStudentCourseId(testCourseId);// 紐づいたコースIDを設定
		demoStatus.setStatus(ApplicationStatusEnum.HON_MOSIKOMI);// 登録する申込状況の設定

		// Act
		int registedStatus = applicationStatusRepository.registerStatus(
				demoStatus.getStudentCourseId(),
				demoStatus.getStatus().name()  // Enum → String に変換
		);

		ApplicationStatus actual = applicationStatusRepository.searchStudentCourseStatus(testCourseId);

		// Assert:
		assertThat(registedStatus).isEqualTo(1);
		assertThat(actual).isNotNull();
		assertThat(actual.getStudentCourseId()).isEqualTo(testCourseId);
		assertThat(actual.getStatus()).isEqualTo(ApplicationStatusEnum.HON_MOSIKOMI);
	}


	@Test
	void updateStatus_登録済み申し込み状況が更新できること() {
		ApplicationStatus demoStatus = getApplicationStatus();

		String updateStatus = ("受講中");
		ApplicationStatusEnum updateDemoStatus = inputDisplayValue(updateStatus);

		// Act:
		int update = applicationStatusRepository.updateStatus(demoStatus.getStudentCourseId(), updateDemoStatus);
		ApplicationStatus actual = applicationStatusRepository.searchStudentCourseStatus(demoStatus.getStudentCourseId());

		// Assert:
		assertThat(update).isEqualTo(1);
		assertThat(actual.getStatus()).isEqualTo(updateDemoStatus);
		assertThat(actual).isNotNull();
	}


	//ダミーの申込情報の登録処理
	private ApplicationStatus getApplicationStatus() {
		int testCourseId = 256;
		registedDemoStudent();
		registedDemoCourse();
		ApplicationStatus demoStatus = demoData.createDefaultStatus();
		demoStatus.setStudentCourseId(testCourseId);
		demoStatus.setStatus(ApplicationStatusEnum.HON_MOSIKOMI);

		applicationStatusRepository.registerStatus(
				demoStatus.getStudentCourseId(),
				demoStatus.getStatus().name()  // Enum → String に変換して渡す
		);
		return demoStatus;
	}


	//ダミーの受講生情報の登録処理（Mock）
	private void registedDemoStudent() {
		Student demoStudent = demoData.createDefaultStudent();
		studentSut.registerStudent(demoStudent);
	}

	//ダミーのコース情報の登録処理（Mock）
	private void registedDemoCourse() {
		StudentCourse demoStudentCourse = demoData.createDefaultCourse();
		courseSut.registerStudentCourses(demoStudentCourse);
	}
}
 */