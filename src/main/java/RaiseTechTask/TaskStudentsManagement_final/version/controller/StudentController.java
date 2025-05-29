package RaiseTechTask.TaskStudentsManagement_final.version.controller;

import RaiseTechTask.TaskStudentsManagement_final.version.domain.StudentDetail;
import RaiseTechTask.TaskStudentsManagement_final.version.service.StudentService;
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
	@PostMapping("/registerStudent")
	public ResponseEntity<StudentDetail> registerStudent(@RequestBody StudentDetail studentDetail) {
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
	public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {

		service.updateStudent(studentDetail);
		return ResponseEntity.ok("更新処理が成功しました。");
	}
}
