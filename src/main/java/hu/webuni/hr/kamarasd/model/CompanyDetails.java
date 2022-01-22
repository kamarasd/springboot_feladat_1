package hu.webuni.hr.kamarasd.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CompanyDetails {
	
	@Id
	@GeneratedValue
	public long id;
	
	public String name;
	
	public CompanyDetails() {
		
	}

	public CompanyDetails(String name) {
		super();
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
