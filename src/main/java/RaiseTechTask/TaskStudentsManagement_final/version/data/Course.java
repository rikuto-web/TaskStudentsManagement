package RaiseTechTask.TaskStudentsManagement_final.version.data;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Course {

    private Integer id;
    private Integer studentId;
    private String courseName;
    private LocalDate courseStartDay;
    private LocalDate courseCompletionDay;
}
