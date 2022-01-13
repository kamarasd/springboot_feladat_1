package hu.webuni.hr.kamarasd.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;

@Entity
public class Employee {

	@Id
	@GeneratedValue
	public Long employeeId;
	
	@NotBlank(message = "Name is blank")
	public String name;

	@NotBlank(message = "Post is blank")
	public String post;
	
	@Positive(message = "Salary must be positive number")
	public Integer salary;
	
	@Past(message = "Workingdate need to be in the past")
	public LocalDateTime workingDate;
	
	public Employee() {
		
	}
	
	public Employee(long employeeId, String name, String post, Integer salary, LocalDateTime workingDate) {

		this.employeeId = employeeId;
		this.name = name;
		this.post = post;
		this.salary = salary;
		this.workingDate = workingDate;
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
