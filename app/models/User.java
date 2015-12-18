package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BaseModel {

	@Column(unique=true, nullable = false)
	public String username;

	@Column(unique=true, nullable = false)
	public String password;

	@OneToMany(mappedBy = "user", cascade=CascadeType.REMOVE, fetch=FetchType.EAGER)
	public List<UserBookConnection> books = new ArrayList<UserBookConnection>();

	public User(String username) {
		this.username = username;
	}

	public static User authenticate(String username, String password) {
		return find.where().eq("username", username).eq("password", password).findUnique();
	}

	public static Finder<String,User> find = new Finder<String,User>(
			User.class
	);

}
