package raisetechtask.taskstudentsmanagement.finalversion.repository;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import raisetechtask.taskstudentsmanagement.finalversion.data.Student;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
	void IDに紐づいた受講生情報の検索が行えること() {
		Student studentToRegister = createDefaultStudent();
		sut.registerStudent(studentToRegister);

		Student actual = sut.searchStudent(studentToRegister.getId());

		assertThat(actual.getId()).isEqualTo(studentToRegister.getId());
	}

	@Test
	void IDに紐づいた受講生の情報が見つからない場合() {
		int nonExistentStudentId = 9999999; // 確実に存在しないID
		Student actual = sut.searchStudent(nonExistentStudentId);

		assertNull(actual, "存在しないIDで検索した場合、nullが返るべきです");
	}


	@Test
	void 受講生の登録が行えること() {
		sut.registerStudent(createDefaultStudent());

		List<Student> actual = sut.searchStudentsList();
		assertThat(actual.size()).isEqualTo(6);
	}

	@Test
	void 必須項目がnullの場合に登録処理が実行できないこと() {
		Student studentWithNullName = Student.builder()
				.fullName(null)
				.build();

		assertThrows(DataIntegrityViolationException.class, () -> sut.registerStudent(studentWithNullName),
				"必須項目がnull");
	}

	@Test
	void 受講生情報の更新が行えること() {
		Student initialStudent = createDefaultStudent();
		sut.registerStudent(initialStudent);
		int studentIdToUpdate = initialStudent.getId();
		Student demoStudent = sut.searchStudent(studentIdToUpdate);
		demoStudent.setDeleted(true);

		sut.updateStudent(demoStudent);
		Student updatedStudent = sut.searchStudent(studentIdToUpdate);


		assertThat(updatedStudent.isDeleted()).isTrue();
	}

	//ヘルパーメソッド
	private Student createDefaultStudent() {
		return Student.builder()
				.fullName("デフォルト太郎")
				.furigana("デフォルトタロウ")
				.nickname("デモニック")
				.emailAddress("default.test@example.com")
				.address("東京都千代田区")
				.age(25)
				.gender("男性")
				.remark("デフォルトデータ")
				.isDeleted(false)
				.build();
	}
}