package pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_category")
public class UserCategoryPOJO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "category")
	private String category;

	public UserCategoryPOJO() {
	}

	public UserCategoryPOJO(String category) {
		super();
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public String getCategory() {
		return category;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
