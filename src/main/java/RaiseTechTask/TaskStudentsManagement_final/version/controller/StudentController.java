package RaiseTechTask.TaskStudentsManagement_final.version.controller;

import RaiseTechTask.TaskStudentsManagement_final.version.data.Course;
import RaiseTechTask.TaskStudentsManagement_final.version.data.Student;
import RaiseTechTask.TaskStudentsManagement_final.version.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
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

    @GetMapping("/studentList")
    public String getStudentList(Model model) {
        List<Student> students = student.searchStudentList();
        List<Course> courses = course.searchCoursesList();

        model.addAttribute("studentList", converter.convertStudentDetails(students, courses));
        return "studentList";
        //HTMLファイルの名前でいいのか？
    }

    @GetMapping("/coursesList")
    public String getCoursesList(Model model2) {
        List<Course> courses = course.searchCoursesList();
        model2.addAttribute("courseList", courses);
        return "courseList";
    }
}
