package pojo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "audit_report")
public class AuditReportPOJO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "student_id")
	private StudentPOJO student_id;

	@Column(name = "date_generated")
	private String date_generated;

	@Column(name = "degree_completed_percent")
	private double degree_completed_percent;

	@Column(name = "obtained_credits")
	private double obtained_credits;

	@Column(name = "required_credits")
	private double required_credits;

	@Column(name = "current_cpi")
	private double current_cpi;

	@Column(name = "required_cpi")
	private double required_cpi;

	@Column(name = "present_courses")
	private double present_courses;

	@Column(name = "required_courses")
	private double required_courses;

	@Column(name = "time_left")
	private double time_left;
	
	@ManyToOne
	@JoinColumn(name = "sem")
	private SemesterPOJO sem;

	public AuditReportPOJO() {

	}

	public AuditReportPOJO(StudentPOJO student_id, double degree_completed_percent, double obtained_credits,
			double required_credits, double current_cpi, double required_cpi, double present_courses,
			double required_courses, double time_left, SemesterPOJO sem) {
		super();
		this.student_id = student_id;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		this.date_generated = dateFormat.format(date).toString();
		this.degree_completed_percent = degree_completed_percent;
		this.obtained_credits = obtained_credits;
		this.required_credits = required_credits;
		this.current_cpi = current_cpi;
		this.required_cpi = required_cpi;
		this.present_courses = present_courses;
		this.required_courses = required_courses;
		this.time_left = time_left;
		this.sem = sem;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public StudentPOJO getStudent_id() {
		return student_id;
	}

	public void setStudent_id(StudentPOJO student_id) {
		this.student_id = student_id;
	}

	public String getDate_generated() {
		return date_generated;
	}

	public void setDate_generated(String date_generated) {
		this.date_generated = date_generated;
	}

	public double getDegree_completed_percent() {
		return degree_completed_percent;
	}

	public void setDegree_completed_percent(double degree_completed_percent) {
		this.degree_completed_percent = degree_completed_percent;
	}

	public double getObtained_credits() {
		return obtained_credits;
	}

	public void setObtained_credits(double obtained_credits) {
		this.obtained_credits = obtained_credits;
	}

	public double getRequired_credits() {
		return required_credits;
	}

	public void setRequired_credits(double required_credits) {
		this.required_credits = required_credits;
	}

	public double getCurrent_cpi() {
		return current_cpi;
	}

	public void setCurrent_cpi(double current_cpi) {
		this.current_cpi = current_cpi;
	}

	public double getRequired_cpi() {
		return required_cpi;
	}

	public void setRequired_cpi(double required_cpi) {
		this.required_cpi = required_cpi;
	}

	public double getPresent_courses() {
		return present_courses;
	}

	public void setPresent_courses(double present_courses) {
		this.present_courses = present_courses;
	}

	public double getRequired_courses() {
		return required_courses;
	}

	public void setRequired_courses(double required_courses) {
		this.required_courses = required_courses;
	}

	public double getTime_left() {
		return time_left;
	}

	public void setTime_left(double time_left) {
		this.time_left = time_left;
	}

	public SemesterPOJO getSem() {
		return sem;
	}

	public void setSem(SemesterPOJO sem) {
		this.sem = sem;
	}

}
