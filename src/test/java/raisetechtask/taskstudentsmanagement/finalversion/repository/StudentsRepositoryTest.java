package raisetechtask.taskstudentsmanagement.finalversion.repository;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetechtask.taskstudentsmanagement.finalversion.data.Student;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest//テスト終了時自動でロールバックする
class StudentsRepositoryTest {

	@Autowired
	private StudentsRepository sut;

	@Test
	void 受講生の全件検索が行えること() {
		List<Student> actual = sut.searchStudentsList();
		assertThat(actual.size()).isEqualTo(5);
	}


	@Test
	void 図構成の登録が行えること() {
		Student student = Student.builder()
				.fullName("田中太郎")
				.furigana("タナカタロウ")
				.nickname("たなか")
				.emailAddress("rftgyhu@huji.com")
				.address("東京")
				.age(35)
				.gender("男性")
				.remark("")
				.deleted(false)
				.build();

		sut.registerStudent(student);

		List<Student> actual = sut.searchStudentsList();
		assertThat(actual.size()).isEqualTo(5);

	}
}