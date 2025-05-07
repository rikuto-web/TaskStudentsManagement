package RaiseTechTask.TaskStudentsManagement_final.version.data;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Course {

    private Integer id;
    private Integer studentId;
    private String courseName;
    private LocalDateTime courseStartDay;
    private LocalDateTime courseCompletionDay;
}
