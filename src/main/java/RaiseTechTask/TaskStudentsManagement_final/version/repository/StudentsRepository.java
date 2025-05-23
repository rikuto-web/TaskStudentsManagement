package RaiseTechTask.TaskStudentsManagement_final.version.repository;


import RaiseTechTask.TaskStudentsManagement_final.version.data.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentsRepository {
    //全件取得(論理削除）
    @Select("SELECT * FROM students WHERE is_deleted = 0")
    @Results({@Result(property = "deleted", column = "is_deleted")})
    List<Student> search();

    //生徒IDと一致する情報取得
    @Select("SELECT * FROM students WHERE id = #{id}")
    Student searchStudent(Integer id);

    //生徒の追加
    @Insert("INSERT INTO students (full_name, furigana, nickname, email_address, address, age, gender, remark, is_Deleted)" +
            "VALUES(#{fullName}, #{furigana}, #{nickname}, #{emailAddress}, #{address}, #{age}, #{gender}, #{remark}, #{deleted})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addStudent(Student student);

    //生徒の更新
    @Update("UPDATE students SET " +
            "full_name = #{fullName}, " +
            "furigana = #{furigana}, " +
            "nickname = #{nickname}, " +
            "email_address = #{emailAddress}, " +
            "address = #{address}, " +
            "age = #{age}, " +
            "gender =#{gender}, " +
            "remark =#{remark}, " +
            "is_deleted = #{deleted} " +
            "WHERE id = #{id}")
    void updateStudent(Student student);

    //生徒の削除
    @Delete("DELETE FROM students WHERE id = #{id}")
    void deleteStudent(Integer id);


}
