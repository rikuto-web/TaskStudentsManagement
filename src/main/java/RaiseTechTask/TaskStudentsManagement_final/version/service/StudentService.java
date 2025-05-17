package RaiseTechTask.TaskStudentsManagement_final.version.service;

import RaiseTechTask.TaskStudentsManagement_final.version.data.Course;
import RaiseTechTask.TaskStudentsManagement_final.version.data.Student;
import RaiseTechTask.TaskStudentsManagement_final.version.domain.StudentDetail;
import RaiseTechTask.TaskStudentsManagement_final.version.repository.CoureseRepository;
import RaiseTechTask.TaskStudentsManagement_final.version.repository.StudentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentService {
    //コンストラクタインジェクション
    private StudentsRepository studentRepository;
    private CoureseRepository coursesRepository;

    @Autowired
    public StudentService(StudentsRepository studentRepository, CoureseRepository coursesRepository) {
        this.studentRepository = studentRepository;
        this.coursesRepository = coursesRepository;
    }

    //全件表示・生徒
    public List<Student> searchStudentList() {
        return studentRepository.selectAllStudent();
    }

    //全件表示・コース
    public List<Course> searchCoursesList() {
        return coursesRepository.slectAllCourse();
    }

    //登録
    @Transactional
    public void postStudent(StudentDetail studentDetail) {
        studentRepository.addStudent(studentDetail.getStudent());
        for (Course studentCourse : studentDetail.getStudentsCourses()) {
            studentCourse.setStudentId(studentDetail.getStudent().getId());
            studentCourse.setCourseStartDay(LocalDate.now());
            studentCourse.setCourseCompletionDay(LocalDate.now().plusYears(1));
            coursesRepository.addStudentCourses(studentCourse);
        }
    }

    //生徒情報の検索
    @Transactional
    public StudentDetail pickupStudent(Integer id) {
        Student student = studentRepository.selectStudentById(id);
        List<Course> courses = coursesRepository.selectStudentCoursesById(id);
        StudentDetail studentDetail = new StudentDetail();
        studentDetail.setStudent(student);
        studentDetail.setStudentsCourses(courses);
        return studentDetail;
    }

    //更新処理
    @Transactional
    public void updateStudent(StudentDetail studentDetail) {
        studentRepository.updateStudent(studentDetail.getStudent());
        for (Course studentCourse : studentDetail.getStudentsCourses()) {
            studentCourse.setStudentId(studentDetail.getStudent().getId());
            studentCourse.setCourseStartDay(LocalDate.now());
            studentCourse.setCourseCompletionDay(LocalDate.now().plusYears(1));
            coursesRepository.updateStudentCourses(studentCourse);
        }
    }
}