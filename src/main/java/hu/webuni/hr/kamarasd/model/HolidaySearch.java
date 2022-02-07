package hu.webuni.hr.kamarasd.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HolidaySearch {

	public Long id;
	public LocalDate holidayDateFrom;
	public LocalDate holidayDateTo;
	public String name;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
	
	
	
}
