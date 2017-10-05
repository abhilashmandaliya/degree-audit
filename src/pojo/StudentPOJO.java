package pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class StudentPOJO {

    @Id
    @Column(name = "student_id")
    private long student_id;

    @Column(name = "student_name")
    private String student_name;

    @Column(name = "program_id")
    private int program_id;

    @Column(name = "year_of_enrolment")
    private int year_of_enrolment;

    public StudentPOJO() {
    }

    public StudentPOJO(long student_id, String student_name, int program_id, int year_of_enrolment) {
        this.student_id = student_id;
        this.student_name = student_name;
        this.program_id = program_id;
        this.year_of_enrolment = year_of_enrolment;
    }

    public long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(long student_id) {
        this.student_id = student_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public int getProgram_id() {
        return program_id;
    }

    public void setProgram_id(int program_id) {
        this.program_id = program_id;
    }

    public int getYear_of_enrolment() {
        return year_of_enrolment;
    }

    public void setYear_of_enrolment(int year_of_enrolment) {
        this.year_of_enrolment = year_of_enrolment;
    }

}
