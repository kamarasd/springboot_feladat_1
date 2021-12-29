package hu.webuni.hr.kamarasd.dto;

import java.util.ArrayList;
import java.util.List;

public class CompanyDto {
	
	private Long id;
	private String companyNo;
	private String companyName;
	private String companyAddress;
	List<EmployeeDto> employeeList = new ArrayList<>();
	
	public CompanyDto() {
		
	}
	
	public CompanyDto(long id, String companyNo, String companyName, String companyAddress, List<EmployeeDto> employeeList) {
		super();
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
	
	public List<EmployeeDto> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<EmployeeDto> employeeList) {
		this.employeeList = employeeList;
	}

}
