package BasicClassAccessDbase;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class UsersWBC  implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_Users;
	private String name;
	private String lastName;
	private String nikName;
	private String pass;
	
	public UsersWBC (String name,	String lastName, String nikName, String pass) {
		this.name = name;
		this.lastName = lastName;
		this.nikName = nikName;
		this.pass = pass;
	}
	
	public UsersWBC() {
		super();
	}

	public int getId_Users() {
		return id_Users;
	}

	public void setId_Users(int id_Users) {
		this.id_Users = id_Users;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNikName() {
		return nikName;
	}

	public void setNikName(String nikName) {
		this.nikName = nikName;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
	
}
