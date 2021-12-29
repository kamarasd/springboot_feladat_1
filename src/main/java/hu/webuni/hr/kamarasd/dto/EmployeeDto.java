package hu.webuni.hr.kamarasd.dto;


import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;

public class EmployeeDto {

	public Long employeeId;
	
	@NotEmpty(message = "Name is empty")
	@NotBlank(message = "Name is blank")
	@NotNull(message = "Name is null")
	public String name;
	
	@NotEmpty(message = "Post is empty")
	@NotBlank(message = "Post is blank")
	@NotNull(message = "Post is null")
	public String post;
	
	@Positive(message = "Salary must be positive number")
	public Integer salary;
	
	@Past(message = "Workingdate need to be in the past")
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
