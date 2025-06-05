package RaiseTechTask.TaskStudentsManagement_final.version.service;

import RaiseTechTask.TaskStudentsManagement_final.version.controller.StudentConverter;
import RaiseTechTask.TaskStudentsManagement_final.version.domain.StudentDetail;
import RaiseTechTask.TaskStudentsManagement_final.version.repository.CoursesRepository;
import RaiseTechTask.TaskStudentsManagement_final.version.repository.StudentsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

	@Mock
	private StudentsRepository repository;

	@Mock
	private CoursesRepository coursesRepository;

	@Mock
	private StudentConverter converter;


	@Test
	void testSearchStudentList() {
		StudentService sut = new StudentService(repository, coursesRepository, converter);
		List<StudentDetail> expectes = new ArrayList<>();

		List<StudentDetail> actual = sut.searchStudentList();

		Assertions.assertEquals(expectes, actual);
	}


}