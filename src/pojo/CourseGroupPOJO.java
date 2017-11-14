package pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "course_group")
public class CourseGroupPOJO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "group_name")
	private String group_name;

	@Column(name = "min_avg")
	private float min_avg;

	@Column(name = "max_avg")
	private float max_avg;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public float getMin_avg() {
		return min_avg;
	}

	public void setMin_Avg(float min_avg) {
		this.min_avg = min_avg;
	}

	public float getMax_avg() {
		return max_avg;
	}

	public void setMax_cpi(float max_cpi) {
		this.max_avg = max_cpi;
	}
	
	public CourseGroupPOJO() {
		// TODO Auto-generated constructor stub
	}
	
	public CourseGroupPOJO(String group_name, float min_avg, float max_avg) {
		this.group_name = group_name;
		this.min_avg = min_avg;
		this.max_avg = max_avg;
	}
}
