package raisetechtask.taskstudentsmanagement.finalversion.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetechtask.taskstudentsmanagement.finalversion.domain.StudentDetail;
import raisetechtask.taskstudentsmanagement.finalversion.exception.TestException;
import raisetechtask.taskstudentsmanagement.finalversion.service.StudentService;

import java.util.List;

/**
 * 受講生の検索や登録、更新などを行うREST　APIとして受け付けるcontrollerです。
 */
@RestController
@Validated
public class StudentController {

	private StudentService service;

	@Autowired
	public StudentController(StudentService service) {
		this.service = service;
	}

	/**
	 * 受講生詳細の一覧検索です
	 * 全件検索を行うので、条件指定は行わないものになります。
	 *
	 * @return 受講生一覧（全件）
	 */
	@Operation(summary = "一覧検索", description = "受講生の一覧を検索します。")
	@GetMapping("/studentList")
	public List<StudentDetail> getStudentList() {
		return service.searchStudentList();
	}

	/**
	 * 受講生詳細検索です。
	 * IDに紐づく任意の受講生の情報を取得します
	 *
	 * @param id 　受講生ID
	 * @return 受講生詳細
	 */
	@GetMapping("/student/{id}")
	public StudentDetail getStudent(@PathVariable @PositiveOrZero @Max(999) int id) {
		return service.searchStudent(id);
	}

	/**
	 * 受講生詳細の登録を行います。
	 *
	 * @param studentDetail 受講生情報とコース情報
	 * @return 実行結果
	 */
	@Operation(summary = "受講生登録", description = "受講生を登録します。")
	@PostMapping("/registerStudent")
	public ResponseEntity<StudentDetail> registerStudent(@RequestBody @Valid StudentDetail studentDetail) {
		StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
		return ResponseEntity.ok(responseStudentDetail);
	}

	/**
	 * 受講生詳細の更新を行います
	 * キャンセルフラグの更新もここで行います（論理削除）
	 *
	 * @param studentDetail 受講生詳細
	 * @return 実行結果
	 */
	@PutMapping("/updateStudent")
	public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail) {

		service.updateStudent(studentDetail);
		return ResponseEntity.ok("更新処理が成功しました。");
	}


	//例外メソッドとしてとりあえず保存
	@GetMapping("/exStudentList")
	public List<StudentDetail> ex() throws TestException {
		throw new TestException("エラーが発生しました");
	}
}
