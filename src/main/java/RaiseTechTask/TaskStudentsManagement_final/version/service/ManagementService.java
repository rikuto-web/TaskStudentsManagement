package RaiseTechTask.TaskStudentsManagement_final.version.service;

import RaiseTechTask.TaskStudentsManagement_final.version.entity.Course;
import RaiseTechTask.TaskStudentsManagement_final.version.entity.Student;
import RaiseTechTask.TaskStudentsManagement_final.version.repository.CoureseRepository;
import RaiseTechTask.TaskStudentsManagement_final.version.repository.StudentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ManagementService {

    //コンストラクタインジェクション
    private StudentsRepository sr;
    private CoureseRepository cr;

    @Autowired
    public ManagementService(StudentsRepository sr, CoureseRepository cr) {
        this.sr = sr;
        this.cr = cr;
    }

    //全件表示・生徒情報
    public List<Student> getStudentList() {
        return sr.slectAllStudent();
    }

    //全件表示・コース情報
    public List<Course> getCoursesList() {
        return cr.slectAllCourse();
    }


}
