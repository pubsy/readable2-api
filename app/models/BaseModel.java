package models;

import com.avaje.ebean.Model;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@MappedSuperclass
public class BaseModel extends Model {

	@Id
	@GeneratedValue
	public Long id;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "inserted_at")
	public Date insertedAt;
	
	public BaseModel(){
		this.insertedAt = new Date();
	}
}
