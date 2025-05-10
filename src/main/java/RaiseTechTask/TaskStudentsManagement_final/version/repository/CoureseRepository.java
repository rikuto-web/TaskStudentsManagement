package RaiseTechTask.TaskStudentsManagement_final.version.repository;

import RaiseTechTask.TaskStudentsManagement_final.version.data.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CoureseRepository {

    /**
     * 全件検索します
     * @return全件検索した情報
     */
    /**
     * リターンあるとメソッドにも反映される
     * voidは戻り値がないためなし
     */


    //全件取得
    @Select("SELECT * FROM students_courses")
    List<Course> slectAllCourse();

    //コースの追加
    @Insert("INSERT INTO students_courses (student_id, course_name, course_start_day, course_completion_day)" +
            "VALUES(#{studentId}, #{courseName}, #{courseStartDay}, #{courseCompletionDay})")
    void addStudentCourses(Course studentCourses);

    //コースの更新
    @Update("UPDATE students_courses SET course_name= #{course_name}, course_start_day= #{course_start_day}, course_completion_day= #{course_completion_day}" +
            "WHERE id = #{id}")
    void updateStudentCourses(Course studentCourses);

    //コースの削除
    @Delete("DELETE FROM students_courses WHERE id = #{id}")
    void deleteStudentCourses(int id);
}
