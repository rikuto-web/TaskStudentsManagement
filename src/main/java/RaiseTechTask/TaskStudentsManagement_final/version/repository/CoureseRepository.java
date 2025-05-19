package RaiseTechTask.TaskStudentsManagement_final.version.repository;

import RaiseTechTask.TaskStudentsManagement_final.version.data.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CoureseRepository {

    //全件取得
    @Select("SELECT * FROM students_courses ")
    List<Course> slectAllCourse();

    //生徒IDに紐づいたコース情報取得
    @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
    List<Course> searchStudentCourse(Integer studentId);

    //コースの追加
    @Insert("INSERT INTO students_courses (student_id, course_name, course_start_day, course_completion_day)" +
            "VALUES(#{studentId}, #{courseName}, #{courseStartDay}, #{courseCompletionDay})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addStudentCourses(Course studentsCourse);

    //コースの更新
    @Update("UPDATE students_courses SET " +
            "course_name = #{courseName} " +
            "WHERE id = #{id}")
    void updateStudentCourses(Course studentCourses);

    //コースの削除
    @Delete("DELETE FROM students_courses WHERE id = #{id}")
    void deleteStudentCourses(Integer id);
}
