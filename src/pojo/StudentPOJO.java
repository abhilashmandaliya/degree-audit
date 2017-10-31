package pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class StudentPOJO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int student_id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserPOJO user_id;

	@ManyToOne
	@JoinColumn(name = "program_id")
	private ProgramPOJO program_id;

	@Column(name = "year_of_enrolment")
	private int year_of_enrolment;

	public StudentPOJO() {
	}

	public StudentPOJO(UserPOJO user_id, ProgramPOJO program_id, int year_of_enrolment) {
		this.user_id = user_id;
		this.program_id = program_id;
		this.year_of_enrolment = year_of_enrolment;
	}

	public int getStudent_id() {
		return student_id;
	}

	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}

	public UserPOJO getUser_id() {
		return user_id;
	}

	public void setUser_id(UserPOJO user_id) {
		this.user_id = user_id;
	}

	public ProgramPOJO getProgram_id() {
		return program_id;
	}

	public void setProgram_id(ProgramPOJO program_id) {
		this.program_id = program_id;
	}

	public int getYear_of_enrolment() {
		return year_of_enrolment;
	}

	public void setYear_of_enrolment(int year_of_enrolment) {
		this.year_of_enrolment = year_of_enrolment;
	}

}
