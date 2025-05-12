package RaiseTechTask.TaskStudentsManagement_final.version.domain;

import RaiseTechTask.TaskStudentsManagement_final.version.data.Course;
import RaiseTechTask.TaskStudentsManagement_final.version.data.Student;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StudentDetail {
    private Student student;
    private List<Course> studentsCourses;
}

