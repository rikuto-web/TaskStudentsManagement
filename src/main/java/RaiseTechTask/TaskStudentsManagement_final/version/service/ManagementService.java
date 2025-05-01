package RaiseTechTask.TaskStudentsManagement_final.version.service;

import RaiseTechTask.TaskStudentsManagement_final.version.entity.Courses;
import RaiseTechTask.TaskStudentsManagement_final.version.entity.Students;
import RaiseTechTask.TaskStudentsManagement_final.version.repository.CoureseRepository;
import RaiseTechTask.TaskStudentsManagement_final.version.repository.StudentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ManagementService {

    //コンストラクタインジェクション
    private StudentsRepository sr;

    @Autowired
    public ManagementService(StudentsRepository sr) {
        this.sr = sr;
    }

    //フィールドインジェクション
    @Autowired
    private CoureseRepository cr;


    //全件表示・生徒情報
    public List<Students> getStudentList() {
        return sr.slectAllStudents();
    }

    //全件表示・コース情報
    public List<Courses> getCoursesList() {
        return cr.slectAllCourses();
    }


}
