package pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "course_program")
public class CourseProgramPOJO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "is_active")
	private boolean is_active;

	@ManyToOne
	@JoinColumn(name = "program_id")
	private ProgramPOJO program;

	@ManyToOne
	@JoinColumn(name = "course_id")
	private CoursePOJO course;

	@ManyToOne
	@JoinColumn(name = "course_cat")
	private CourseCategoryPOJO course_category;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isIs_active() {
		return is_active;
	}

	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}

	public ProgramPOJO getProgram() {
		return program;
	}

	public void setProgram(ProgramPOJO program) {
		this.program = program;
	}

	public CoursePOJO getCourse() {
		return course;
	}

	public void setCourse(CoursePOJO course) {
		this.course = course;
	}

	public CourseCategoryPOJO getCourse_category() {
		return course_category;
	}

	public void setCourse_category(CourseCategoryPOJO course_category) {
		this.course_category = course_category;
	}

	public CourseProgramPOJO() {

	}

	public CourseProgramPOJO(ProgramPOJO program, CoursePOJO course, boolean is_active) {
		this.program = program;
		this.course = course;
		this.is_active = is_active;
	}

}
