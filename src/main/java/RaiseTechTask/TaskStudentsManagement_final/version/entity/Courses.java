package RaiseTechTask.TaskStudentsManagement_final.version.entity;

public class Courses {

    private int id;
    private int studentId;
    private String courseName;
    private String courseStartDay;
    private String courseCompletionDay;

    public String getCourseStartDay() {
        return courseStartDay;
    }

    public void setCourseStartDay(String courseStartDay) {
        this.courseStartDay = courseStartDay;
    }

    public String getCourseCompletionDay() {
        return courseCompletionDay;
    }

    public void setCourseCompletionDay(String courseCompletionDay) {
        this.courseCompletionDay = courseCompletionDay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
