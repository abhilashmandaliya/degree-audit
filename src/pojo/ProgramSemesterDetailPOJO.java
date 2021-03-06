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
@Table(name = "program_semester_detail")
public class ProgramSemesterDetailPOJO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "semester_id")
	private SemesterPOJO semester;

	@ManyToOne
	@JoinColumn(name = "program_id")
	private ProgramPOJO program;

	@Column(name = "core")
	private int core;

	@Column(name = "min_tech_electives")
	private int min_tech_electives;

	@Column(name = "max_tech_electives")
	private int max_tech_electives;

	@Column(name = "min_open_electives")
	private int min_open_electives;

	@Column(name = "max_open_electives")
	private int max_open_electives;

	@Column(name = "core_credits")
	private int core_credits;

	@Column(name = "min_tech_electives_credits")
	private int min_tech_electives_credits;

	@Column(name = "max_tech_electives_credits")
	private int max_tech_electives_credits;

	@Column(name = "min_open_electives_credits")
	private int min_open_electives_credits;

	@Column(name = "max_open_electives_credits")
	private int max_open_electives_credits;

	@Column(name = "min_semester_credits")
	private int min_semester_credits;

	@Column(name = "max_semester_credits")
	private int max_semester_credits;

	@Column(name = "max_semester_courses")
	private int max_semester_courses;

	@Column(name = "min_semester_courses")
	private int min_semester_courses;

	@Column(name = "min_spi")
	private float min_spi;

	public ProgramSemesterDetailPOJO() {
		// TODO Auto-generated constructor stub
	}

	public ProgramSemesterDetailPOJO(SemesterPOJO semester, ProgramPOJO program, int core, int min_tech_electives,
			int max_tech_electives, int min_open_electives, int max_open_electives, int core_credits,
			int min_tech_electives_credits, int max_tech_electives_credits, int min_open_electives_credits,
			int max_open_electives_credits, int min_semester_credits, int max_semester_credits,
			int min_semester_courses, int max_semester_cources, float min_spi) {
		super();
		this.semester = semester;
		this.program = program;
		this.core = core;
		this.min_tech_electives = min_tech_electives;
		this.max_tech_electives = max_tech_electives;
		this.min_open_electives = min_open_electives;
		this.max_open_electives = max_open_electives;
		this.core_credits = core_credits;
		this.min_tech_electives_credits = min_tech_electives_credits;
		this.max_tech_electives_credits = max_tech_electives_credits;
		this.min_open_electives_credits = min_open_electives_credits;
		this.max_open_electives_credits = max_open_electives_credits;
		this.min_semester_credits = min_semester_credits;
		this.max_semester_credits = max_semester_credits;
		this.max_semester_courses = max_semester_cources;
		this.min_semester_courses = min_semester_courses;
		this.min_spi = min_spi;
	}

	public int getMax_semester_courses() {
		return max_semester_courses;
	}

	public void setMax_semester_courses(int max_semester_courses) {
		this.max_semester_courses = max_semester_courses;
	}

	public int getMin_semester_courses() {
		return min_semester_courses;
	}

	public void setMin_semester_courses(int min_semester_courses) {
		this.min_semester_courses = min_semester_courses;
	}

	public float getMin_spi() {
		return min_spi;
	}

	public void setMin_spi(float min_spi) {
		this.min_spi = min_spi;
	}

	public int getMin_semester_credits() {
		return min_semester_credits;
	}

	public void setMin_semester_credits(int min_semester_credits) {
		this.min_semester_credits = min_semester_credits;
	}

	public int getMax_semester_credits() {
		return max_semester_credits;
	}

	public void setMax_semester_credits(int max_semester_credits) {
		this.max_semester_credits = max_semester_credits;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SemesterPOJO getSemester() {
		return semester;
	}

	public void setSemester(SemesterPOJO semester) {
		this.semester = semester;
	}

	public ProgramPOJO getProgram() {
		return program;
	}

	public void setProgram(ProgramPOJO program) {
		this.program = program;
	}

	public int getCore() {
		return core;
	}

	public void setCore(int core) {
		this.core = core;
	}

	public int getMin_tech_electives() {
		return min_tech_electives;
	}

	public void setMin_tech_electives(int min_tech_electives) {
		this.min_tech_electives = min_tech_electives;
	}

	public int getMax_tech_electives() {
		return max_tech_electives;
	}

	public void setMax_tech_electives(int max_tech_electives) {
		this.max_tech_electives = max_tech_electives;
	}

	public int getMin_open_electives() {
		return min_open_electives;
	}

	public void setMin_open_electives(int min_open_electives) {
		this.min_open_electives = min_open_electives;
	}

	public int getMax_open_electives() {
		return max_open_electives;
	}

	public void setMax_open_electives(int max_open_electives) {
		this.max_open_electives = max_open_electives;
	}

	public int getCore_credits() {
		return core_credits;
	}

	public void setCore_credits(int core_credits) {
		this.core_credits = core_credits;
	}

	public int getMin_tech_electives_credits() {
		return min_tech_electives_credits;
	}

	public void setMin_tech_electives_credits(int min_tech_electives_credits) {
		this.min_tech_electives_credits = min_tech_electives_credits;
	}

	public int getMax_tech_electives_credits() {
		return max_tech_electives_credits;
	}

	public void setMax_tech_electives_credits(int max_tech_electives_credits) {
		this.max_tech_electives_credits = max_tech_electives_credits;
	}

	public int getMin_open_electives_credits() {
		return min_open_electives_credits;
	}

	public void setMin_open_electives_credits(int min_open_electives_credits) {
		this.min_open_electives_credits = min_open_electives_credits;
	}

	public int getMax_open_electives_credits() {
		return max_open_electives_credits;
	}

	public void setMax_open_electives_credits(int max_open_electives_credits) {
		this.max_open_electives_credits = max_open_electives_credits;
	}

}
