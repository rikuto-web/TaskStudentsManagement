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

@MybatisTest // テスト終了時自動でロールバックする (これが重要)
class StudentsRepositoryTest {

	@Autowired
	private StudentsRepository sut;

	// @BeforeEach メソッドは削除します。
	// data.sql がテスト開始時に自動で読み込まれることを前提とします。

	@Test
	void 受講生の全件検索が行えること() {
		// data.sql に5件の学生データが存在することを前提とします。
		List<Student> actual = sut.searchStudentsList();
		assertThat(actual.size()).isEqualTo(5);
		// 必要であれば、個々のデータの内容もアサートで検証すると良いでしょう
		// 例: assertThat(actual.get(0).getFullName()).isEqualTo("初期学生1");
	}

	@Test
	void IDに紐づいた受講生情報の検索が行えること() {
		// data.sql に存在するID（例: 1）を検索対象とします。
		// もし data.sql のIDが連番であれば、1番目の学生データを想定
		int existingStudentId = 1; // data.sql に存在するIDに合わせる

		Student actual = sut.searchStudent(existingStudentId);

		assertThat(actual).isNotNull(); // nullでないことを確認
		assertThat(actual.getId()).isEqualTo(existingStudentId);
		assertThat(actual.getFullName()).isEqualTo("山田 太郎"); // data.sql の内容に合わせる
		// 他のフィールドも data.sql の内容と一致するか検証
		assertThat(actual.getEmailAddress()).isEqualTo("taro.yamada@example.com");
	}

	@Test
	void IDに紐づいた受講生の情報が見つからない場合() {
		// data.sql に確実に存在しないIDを検索します。
		// data.sql のIDが1から始まる場合、0や大きな数など
		int nonExistentStudentId = 9999999;
		Student actual = sut.searchStudent(nonExistentStudentId);

		assertNull(actual, "存在しないIDで検索した場合、nullが返るべきです");
	}


	@Test
	void 受講生の登録が行えること() {
		// 新規登録用のStudentオブジェクトを作成します。
		// IDはMyBatisの自動採番に任せるため、BuilderでIDは設定しません。
		// data.sql のデータと重複しないよう、ユニークな情報を与えます。
		Student newStudent = Student.builder()
				.fullName("新規登録学生")
				.furigana("シンキトウロクガクセイ")
				.nickname("Newbie")
				.emailAddress("new.student@example.com")
				.address("東京都新宿区")
				.age(22)
				.gender("女性")
				.remark("新しく登録された学生")
				.isDeleted(false)
				.build();

		sut.registerStudent(newStudent); // 登録実行

		// data.sql の5件 + 新規登録1件 = 6件になることを確認
		List<Student> actual = sut.searchStudentsList();
		assertThat(actual.size()).isEqualTo(6);

		// 登録された学生が正しく検索できるかも確認
		Student registeredStudent = sut.searchStudent(newStudent.getId()); // registerStudentでIDがnewStudentにセットされているはず
		assertThat(registeredStudent).isNotNull();
		assertThat(registeredStudent.getFullName()).isEqualTo("新規登録学生");
	}

	@Test
	void 必須項目がnullの場合に登録処理が実行できないこと() {
		// fullName を null に設定したStudentオブジェクトをBuilderで作成
		// setterは使用しません。
		Student studentWithNullName = Student.builder()
				.fullName(null) // fullName を null に設定
				.furigana("フリガナナシ")
				.emailAddress("nullname@example.com")
				.address("不明")
				.age(30)
				.gender("不明")
				.remark("テスト用")
				.isDeleted(false)
				.build();

		// DataIntegrityViolationException がスローされることを検証
		assertThrows(DataIntegrityViolationException.class, () -> sut.registerStudent(studentWithNullName),
				"必須項目がnullの場合にDataIntegrityViolationExceptionがスローされるべきです");
	}

	@Test
	void 受講生情報の更新が行えること() {
		// data.sql に存在する学生（例: ID=1）を更新対象とします。
		int studentIdToUpdate = 1; // data.sql に存在するIDに合わせる

		// まず、現在のデータを取得します。
		Student initialStudent = sut.searchStudent(studentIdToUpdate);
		assertThat(initialStudent).isNotNull();
		assertThat(initialStudent.isDeleted()).isFalse(); // 更新前の状態を確認

		// 更新用オブジェクトをBuilderパターンで作成します。
		// IDは既存のものを使い、変更したいフィールド（isDeleted）だけ新しい値を設定し、
		// 他のフィールドは元の値をコピーします（getterを使用）。
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
				.isDeleted(true) // isDeleted を true に更新
				.build();

		sut.updateStudent(studentToUpdate); // 更新を実行

		// 更新後の学生情報を再取得し、変更が適用されたことを確認
		Student updatedStudent = sut.searchStudent(studentIdToUpdate);
		assertThat(updatedStudent).isNotNull();
		assertThat(updatedStudent.isDeleted()).isTrue(); // isDeleted が true になっていることを確認
		assertThat(updatedStudent.getFullName()).isEqualTo(initialStudent.getFullName()); // 他のフィールドが変わっていないことも確認
	}

	// ヘルパーメソッド `createDefaultStudent()` は、この新しい方針ではほとんど使われません。
	// 各テストで直接Builderパターンを使ってデータを構築します。
	// もし特定の共通的な「比較用」オブジェクトが必要な場合は残しても良いですが、
	// ユニークなデータを登録する目的ではなくなります。
    /*
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
    */
}