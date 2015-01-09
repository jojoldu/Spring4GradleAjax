package zum.potal.dwlee.vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import com.sun.istack.internal.NotNull;

@Entity
public class User {
	
	@Id
	private String id;
	
	@NotNull
	private String password;
	
	@NotNull
	private String email;
	
	public User() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
