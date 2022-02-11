package hu.webuni.hr.kamarasd.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;

public class EmployeeDto {

	public Long employeeId;
	
	@NotBlank(message = "Name is blank")
	public String name;
	
	@NotBlank(message = "Post is blank")
	public String post;
	
	@Positive(message = "Salary must be positive number")
	public Integer salary;
	
	@Past(message = "Workingdate need to be in the past")
	public LocalDateTime workingDate;
	
	public String username;
	public String password;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private CompanyDto company;

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

	public CompanyDto getCompany() {
		return company;
	}

	public void setCompany(CompanyDto company) {
		this.company = company;
	}
	
	@Override
	public String toString() {
		return "Employee [employeeId ="+employeeId+", name="+name+", post="+post+", salary="+salary+", workingDate="+workingDate+" ]";
	}
}
