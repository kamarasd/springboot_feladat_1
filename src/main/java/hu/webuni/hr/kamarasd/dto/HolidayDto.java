package hu.webuni.hr.kamarasd.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Future;

import hu.webuni.hr.kamarasd.model.Approved;

@Entity
public class HolidayDto {
	
	@Id
	@GeneratedValue
	public Long id;
	
	@Future
	public LocalDate holidayStart;
	
	@Future
	public LocalDate holidayEnd;
	
	public String approved;

	public LocalDateTime createdDate;
	
	public String createdBy;
	
	public String superior;
	
	//@OneToMany(mappedBy = "holiday")
	//public List<Employee> employeeList;
	
	public HolidayDto() {
		this.approved = "Not approved";
		this.createdDate = LocalDateTime.now();
	}
	
	public HolidayDto(@Future LocalDate holidayStart, @Future LocalDate holidayEnd,
		String createdBy, String superior) {
		this.holidayStart = holidayStart;
		this.holidayEnd = holidayEnd;
		this.approved = "Not approved";
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

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}
	
	
	
	
	

}
