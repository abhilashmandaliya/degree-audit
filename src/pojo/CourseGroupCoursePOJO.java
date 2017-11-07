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
@Table(name = "course_group_course")
public class CourseGroupCoursePOJO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "course_id")
	private CoursePOJO course;

	@ManyToOne
	@JoinColumn(name = "course_group")
	private CourseGroupPOJO course_group;

	@Column(name = "is_active")
	private boolean is_active;

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

	public CourseGroupPOJO getCourse_group() {
		return course_group;
	}

	public void setCourse_group(CourseGroupPOJO course_group) {
		this.course_group = course_group;
	}

	public boolean isIs_active() {
		return is_active;
	}

	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}
	
	public CourseGroupCoursePOJO() {
		// TODO Auto-generated constructor stub
	}
	
	public CourseGroupCoursePOJO(CoursePOJO course, CourseGroupPOJO course_group) {
		this.course = course;
		this.course_group = course_group;
		this.is_active = true;
	}
}
