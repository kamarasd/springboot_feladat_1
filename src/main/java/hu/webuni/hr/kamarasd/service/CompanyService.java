package hu.webuni.hr.kamarasd.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.kamarasd.dto.EmployeeDto;
import hu.webuni.hr.kamarasd.mapper.EmployeeMapper;
import hu.webuni.hr.kamarasd.model.Company;

@Service
public class CompanyService {
	
	@Autowired
	EmployeeMapper employeeMapper;
	
	private Map<Long, Company> companies = new HashMap<>();
	
	{
		companies.put(1L, new Company(1, "Teszt cég", "asd", "asd", null));
	}
	
	public Company saveCompany(Company company) {
		companies.put(company.getId(), company);
		return company;
	}
	
	public List<Company> findAll(Boolean type) {
		
		if(type == true && type != null) { 
			return companies
					.values()
					.stream()
					.map(comp -> new Company(comp.getId(), comp.getCompanyNo(), comp.getCompanyName(), comp.getCompanyAddress(), null))
					.collect(Collectors.toList());
		} else {
			return new ArrayList<>(companies.values());	
		}
	}
	
	public Company findById(Long id) {
		return companies.get(id);
	}
	
	public void deleteCompany(Long id) {
		companies.remove(id);
	}
	
	public List<EmployeeDto> getEmployeeFromCompany(Long id) {
		return companies.get(id).getEmployeeList();
	}
	
	public Company saveEmployeeToCompany(Company company, Long id, EmployeeDto employee) {
		List<EmployeeDto> addEmployee = getEmployeeFromCompany(id);
		addEmployee.add(employee);
		companies.get(id).setEmployeeList(addEmployee);
		return company;
	}
	
	public Company deleteEmployeeFromCompany(Company company, Long companyId, Long employeeId) {
		companies.get(companyId).getEmployeeList().removeIf(employee -> employee.getEmployeeId() == employeeId);
		return company;
	}
	
	public Company addEmployeeToCompany(Company company, Long id, List<EmployeeDto> employeeDto) {
		companies.get(id).setEmployeeList(employeeDto);
		return company;
	}
	
	public Company changeAllEmployeeInCompany(Company company, long id, List<EmployeeDto> employeeDto) {
		companies.get(id).setEmployeeList(employeeDto);
		return company;
	}
}
