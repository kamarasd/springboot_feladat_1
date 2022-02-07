package hu.webuni.hr.kamarasd.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Future;

@Entity
public class Holiday {
	
	@Id
	@GeneratedValue
	public Long id;
	
	@Future
	public LocalDate holidayStart;
	
	@Future
	public LocalDate holidayEnd;
	
	public Approved approved;

	public LocalDateTime createdDate;
	
	public String createdBy;
	
	public String superior;
	
	//@OneToMany(mappedBy = "holiday")
	//public List<Employee> employeeList;
	
	public Holiday() {
		this.approved = Approved.PENDING;
		this.createdDate = LocalDateTime.now();
	}
	
	public Holiday(@Future LocalDate holidayStart, @Future LocalDate holidayEnd, Approved approved,
		String createdBy, String superior) {
		this.holidayStart = holidayStart;
		this.holidayEnd = holidayEnd;
		this.approved = Approved.PENDING;
		this.createdDate = LocalDateTime.now();
		this.createdBy = createdBy;
		this.superior = superior;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getSuperior() {
		return superior;
	}

	public void setSuperior(String superior) {
		this.superior = superior;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getHolidayStart() {
		return holidayStart;
	}

	public void setHolidayStart(LocalDate holidayStart) {
		this.holidayStart = holidayStart;
	}

	public LocalDate getHolidayEnd() {
		return holidayEnd;
	}

	public void setHolidayEnd(LocalDate holidayEnd) {
		this.holidayEnd = holidayEnd;
	}

	public Approved getApproved() {
		return approved;
	}

	public void setApproved(Approved approved) {
		this.approved = approved;
	}
	
	
	
	
	

}
