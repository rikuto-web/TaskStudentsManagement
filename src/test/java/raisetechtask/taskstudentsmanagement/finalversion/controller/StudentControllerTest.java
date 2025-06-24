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
import raisetechtask.taskstudentsmanagement.finalversion.data.ApplicationStatusEnum;
import raisetechtask.taskstudentsmanagement.finalversion.data.Course;
import raisetechtask.taskstudentsmanagement.finalversion.data.Student;
import raisetechtask.taskstudentsmanagement.finalversion.domain.StudentDetail;
import raisetechtask.taskstudentsmanagement.finalversion.service.StudentService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

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
		mockMvc.perform(MockMvcRequestBuilders.get("/studentList"))
				.andExpect(MockMvcResultMatchers.status().isOk());

		Mockito.verify(service, Mockito.times(1)).searchStudentList();
	}

	@Test
	void IDに紐づく任意の受講生情報の取得操作ができていること() throws Exception {
		int testId = 0;
		mockMvc.perform(MockMvcRequestBuilders.get("/student/{id}", testId))
				.andExpect(MockMvcResultMatchers.status().isOk());

		Mockito.verify(service, Mockito.times(1)).searchStudent(testId);
	}

	@Test
	void 受講生詳細の登録処理の呼び出しができていること() throws Exception {
		Student requestStudent = new Student(7, "田中太郎", "タナカタロウ", "ニックネーム", "register@example.com", "東京", 25, "男性", "備考", false);
		List<Course> requestCourses = new ArrayList<>();
		ApplicationStatus repuestStatus = new ApplicationStatus(10, 10, ApplicationStatusEnum.JUKOCHU);
		StudentDetail requestStudentDetail = new StudentDetail(requestStudent, requestCourses, repuestStatus);

		Student registeredStudent = new Student(7, "田中太郎", "タナカタロウ", "ニックネーム", "register@example.com", "東京", 25, "男性", "備考", false);
		List<Course> registeredCourses = new ArrayList<>();
		StudentDetail expectedRegisteredStudentDetail = new StudentDetail(registeredStudent, registeredCourses, repuestStatus);

		when(service.registerStudent(any(StudentDetail.class))).thenReturn(expectedRegisteredStudentDetail);

		mockMvc.perform(MockMvcRequestBuilders.post("/registerStudent")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(requestStudentDetail)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.student.id").value(expectedRegisteredStudentDetail.getStudent().getId())) // レスポンスボディのJSONを検証
				.andExpect(MockMvcResultMatchers.jsonPath("$.student.fullName").value(expectedRegisteredStudentDetail.getStudent().getFullName()));
		Mockito.verify(service, Mockito.times(1)).registerStudent(requestStudentDetail);
	}

	@Test
	void 受講生詳細の更新の呼び出しができていること() throws Exception {
		Student requestStudent = new Student(7, "田中太郎", "タナカタロウ", "ニックネーム", "register@example.com", "東京", 25, "男性", "備考", false);
		List<Course> requestCourses = new ArrayList<>();
		ApplicationStatus repuestStatus = new ApplicationStatus(10, 10, ApplicationStatusEnum.JUKOCHU);

		StudentDetail requestStudentDetail = new StudentDetail(requestStudent, requestCourses, repuestStatus);

		Student updateStudent = new Student(7, "田中次郎", "タナカジロウ", "ニックネーム", "register@example.com", "東京", 25, "男性", "備考", false);
		List<Course> registeredCourses = new ArrayList<>();
		StudentDetail expectedRegisteredStudentDetail = new StudentDetail(updateStudent, registeredCourses, repuestStatus);

		doNothing().when(service).updateStudent(any(StudentDetail.class));
		mockMvc.perform(MockMvcRequestBuilders.put("/updateStudent")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(requestStudentDetail)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("更新処理が成功しました。"));

		Mockito.verify(service, Mockito.times(1)).updateStudent(requestStudentDetail);
	}
}