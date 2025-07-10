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

@MybatisTest
class StudentsRepositoryTest {

	@Autowired
	private StudentsRepository sut;

	@Test
	void 全件検索で期待通りの件数が取得できること() {
		List<Student> actual = sut.searchStudentsList();
		assertThat(actual).isNotNull();
		assertThat(actual.size()).isEqualTo(5);
	}

	@Test
	void IDに紐づいた受講生情報の検索が行えること() {
		int existingStudentId = 1;

		Student actual = sut.searchStudent(existingStudentId);

		assertThat(actual).isNotNull();
		assertThat(actual.getId()).isEqualTo(existingStudentId);
	}

	@Test
	void IDに紐づいた受講生の情報が見つからない場合() {
		int nonExistentStudentId = 9999999;

		Student actual = sut.searchStudent(nonExistentStudentId);

		assertNull(actual, "存在しないIDで検索した場合、nullが返るべきです");
	}


	@Test
	void 受講生の登録が行えること() {
		Student newStudent = Student.builder()
				.fullName("新規登録")
				.furigana("シンキトウロク")
				.nickname("Newbie")
				.emailAddress("new.student@example.com")
				.address("東京都新宿区")
				.age(22)
				.gender("女性")
				.remark("新しく登録された学生")
				.isDeleted(false)
				.build();

		sut.registerStudent(newStudent);

		List<Student> actual = sut.searchStudentsList();
		assertThat(actual.size()).isEqualTo(6);

		Student registeredStudent = sut.searchStudent(newStudent.getId());
		assertThat(registeredStudent).isNotNull();
		assertThat(registeredStudent.getFullName()).isEqualTo("新規登録");
	}

	@Test
	void 必須項目がnullの場合に登録処理が実行できないこと() {
		Student studentWithNullName = Student.builder()
				.fullName(null)
				.furigana("フリガナナシ")
				.emailAddress("nullname@example.com")
				.address("不明")
				.age(30)
				.gender("不明")
				.remark("テスト用")
				.isDeleted(false)
				.build();

		assertThrows(DataIntegrityViolationException.class, () -> sut.registerStudent(studentWithNullName),
				"必須項目がnullの場合にDataIntegrityViolationExceptionがスローされるべきです");
	}

	@Test
	void 受講生情報の更新が行えること() {
		//更新の事前確認
		int studentIdToUpdate = 1;

		Student initialStudent = sut.searchStudent(studentIdToUpdate);

		assertThat(initialStudent).isNotNull();
		assertThat(initialStudent.isDeleted()).isFalse();

		//更新内容の準備
		Student studentToUpdate = Student.builder()
				.id(studentIdToUpdate)
				.fullName(initialStudent.getFullName())
				.furigana(initialStudent.getFurigana())
				.nickname(initialStudent.getNickname())
				.emailAddress(initialStudent.getEmailAddress())
				.address(initialStudent.getAddress())
				.age(initialStudent.getAge())
				.gender(initialStudent.getGender())
				.remark(initialStudent.getRemark())
				.isDeleted(true)
				.build();

		sut.updateStudent(studentToUpdate);

		Student updatedStudent = sut.searchStudent(studentIdToUpdate);
		assertThat(updatedStudent).isNotNull();
		assertThat(updatedStudent.isDeleted()).isTrue();
		assertThat(updatedStudent.getFullName()).isEqualTo(initialStudent.getFullName());
	}
}