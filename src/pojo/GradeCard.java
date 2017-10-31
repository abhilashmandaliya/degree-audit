package pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@IdClass(GradeCardKey.class)
@Table(name = "grade_card")
public class GradeCard {

	@Id
	@Column(name = "student_id")
	private long student_id;

	@Id
	@ManyToOne
	@JoinColumn(name = "course_id")
	private CoursePOJO course_id;

	@Id
	@Column(name = "semester")
	private short semester;

	@Column(name = "status")
	private boolean status;

	@Column(name = "earn_grade")
	private double earn_grade;

	public GradeCard() {
	}

	public GradeCard(long student_id, CoursePOJO course_id, short semester, boolean status, double earn_grade) {
		this.student_id = student_id;
		this.course_id = course_id;
		this.semester = semester;
		this.status = status;
		this.earn_grade = earn_grade;
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public double getEarn_grade() {
		return earn_grade;
	}

	public void setEarn_grade(double earn_grade) {
		this.earn_grade = earn_grade;
	}

	
}
