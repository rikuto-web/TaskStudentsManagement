package raisetechtask.taskstudentsmanagement.finalversion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
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
import raisetechtask.taskstudentsmanagement.finalversion.data.Student;
import raisetechtask.taskstudentsmanagement.finalversion.domain.StudentDetail;
import raisetechtask.taskstudentsmanagement.finalversion.service.StudentService;

import java.util.List;

/**
 * 受講生の検索や登録、更新などを行うREST　APIとして受け付けるcontrollerです。
 */
@RestController
@Validated
@Tag(name = "受講生管理API", description = "受講生情報の検索、登録、更新を管理するAPI")
public class StudentController {

	private final StudentService service;

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
	@ApiResponse(responseCode = "200", description = "成功。受講生の一覧を返します。",
			content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = StudentDetail.class)))
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
	@Operation(summary = "受講生登録", description = "受講生を登録します。")
	@ApiResponse(responseCode = "200", description = "登録成功。登録された受講生詳細情報を返します。",
			content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = StudentDetail.class)))
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
	@ApiResponse(responseCode = "200", description = "登録成功。登録された受講生詳細情報を返します。",
			content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = StudentDetail.class)))
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
	@Operation(summary = "受講生情報更新", description = "既存の受講生情報を更新します。キャンセルフラグの更新（論理削除）も含まれます。")
	@ApiResponse(responseCode = "200", description = "更新処理が成功しました。",
			content = @Content(mediaType = "text/plain", schema = @Schema(type = "string", example = "更新処理が成功しました。")))
	@PutMapping("/updateStudent")
	public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail) {

		service.updateStudent(studentDetail);
		return ResponseEntity.ok("更新処理が成功しました。");
	}

	/**
	 * 性別で受講生を検索を行います。
	 * 男性・女性・その他のみ受付を行います。
	 */
	@Operation(summary = "性別による受講生検索", description = "指定された性別の受講生の一覧を取得します。")
	@ApiResponse(responseCode = "200", description = "成功。指定された性別の受講生リストを返します。",
			content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = Student.class, type = "array")))
	@GetMapping("/students/gender/{gender}")
	public List<Student> getStudentsByGender(
			@PathVariable
			@Pattern(regexp = "男性|女性|その他", message = "genderは「男性」「女性」「その他」のいずれかで指定してください") String gender) {
		return service.searchStudentsByGender(gender);
	}
}
