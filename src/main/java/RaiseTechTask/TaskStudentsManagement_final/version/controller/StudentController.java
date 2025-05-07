package RaiseTechTask.TaskStudentsManagement_final.version.controller;

import RaiseTechTask.TaskStudentsManagement_final.version.data.Course;
import RaiseTechTask.TaskStudentsManagement_final.version.data.Student;
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

    @Autowired
    public StudentController(StudentService students, StudentService course) {
        this.student = students;
        this.course = course;
    }
/*
    @GetMapping("/studentsList")
    public List<Student> getStudent() {
        //リクエストの加工処理や入力チェックなどを記述していく
        return student.searchStudentList();
    }

    @GetMapping("/coursesList")
    public List<Course> getCoursesList() {
        return course.searchCoursesList();
    }
*/

    @GetMapping("/studentsList")
    public List<Student> getStudent() {
        //リクエストの加工処理や入力チェックなどを記述していく
        return student.searchStudent();
    }

    @GetMapping("/coursesList")
    public List<Course> getCoursesList() {
        return course.searchCourses();
    }

}
