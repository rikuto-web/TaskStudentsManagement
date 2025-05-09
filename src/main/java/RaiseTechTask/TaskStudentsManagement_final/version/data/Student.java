package RaiseTechTask.TaskStudentsManagement_final.version.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {

    private Integer id;
    private String fullName;
    private String furigana;
    private String nickname;
    private String emailAddress;
    private String address;
    private int age;
    private String gender;
}
