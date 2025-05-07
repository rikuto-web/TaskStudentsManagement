package RaiseTechTask.TaskStudentsManagement_final.version.repository;


import RaiseTechTask.TaskStudentsManagement_final.version.data.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentsRepository {
    //全件取得
    @Select("SELECT * FROM students")
    List<Student> slectAllStudent();

    //生徒の追加
    @Insert("INSERT INTO students (id, full_name, nickname, email_address, address, age, gender)" +
            "VALUES(#{id}, #{full_name}, #{nickname}, #{email_address}, #{address}, #{age}, #{gender})")
    void addStudent(Student student);

    //生徒の更新
    @Update("UPDATE students SET full_name= #{full_name}, nickname= #{nickname}, email_address= #{email_address}," +
            "address= #{address}, age= #{age}, gender=#{gender}" +
            "WHERE id = #{id}")
    void updateStudent(Student student);

    //生徒の削除
    @Delete("DELETE FROM students WHERE id = #{id}")
    void deleteStudent(int id);
}
