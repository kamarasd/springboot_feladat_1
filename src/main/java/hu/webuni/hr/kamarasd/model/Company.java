package hu.webuni.hr.kamarasd.model;

import java.util.ArrayList;
import java.util.List;

public class Company {


	private Long id;
	private String companyNo;
	private String companyName;
	private String companyAddress;
	private List<Employee> employeeList = new ArrayList<>();
	
	public Company() {
		
	}
	
	public Company(long id, String companyNo, String companyName, String companyAddress, List<Employee> employeeList) {
		this.id = id;
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

}
