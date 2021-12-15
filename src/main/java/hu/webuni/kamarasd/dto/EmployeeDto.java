package hu.webuni.kamarasd.dto;

import java.time.LocalDateTime;

public class EmployeeDto {

	public Long employeeId;
	public String name;
	public String post;
	public Integer salary;
	public LocalDateTime workingDate;
	
	public EmployeeDto(long id, String name, String post, Integer salary, LocalDateTime workingDate) {
		super();
		this.employeeId = id;
		this.name = name;
		this.post = post;
		this.salary = salary;
		this.workingDate = workingDate;
	}
	
	public EmployeeDto() {
		
	}
	
	public Long getEmployeeId() {
		return employeeId;
	}
	
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPost() {
		return post;
	}
	
	public void setPost(String post) {
		this.post = post;
	}
	
	public Integer getSalary() {
		return salary;
	}
	
	public void setSalary(Integer salary) {
		this.salary = salary;
	}
	
	public LocalDateTime getWorkingDate() {
		return workingDate;
	}
	
	public void setWorkingDate(CharSequence workingDate) {
		this.workingDate = LocalDateTime.parse(workingDate);
	}

}
