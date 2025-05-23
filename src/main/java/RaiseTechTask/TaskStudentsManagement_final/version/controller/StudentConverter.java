package RaiseTechTask.TaskStudentsManagement_final.version.controller;

import RaiseTechTask.TaskStudentsManagement_final.version.data.Course;
import RaiseTechTask.TaskStudentsManagement_final.version.data.Student;
import RaiseTechTask.TaskStudentsManagement_final.version.domain.StudentDetail;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * 受講生城砦を受講生やコース情報、もしくはその逆の変換を行うコンバーターです
 */
@Component
public class StudentConverter {
    /**
     * 受講生に紐づく受講生コース情報をマッピングする
     * 受講生コース情報は受講生に対し手複数存在するのでループを回して受講生詳細情報を組み立てる
     * 。
     * @param studentList 受講生一覧
     * @param courseList 受講生コース情報のリスト
     * @return 受講生詳細情報のリスト
     */
    public List<StudentDetail> convertStudentDetails(List<Student> studentList, List<Course> courseList) {
        List<StudentDetail> studentDetails = new ArrayList<>();
        studentList.forEach(student -> {
            StudentDetail studentDatail = new StudentDetail();
            studentDatail.setStudent(student);

            List<Course> convertStudentCourses = courseList.stream()
                    .filter(course -> student.getId() == (course.getStudentId()))
                    .collect(Collectors.toList());

            studentDatail.setStudentCourseList(convertStudentCourses);
            studentDetails.add(studentDatail);
        });
        return studentDetails;
    }
}