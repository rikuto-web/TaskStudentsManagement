package RaiseTechTask.TaskStudentsManagement_final.version.repository;

import RaiseTechTask.TaskStudentsManagement_final.version.entity.Courses;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CoureseRepository {

    //全件取得
    @Select("SELECT * FROM students_courses")
    List<Courses> slectAllCourses();

    //コースの追加
    @Insert("INSERT INTO students_courses (id, course_name, course_start_day, course_completion_day)" +
            "VALUES(#{id}, #{course_name}, #{course_start_day}, #{course_completion_day}")
    void addStudentCourses(Courses studentCourses);

    //コースの更新
    @Update("UPDATE students_courses SET course_name= #{course_name}, course_start_day= #{course_start_day}, course_completion_day= #{course_completion_day}," +
            "WHERE id = #{id}")
    void updateStudentCourses(Courses studentCourses);

    //コースの削除
    @Delete("DELETE FROM students_courses WHERE id = #{id}")
    void deleteStudentCourses(int id);
}
