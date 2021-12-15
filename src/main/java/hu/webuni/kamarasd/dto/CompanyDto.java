package hu.webuni.kamarasd.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CompanyDto {
	
	public Long id;
	public String companyNo;
	public String companyName;
	public String companyAddress;
	public List<EmployeeDto> employeeList = new ArrayList<>();
	
	public CompanyDto(long id, String companyNo, String companyName, String companyAddress, List<EmployeeDto> employeeList) {
		super();
		this.id = id;
		this.companyNo = companyNo;
		this.companyName = companyName;
		this.companyAddress = companyAddress;
		this.employeeList = employeeList;
	}

	public List<EmployeeDto> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<EmployeeDto> employeeList) {
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

}
