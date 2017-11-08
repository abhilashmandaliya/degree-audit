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
@Table(name = "semester_course")
public class SemesterCoursePOJO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "course_id")
	private CoursePOJO course;

	@ManyToOne
	@JoinColumn(name = "semester")
	private SemesterPOJO semester;

	@ManyToOne
	@JoinColumn(name = "program_id")
	private ProgramPOJO program;

	@Column(name = "is_active")
	private boolean is_active;
	
	public SemesterCoursePOJO() {
		// TODO Auto-generated constructor stub
	}

	public SemesterCoursePOJO(CoursePOJO course, SemesterPOJO semester, ProgramPOJO program) {
		this.course = course;
		this.semester = semester;
		this.program = program;
		this.is_active = true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CoursePOJO getCourse() {
		return course;
	}

	public void setCourse(CoursePOJO course) {
		this.course = course;
	}

	public SemesterPOJO getSemester() {
		return semester;
	}

	public void setSemester(SemesterPOJO semester) {
		this.semester = semester;
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
	
}
