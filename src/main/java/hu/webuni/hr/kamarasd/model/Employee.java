package hu.webuni.hr.kamarasd.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	
	@Positive(message = "Salary must be positive number")
	public Integer salary;
	
	@Past(message = "Workingdate need to be in the past")
	public LocalDateTime workingDate;
	
	public String username;
	public String password;

	@ManyToOne
	public Company company;

	@ManyToOne
	public Position position;
	
	private String superior;

	public Employee() {
		
	}
	
	public Employee(String name, Integer salary, LocalDateTime workingDate) {
		this.name = name;
		this.salary = salary;
		this.workingDate = workingDate;
	}
	
	public Employee(int salary, LocalDateTime workingDate) {
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
	
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

	public String getSuperior() {
		return superior;
	}

	public void setSuperior(String superior) {
		this.superior = superior;
	}

	
}
