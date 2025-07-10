package raisetechtask.taskstudentsmanagement.finalversion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import raisetechtask.taskstudentsmanagement.finalversion.data.ApplicationStatus;
import raisetechtask.taskstudentsmanagement.finalversion.data.Student;
import raisetechtask.taskstudentsmanagement.finalversion.data.StudentCourse;
import raisetechtask.taskstudentsmanagement.finalversion.domain.CourseApplicationDetail;
import raisetechtask.taskstudentsmanagement.finalversion.domain.StudentDetail;
import raisetechtask.taskstudentsmanagement.finalversion.enums.ApplicationStatusEnum;
import raisetechtask.taskstudentsmanagement.finalversion.service.StudentService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private StudentService service;

	@Test
	void 受講生詳細の一覧検索が実行できて空のリストが帰ってくること() throws Exception {
		mockMvc.perform(get("/studentList"))
				.andExpect(status().isOk());

		Mockito.verify(service, Mockito.times(1)).searchStudentList();
	}

	@Test
	void IDに紐づく任意の受講生情報の取得操作ができていること() throws Exception {
		int testId = 0;
		mockMvc.perform(get("/student/{id}", testId))
				.andExpect(status().isOk());

		Mockito.verify(service, Mockito.times(1)).searchStudent(testId);
	}

	@Test
	void 受講生詳細の登録処理の呼び出しができていること() throws Exception {
		Student requestStudent = createStudent(7, "田中太郎", "タナカタロウ", "ニックネーム",
				"register@example.com", "東京", 25, "男性", "備考", false);

		StudentCourse studentCourse = createStudentCourse(10, 7, "Java入門", LocalDate.of(2025, 4, 1), LocalDate.of(2025, 7, 1));
		ApplicationStatus applicationStatus = createApplicationStatus(10, 10, ApplicationStatusEnum.JUKOCHU);
		CourseApplicationDetail courseDetail = createCourseApplicationDetail(studentCourse, applicationStatus);
		List<CourseApplicationDetail> courseList = List.of(courseDetail);

		StudentDetail requestStudentDetail = createStudentDetail(requestStudent, courseList);

		Student registeredStudent = createStudent(7, "田中太郎", "タナカタロウ", "ニックネーム",
				"register@example.com", "東京", 25, "男性", "備考", false);
		StudentDetail expectedRegisteredStudentDetail = createStudentDetail(registeredStudent, courseList);

		when(service.registerStudent(any(StudentDetail.class))).thenReturn(expectedRegisteredStudentDetail);

		mockMvc.perform(MockMvcRequestBuilders.post("/registerStudent")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(requestStudentDetail)))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.student.id").value(expectedRegisteredStudentDetail.getStudent().getId()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.student.fullName").value(expectedRegisteredStudentDetail.getStudent().getFullName()));

		Mockito.verify(service, Mockito.times(1)).registerStudent(requestStudentDetail);
	}

	@Test
	void 受講生詳細の更新の呼び出しができていること() throws Exception {
		Student requestStudent = createStudent(7, "田中太郎", "タナカタロウ", "ニックネーム",
				"register@example.com", "東京", 25, "男性", "備考", false);

		List<CourseApplicationDetail> courseDetails = new ArrayList<>();

		StudentDetail requestStudentDetail = createStudentDetail(requestStudent, courseDetails);

		Student updateStudent = createStudent(7, "田中次郎", "タナカジロウ", "ニックネーム",
				"register@example.com", "東京", 25, "男性", "備考", false);
		List<CourseApplicationDetail> updatedCourseDetails = new ArrayList<>();

		doNothing().when(service).updateStudent(any(StudentDetail.class));

		mockMvc.perform(MockMvcRequestBuilders.put("/updateStudent")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(requestStudentDetail)))
				.andExpect(status().isOk())
				.andExpect(content().string("更新処理が成功しました。"));

		Mockito.verify(service, Mockito.times(1)).updateStudent(requestStudentDetail);
	}

	@Test
	void 性別が男性の場合は200と空リスト返却() throws Exception {
		when(service.searchStudentsByGender("男性")).thenReturn(Collections.emptyList());

		mockMvc.perform(get("/students/gender/男性"))
				.andExpect(status().isOk())
				.andExpect(content().json("[]"));
	}

	@Test
	void 性別が女性の場合は200と空リスト返却() throws Exception {
		when(service.searchStudentsByGender("女性")).thenReturn(Collections.emptyList());

		mockMvc.perform(get("/students/gender/女性"))
				.andExpect(status().isOk())
				.andExpect(content().json("[]"));
	}

	@Test
	void 性別がその他の場合は200と空リスト返却() throws Exception {
		when(service.searchStudentsByGender("その他")).thenReturn(Collections.emptyList());

		mockMvc.perform(get("/students/gender/その他"))
				.andExpect(status().isOk())
				.andExpect(content().json("[]"));
	}

	@Test
	void 性別が不正な値の場合は400になること() throws Exception {
		mockMvc.perform(get("/students/gender/不明"))
				.andExpect(status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().string(org.hamcrest.Matchers.containsString(
						"genderは「男性」「女性」「その他」のいずれかで指定してください"
				)));
	}

	//region Helper Methods
	private Student createStudent(int id, String fullName, String furigana, String nickname, String emailAddress, String address, int age, String gender, String remark, boolean isDeleted) {
		return Student.builder()
				.id(id)
				.fullName(fullName)
				.furigana(furigana)
				.nickname(nickname)
				.emailAddress(emailAddress)
				.address(address)
				.age(age)
				.gender(gender)
				.remark(remark)
				.isDeleted(isDeleted)
				.build();
	}

	private StudentCourse createStudentCourse(int id, int studentId, String courseName, LocalDate courseStartDay, LocalDate courseCompletionDay) {
		return StudentCourse.builder()
				.id(id)
				.studentId(studentId)
				.courseName(courseName)
				.courseStartDay(LocalDate.now())
				.courseCompletionDay(LocalDate.now().plusMonths(3))
				.build();
	}

	private ApplicationStatus createApplicationStatus(int id, int studentCourseId, ApplicationStatusEnum status) {
		return ApplicationStatus.builder()
				.id(id)
				.studentCourseId(studentCourseId)
				.status(status)
				.build();
	}

	private CourseApplicationDetail createCourseApplicationDetail(StudentCourse studentCourse, ApplicationStatus applicationStatus) {
		return new CourseApplicationDetail(studentCourse, applicationStatus);
	}

	private StudentDetail createStudentDetail(Student student, List<CourseApplicationDetail> courseApplicationDetails) {
		return new StudentDetail(student, courseApplicationDetails);
	}
}