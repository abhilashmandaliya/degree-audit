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
@Table(name = "user")
public class UserPOJO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "is_active")
	private short is_active;

	@ManyToOne
	@JoinColumn(name = "category")
	private UserCategoryPOJO userCategory;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public short getIs_active() {
		return is_active;
	}

	public void setIs_active(short is_active) {
		this.is_active = is_active;
	}

	public UserCategoryPOJO getUserCategory() {
		return userCategory;
	}

	public void setUserCategory(UserCategoryPOJO userCategory) {
		this.userCategory = userCategory;
	}

	public UserPOJO() {
	}

	public UserPOJO(String username, String password, UserCategoryPOJO category, short is_active) {
		super();
		this.username = username;
		this.password = password;
		this.userCategory = category;
		this.is_active = is_active;
	}

}
