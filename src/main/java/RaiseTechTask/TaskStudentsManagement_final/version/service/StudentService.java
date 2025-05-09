package RaiseTechTask.TaskStudentsManagement_final.version.service;

import RaiseTechTask.TaskStudentsManagement_final.version.data.Course;
import RaiseTechTask.TaskStudentsManagement_final.version.data.Student;
import RaiseTechTask.TaskStudentsManagement_final.version.repository.CoureseRepository;
import RaiseTechTask.TaskStudentsManagement_final.version.repository.StudentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class StudentService {

    //コンストラクタインジェクション
    private StudentsRepository sr;
    private CoureseRepository cr;

    @Autowired
    public StudentService(StudentsRepository sr, CoureseRepository cr) {
        this.sr = sr;
        this.cr = cr;
    }

    //全件表示・生徒情報
    public List<Student> searchStudentList() {
        return sr.slectAllStudent();
    }

    //全件表示・コース情報
    public List<Course> searchCoursesList() {
        return cr.slectAllCourse();
    }













    public List<Student> searchStudent() {
        sr.slectAllStudent();
        List<Student> searchByAge = new ArrayList<>();
        for (Student ageList : sr.slectAllStudent()) {
            int age = ageList.getAge();
            if (age >= 30 && age < 40) {
                searchByAge.add(ageList);
            }
        }
        return searchByAge;
    }

    public List<Course> searchCourses() {
        cr.slectAllCourse();
        List<Course> searchByName = new ArrayList<>();
        for (Course name : cr.slectAllCourse()) {
            String searchJava = name.getCourseName();
            if (searchJava.equals("Java")) {
                searchByName.add(name);
            }
        }
        return searchByName;

    }
}
