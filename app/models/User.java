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

	@OneToMany(mappedBy = "user", cascade=CascadeType.REMOVE, fetch=FetchType.EAGER)
	public List<UserBookConnection> books = new ArrayList<UserBookConnection>();

	public User(String username) {
		this.username = username;
	}

}
