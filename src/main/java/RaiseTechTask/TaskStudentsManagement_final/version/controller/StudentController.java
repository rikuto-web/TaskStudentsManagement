package RaiseTechTask.TaskStudentsManagement_final.version.controller;

import RaiseTechTask.TaskStudentsManagement_final.version.data.Course;
import RaiseTechTask.TaskStudentsManagement_final.version.data.Student;
import RaiseTechTask.TaskStudentsManagement_final.version.domain.StudentDetail;
import RaiseTechTask.TaskStudentsManagement_final.version.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class StudentController {

    //コンストラクタインジェクション
    private StudentService student;
    private StudentService course;
    private StudentConverter converter;

    @Autowired
    public StudentController(StudentService students, StudentService course, StudentConverter converter) {
        this.student = students;
        this.course = course;
        this.converter = converter;
    }

    @GetMapping("/studentsList")
    public List<StudentDetail> getStudent() {
        List<Student> students = student.searchStudentList();
        List<Course> courses = course.searchCoursesList();

        return converter.convertStudentDetails(students, courses);
    }

    @GetMapping("/coursesList")
    public List<Course> getCoursesList() {
        return course.searchCourses();
    }
}
