package RaiseTechTask.TaskStudentsManagement_final.version.data;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Course {

    private int id;
    private int studentId;
    private String courseName;
    private LocalDateTime courseStartDay;
    private LocalDateTime courseCompletionDay;
}
