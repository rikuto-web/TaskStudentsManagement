package RaiseTechTask.TaskStudentsManagement_final.version.controller;

import RaiseTechTask.TaskStudentsManagement_final.version.entity.Course;
import RaiseTechTask.TaskStudentsManagement_final.version.entity.Student;
import RaiseTechTask.TaskStudentsManagement_final.version.service.ManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class Controller {

    //コンストラクタインジェクション
    private ManagementService student;
    private ManagementService course;

    @Autowired
    public Controller(ManagementService students, ManagementService course) {
        this.student = students;
        this.course = course;
    }

    @GetMapping("/studentsList")
    public List<Student> getStudent() {
        return student.getStudentList();
    }

    @GetMapping("/coursesList")
    public List<Course> getCoursesList() {
        return course.getCoursesList();
    }
}
