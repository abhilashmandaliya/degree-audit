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
@Table(name = "program_coordinator")
public class ProgramCoordinatorPOJO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "program_id")
	private ProgramPOJO program;

	@ManyToOne
	@JoinColumn(name = "coordinator_id")
	private UserPOJO user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ProgramPOJO getProgram() {
		return program;
	}

	public void setProgram(ProgramPOJO program) {
		this.program = program;
	}

	public UserPOJO getUser() {
		return user;
	}

	public void setUser(UserPOJO user) {
		this.user = user;
	}
	
	public ProgramCoordinatorPOJO() {
		// TODO Auto-generated constructor stub
	}
	
	public ProgramCoordinatorPOJO(ProgramPOJO program, UserPOJO user) {
		this.program = program;
		this.user = user;
	}
}
