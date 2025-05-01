package RaiseTechTask.TaskStudentsManagement_final.version.controller;

import RaiseTechTask.TaskStudentsManagement_final.version.entity.Courses;
import RaiseTechTask.TaskStudentsManagement_final.version.entity.Students;
import RaiseTechTask.TaskStudentsManagement_final.version.service.ManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class Controller {

    //コンストラクタインジェクション
    private ManagementService students;

    @Autowired
    public Controller(ManagementService students) {
        this.students = students;
    }

    //フィールドインジェクション
    @Autowired
    private ManagementService coursesList;


    @GetMapping("/studentsList")
    public List<Students> getStudents() {
        return students.getStudentList();
    }

    @GetMapping("/coursesList")
    public List<Courses> getCoursesList() {
        return coursesList.getCoursesList();
    }
}
