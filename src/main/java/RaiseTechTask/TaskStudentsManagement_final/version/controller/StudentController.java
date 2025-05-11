package RaiseTechTask.TaskStudentsManagement_final.version.controller;

import RaiseTechTask.TaskStudentsManagement_final.version.data.Course;
import RaiseTechTask.TaskStudentsManagement_final.version.data.Student;
import RaiseTechTask.TaskStudentsManagement_final.version.domain.StudentDetail;
import RaiseTechTask.TaskStudentsManagement_final.version.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;


@Controller
public class StudentController {

    //コンストラクタインジェクション
    private StudentService service;
    private StudentConverter converter;

    @Autowired
    public StudentController(StudentService service, StudentConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    //生徒情報一覧
    @GetMapping("/studentList")
    public String getStudentList(Model model) {
        List<Student> students = service.searchStudentList();
        List<Course> courses = service.searchCoursesList();

        model.addAttribute("studentList", converter.convertStudentDetails(students, courses));
        return "studentList";
        //HTMLファイルの名前でいいのか？
    }

    //コース情報一覧
    @GetMapping("/coursesList")
    public String getCoursesList(Model model) {
        List<Course> courses = service.searchCoursesList();
        model.addAttribute("courseList", courses);
        return "courseList";
    }

    //新規生徒情報登録画面
    @GetMapping("/newStudent")
    public String newStudent(Model model) {
        model.addAttribute("studentDetail", new StudentDetail());
        return "registerStudent";
    }

    @PostMapping("/registerStudent")
    public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
        if (result.hasErrors()) {
            return "registerStudent";
        }
        service.postStudent(studentDetail.getStudent());
        return "redirect:/studentList";
    }

    //新規コース情報登録
    @GetMapping("/newCourse")
    public String newCourse(Model model) {
        List<Student> students = service.searchStudentList();
        model.addAttribute("students", students);
        model.addAttribute("course", new Course());
        return "registerCourse";
    }

    @PostMapping("/registerCourse")
    public String registerCourse(@ModelAttribute Course course, BindingResult result) {
        if (result.hasErrors()) {
            return "registerCourse";
        }
        //終了予定日を自動入力してDBへ保存（４か月指定）
        LocalDate start = course.getCourseStartDay();
        course.setCourseCompletionDay(start.plusMonths(4));

        service.postCourse(course);
        return "redirect:/coursesList";
    }
}
