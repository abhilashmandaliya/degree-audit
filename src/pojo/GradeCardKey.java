package pojo;

import java.io.Serializable;

public class GradeCardKey implements Serializable {

    private long student_id;
    private CoursePOJO course_id;
    private short semester;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public GradeCardKey() {
    }

    public GradeCardKey(long student_id, CoursePOJO course_id, short semester) {
        this.student_id = student_id;
        this.course_id = course_id;
        this.semester = semester;
    }

    public long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(long student_id) {
        this.student_id = student_id;
    }

    public CoursePOJO getCourse_id() {
        return course_id;
    }

    public void setCourse_id(CoursePOJO course_id) {
        this.course_id = course_id;
    }

    public short getSemester() {
        return semester;
    }

    public void setSemester(short semester) {
        this.semester = semester;
    }
}
