package pojo;

import javax.persistence.*;

@Entity
@Table(name = "course_category")
public class CourseCategoryPOJO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "course_cat_name")
	private String course_cat_name;
	
	@Column(name = "course_cat_code")
	private String course_cat_code;

	public CourseCategoryPOJO() {
	
	}
	
	public CourseCategoryPOJO(String course_cat_name, String course_cat_code) {
		super();
		this.course_cat_name = course_cat_name;
		this.course_cat_code = course_cat_code;
	}


	public String getCourse_cat_name() {
		return course_cat_name;
	}

	public void setCourse_cat_name(String course_cat_name) {
		this.course_cat_name = course_cat_name;
	}

	public String getCourse_cat_code() {
		return course_cat_code;
	}

	public void setCourse_cat_code(String course_cat_code) {
		this.course_cat_code = course_cat_code;
	}
}
