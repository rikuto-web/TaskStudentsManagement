package RaiseTechTask.TaskStudentsManagement_final.version.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Course {

    private int id;
    private int studentId;
    private String courseName;
    private String courseStartDay;
    private String courseCompletionDay;
}
