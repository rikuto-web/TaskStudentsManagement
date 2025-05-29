package RaiseTechTask.TaskStudentsManagement_final.version.service;

import RaiseTechTask.TaskStudentsManagement_final.version.controller.StudentConverter;
import RaiseTechTask.TaskStudentsManagement_final.version.data.Course;
import RaiseTechTask.TaskStudentsManagement_final.version.data.Student;
import RaiseTechTask.TaskStudentsManagement_final.version.domain.StudentDetail;
import RaiseTechTask.TaskStudentsManagement_final.version.repository.CoursesRepository;
import RaiseTechTask.TaskStudentsManagement_final.version.repository.StudentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * 受講生情報を取り扱うサービスです
 * 受講生の検索や登録・更新を行います
 */
@Service
public class StudentService {

    private StudentsRepository studentRepository;
    private CoursesRepository coursesRepository;
    private StudentConverter converter;


    @Autowired
    public StudentService(StudentsRepository studentRepository, CoursesRepository coursesRepository, StudentConverter converter) {
        this.studentRepository = studentRepository;
        this.coursesRepository = coursesRepository;
        this.converter = converter;

    }

    /**
     * 受講生の詳細一覧検索を行います。
     * 条件検索を行うので、条件指定は行いません。
     * @return 受講生一覧（全件）
     */
    @Transactional
    public List<StudentDetail> searchStudentList() {
        List<Student> studentList = studentRepository.searchStudentsList();
        List<Course> courseList = coursesRepository.searchCourses();
        return converter.convertStudentDetails(studentList, courseList);
    }

    /**
     * 受講生IDで受講生の詳細を検索処理を行います。。
     * IDに紐づく受講生情報を取得したあと、その受講生に紐づく受講生コース情報を取得して設定します。
     * @param id　受講生ID
     * @return 受講生
     */
    @Transactional
    public StudentDetail searchStudent(int id) {
        Student student = studentRepository.searchStudent(id);
        List<Course> course = coursesRepository.searchStudentCourse(student.getId());
        return new StudentDetail(student, course);
    }

    /**
     * 受講生詳細の登録処理を行います。
     * コース情報は、受講生ＩＤと紐づけて登録します。
     * 受講開始日と修了予定日を自動設定して登録します。
     * @param studentDetail 受講生情報
     * @return 登録情報を付与した受講生詳細
     */
    @Transactional
    public StudentDetail registerStudent(StudentDetail studentDetail) {
        Student student = studentDetail.getStudent();

        studentRepository.registerStudent(student);
        studentDetail.getStudentCourseList().forEach(studentCourse -> {
            initStudentsCourse(studentCourse, student);
            coursesRepository.registerStudentCourses(studentCourse);
        });
        return studentDetail;
    }

    /**
     * 受講生コース情報を登録する際の初期情報を設定する。
     *
     * @param studentCourse 受講生コース情報
     * @param student　受講生
     */
    private static void initStudentsCourse(Course studentCourse, Student student) {
        LocalDate now = LocalDate.now();

        studentCourse.setStudentId(student.getId());
        studentCourse.setCourseStartDay(now);
        studentCourse.setCourseCompletionDay(now.plusYears(1));
    }

    /**
     * 受講生詳細の更新作業を行います。
     * 受講生と受講生IDと紐づいたコース情報を更新します。
     * @param studentDetail 受講生情報とコース情報
     */
    @Transactional
    public void updateStudent(StudentDetail studentDetail) {
        studentRepository.updateStudent(studentDetail.getStudent());
        studentDetail.getStudentCourseList()
                .forEach(studentCourse -> coursesRepository.updateStudentCourses(studentCourse));
    }
}