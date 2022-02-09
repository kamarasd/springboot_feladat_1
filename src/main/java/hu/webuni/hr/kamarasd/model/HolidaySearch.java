package hu.webuni.hr.kamarasd.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HolidaySearch {

	public Long id;
	public LocalDate holidayDateFrom;
	public LocalDate holidayDateTo;
	public String creatorName;
	public String superiorName;
	
	public Approved approved;
	public LocalDateTime setDateFrom;
	public LocalDateTime setDateTo;
	
	public HolidaySearch() {
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getHolidayDateFrom() {
		return holidayDateFrom;
	}
	public void setHolidayDateFrom(LocalDate holidayDateFrom) {
		this.holidayDateFrom = holidayDateFrom;
	}
	public LocalDate getHolidayDateTo() {
		return holidayDateTo;
	}
	public void setHolidayDateTo(LocalDate holidayDateTo) {
		this.holidayDateTo = holidayDateTo;
	}
	public Approved getApproved() {
		return approved;
	}
	public void setApproved(Approved approved) {
		this.approved = approved;
	}
	public LocalDateTime getSetDateFrom() {
		return setDateFrom;
	}
	public void setSetDateFrom(LocalDateTime setDateFrom) {
		this.setDateFrom = setDateFrom;
	}
	public LocalDateTime getSetDateTo() {
		return setDateTo;
	}
	public void setSetDateTo(LocalDateTime setDateTo) {
		this.setDateTo = setDateTo;
	}
	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getSuperiorName() {
		return superiorName;
	}

	public void setSuperiorName(String superiorName) {
		this.superiorName = superiorName;
	}
	
}
