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
@Table(name = "prerequisite")
public class PrerequisitePOJO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "for_course")
	private CoursePOJO for_course;

	@ManyToOne
	@JoinColumn(name = "required_course")
	private CoursePOJO required_course;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CoursePOJO getFor_course() {
		return for_course;
	}

	public void setFor_course(CoursePOJO for_course) {
		this.for_course = for_course;
	}

	public CoursePOJO getRequired_course() {
		return required_course;
	}

	public void setRequired_course(CoursePOJO required_course) {
		this.required_course = required_course;
	}

	public PrerequisitePOJO(CoursePOJO for_course, CoursePOJO required_course) {
		super();
		this.for_course = for_course;
		this.required_course = required_course;
	}

	public PrerequisitePOJO() {
		// TODO Auto-generated constructor stub
	}
}