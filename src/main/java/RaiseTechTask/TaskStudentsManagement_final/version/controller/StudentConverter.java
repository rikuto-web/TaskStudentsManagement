package RaiseTechTask.TaskStudentsManagement_final.version.controller;

import RaiseTechTask.TaskStudentsManagement_final.version.data.Course;
import RaiseTechTask.TaskStudentsManagement_final.version.data.Student;
import RaiseTechTask.TaskStudentsManagement_final.version.domain.StudentDetail;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudentConverter {

    public List<StudentDetail> convertStudentDetails(List<Student> students, List<Course> courses) {
        List<StudentDetail> studentDetails = new ArrayList<>();
        students.forEach(student -> {
            StudentDetail studentDatail = new StudentDetail();
            studentDatail.setStudent(student);

            List<Course> convertStudentCourses = courses.stream()
                    .filter(course -> student.getId().equals(course.getStudentId()))
                    .collect(Collectors.toList());

            studentDatail.setStudentsCourses(convertStudentCourses);
            studentDetails.add(studentDatail);
        });
        return studentDetails;
    }
}