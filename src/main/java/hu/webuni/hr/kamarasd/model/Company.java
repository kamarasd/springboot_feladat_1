package hu.webuni.hr.kamarasd.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Company {

	@Id
	@GeneratedValue
	private Long id;
	private String companyNo;
	private String companyName;
	private String companyAddress;
	
	@OneToMany(mappedBy = "company")
	private List<Employee> employeeList;
	
	@ManyToOne
	private CompanyDetails companyDetails;

	public Company() {
		
	}
	
	public Company(String companyNo, String companyName, String companyAddress, List<Employee> employeeList) {
		this.companyNo = companyNo;
		this.companyName = companyName;
		this.companyAddress = companyAddress;
		this.employeeList = employeeList;
	}

	public Long getId() {
		return id;
	}
	
	public String getCompanyNo() {
		return companyNo;
	}
	
	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getCompanyAddress() {
		return companyAddress;
	}
	
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	
	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}
	
	public void addEmployee(Employee employee) {
		if(this.employeeList == null) {
			this.employeeList = new ArrayList<>();
		}
		
		this.employeeList.add(employee);
		employee.setCompany(this);
	}
	
	public CompanyDetails getCompanyDetails() {
		return companyDetails;
	}

	public void setCompanyDetails(CompanyDetails companyDetails) {
		this.companyDetails = companyDetails;
	}

}
