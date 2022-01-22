package hu.webuni.hr.kamarasd.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Position {

	@Id
	@GeneratedValue
	public Long id;
	public String posName;
	public Qualification qualification;
	public Integer minSalary;
	
	public Position() {
		
	}
	
	public Position(String posName, Qualification qualification, Integer minSalary) {
		this.posName = posName;
		this.qualification = qualification;
		this.minSalary = minSalary;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getPosName() {
		return posName;
	}
	
	public void setPosName(String posName) {
		this.posName = posName;
	}
	
	public Qualification getQualification() {
		return qualification;
	}
	
	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}
	
	public Integer getMinSalary() {
		return minSalary;
	}
	
	public void setMinSalary(Integer minSalary) {
		this.minSalary = minSalary;
	}
	
	
	
}
