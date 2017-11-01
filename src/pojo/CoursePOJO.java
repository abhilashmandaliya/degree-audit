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
@Table(name = "course")
public class CoursePOJO {
	
	// Columns of course table
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "course_name")
	private String course_name;
	
	@Column(name = "course_id")
	private String course_id;
	
	@Column(name = "course_credits")
	private int course_credits;

	@ManyToOne
	@JoinColumn(name = "course_category")
	private CourseCategoryPOJO course_category;
	
	// constructors
	public CoursePOJO() {
		
	}
	
	public CoursePOJO(String course_name, String course_id, int course_credits, CourseCategoryPOJO course_category) {
		super();
		this.course_name = course_name;
		this.course_id = course_id;
		this.course_credits = course_credits;
		this.course_category = course_category;
	}
	
	// accessor and mutator methods
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public String getCourse_id() {
		return course_id;
	}

	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}

	public int getCourse_credits() {
		return course_credits;
	}

	public void setCourse_credits(int course_credits) {
		this.course_credits = course_credits;
	}

	public CourseCategoryPOJO getCourse_category() {
		return course_category;
	}

	public void setCourse_category(CourseCategoryPOJO course_category) {
		this.course_category = course_category;
	}
	
}
