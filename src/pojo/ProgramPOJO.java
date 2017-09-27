package pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "program")
public class ProgramPOJO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "min_duration")
	private int min_duration;

	@Column(name = "max_duration")
	private int max_duration;

	@Column(name = "min_credits")
	private int min_credits;

	@Column(name = "max_credits")
	private int max_credits;

	@Column(name = "min_grade_points")
	private int min_grade_points;

	@Column(name = "min_foundation_course")
	private int min_foundation_course;

	@Column(name = "min_courses")
	private int min_courses;

	@Column(name = "max_courses")
	private int max_courses;

	@Column(name = "min_cpi")
	private float min_cpi;

	@Column(name = "name")
	private String name;

	public ProgramPOJO() {
	}

	public ProgramPOJO(String name, int min_duration, int max_duration, int min_credits, int max_credits,
			int min_grade_points, float min_cpi, int min_foundation_course, int min_courses, int max_courses) {
		this.name = name;
		this.min_duration = min_duration;
		this.max_duration = max_duration;
		this.min_credits = min_credits;
		this.max_credits = max_credits;
		this.min_grade_points = min_grade_points;
		this.min_cpi = min_cpi;
		this.min_foundation_course = min_foundation_course;
		this.min_courses = min_courses;
		this.max_courses = max_courses;
	}

	public int getId() {
		return id;
	}

	public int getMax_courses() {
		return max_courses;
	}

	public int getMax_credits() {
		return max_credits;
	}

	public int getMax_duration() {
		return max_duration;
	}

	public int getMin_courses() {
		return min_courses;
	}

	public float getMin_cpi() {
		return min_cpi;
	}

	public int getMin_credits() {
		return min_credits;
	}

	public int getMin_duration() {
		return min_duration;
	}

	public int getMin_foundation_course() {
		return min_foundation_course;
	}

	public int getMin_grade_points() {
		return min_grade_points;
	}

	public String getName() {
		return name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setMax_courses(int max_courses) {
		this.max_courses = max_courses;
	}

	public void setMax_credits(int max_credits) {
		this.max_credits = max_credits;
	}

	public void setMax_duration(int max_duration) {
		this.max_duration = max_duration;
	}

	public void setMin_courses(int min_courses) {
		this.min_courses = min_courses;
	}

	public void setMin_cpi(float min_cpi) {
		this.min_cpi = min_cpi;
	}

	public void setMin_credits(int min_credits) {
		this.min_credits = min_credits;
	}

	public void setMin_duration(int min_duration) {
		this.min_duration = min_duration;
	}

	public void setMin_foundation_course(int min_foundation_course) {
		this.min_foundation_course = min_foundation_course;
	}

	public void setMin_grade_points(int min_grade_points) {
		this.min_grade_points = min_grade_points;
	}

	public void setName(String name) {
		this.name = name;
	}
}
