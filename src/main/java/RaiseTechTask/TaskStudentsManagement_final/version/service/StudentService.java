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
    private StudentsRepository studentsRepository;
    private CoureseRepository courseRepository;

    @Autowired
    public StudentService(StudentsRepository sr, CoureseRepository cr) {
        this.studentsRepository = sr;
        this.courseRepository = cr;
    }

    //全件表示・生徒情報
    public List<Student> searchStudentList() {
        return studentsRepository.selectAllStudent();
    }

    //全件表示・コース情報
    public List<Course> searchCoursesList() {
        return courseRepository.slectAllCourse();
    }

    //生徒情報の登録
    public void postStudent(Student student) {
        studentsRepository.addStudent(student);
    }

    //コース情報の登録
    public void postCourse(Course course) {
        courseRepository.addStudentCourses(course);
    }


    //３０代の年齢絞り込み機能
    public List<Student> searchStudent() {
        studentsRepository.selectAllStudent();
        List<Student> searchByAge = new ArrayList<>();
        for (Student ageList : studentsRepository.selectAllStudent()) {
            int age = ageList.getAge();
            if (age >= 30 && age < 40) {
                searchByAge.add(ageList);
            }
        }
        return searchByAge;
    }

    public List<Course> searchCourses() {
        courseRepository.slectAllCourse();
        List<Course> searchByName = new ArrayList<>();
        for (Course name : courseRepository.slectAllCourse()) {
            String searchJava = name.getCourseName();
            if (searchJava.equals("Java")) {
                searchByName.add(name);
            }
        }
        return searchByName;
    }
}