package pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(GradeCardKey.class)
@Table(name = "grade_card")
public class GradeCard {

    @Id
    @Column(name = "student_id")
    private long student_id;

    @Id
    @Column(name = "course_id")
    private String course_id;

    @Id
    @Column(name = "semester")
    private short semester;

    @Column(name = "status")
    private boolean status;

    @Column(name = "total_grade")
    private double total_grade;

    @Column(name = "obtain_grade")
    private double obtain_grade;

    public GradeCard() {
    }

    public GradeCard(long student_id, String course_id, short semester, boolean status, double total_grade, double obtain_grade) {
        this.student_id = student_id;
        this.course_id = course_id;
        this.semester = semester;
        this.status = status;
        this.total_grade = total_grade;
        this.obtain_grade = obtain_grade;
    }

    public long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(long student_id) {
        this.student_id = student_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public short getSemester() {
        return semester;
    }

    public void setSemester(short semester) {
        this.semester = semester;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getTotal_grade() {
        return total_grade;
    }

    public void setTotal_grade(double total_grade) {
        this.total_grade = total_grade;
    }

    public double getObtain_grade() {
        return obtain_grade;
    }

    public void setObtain_grade(double obtain_grade) {
        this.obtain_grade = obtain_grade;
    }
}
