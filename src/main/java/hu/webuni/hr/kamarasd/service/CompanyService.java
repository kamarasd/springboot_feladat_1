package hu.webuni.hr.kamarasd.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.hr.kamarasd.mapper.EmployeeMapper;
import hu.webuni.hr.kamarasd.model.AverageSalary;
import hu.webuni.hr.kamarasd.model.Company;
import hu.webuni.hr.kamarasd.model.Employee;
import hu.webuni.hr.kamarasd.repository.CompanyRepository;
import hu.webuni.hr.kamarasd.repository.EmployeeRepository;

@Service
public class CompanyService {
	
	@Autowired
	EmployeeMapper employeeMapper;
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	EmployeeService employeeService;
	
	@Transactional
	public Company saveCompany(Company company) {
		return companyRepository.save(company);
	}
	
	public List<Company> findAll() {
		return companyRepository.findAll();
	}
	
	public Optional<Company> findById(Long id) {
		return companyRepository.findById(id);
	}
	
	@Transactional
	public void deleteCompany(Long id) {
		companyRepository.deleteById(id);
	}
	
	public List<Employee> getEmployeeFromCompany(Long id) {
		Company company = companyRepository.findById(id).get();
		return company.getEmployeeList();
	}
	
	@Transactional
	public Company saveEmployeeListToCompany(Long id, List<Employee> employees) {
		Company company = companyRepository.findById(id).get();		
		for (Employee emp: employees) {
			company.addEmployee(emp);
			employeeRepository.save(emp);
		}
		return company;
	}
	
	@Transactional
	public Company deleteEmployeeFromCompany(Long companyId, Long employeeId) {
		Company company = companyRepository.findById(companyId).get();
		Employee employee = employeeRepository.findById(employeeId).get();
		employee.setCompany(null);
		company.getEmployeeList().remove(employee);
		employeeRepository.save(employee);
		
		return company;
	}
	
	@Transactional
	public Company addEmployeeToCompany(Long id, Employee employee) {
		Company company = companyRepository.findByIdWithEmployees(id).get();
		employeeService.setPositionForEmployee(employee);
		company.addEmployee(employee);
		employeeRepository.save(employee);
		
		return company;
	}
	
	@Transactional
	public Company changeAllEmployeeInCompany(Long id, List<Employee> employeeList) {
		Company company = companyRepository.findById(id).get();
		company.getEmployeeList().forEach(e -> e.setCompany(null));
		company.getEmployeeList().clear();
		
		for (Employee emp: employeeList) {
			company.addEmployee(emp);
			employeeRepository.save(emp);
		}

		return company;
	}
	
	public Page<Company> findBySalaryLimit(Pageable pageable, Integer limit) {
		Page<Company> company = companyRepository.findByCompanyWhereSalaryGraterThan(pageable, limit);
		return company;
	}

	public int getPayRaisePercent(Employee employee) {
		return employeeService.getPayRaisePercent(employee);
	}
	
	public List<AverageSalary> getAvarageSalaryByPosition(Long id) {
		return companyRepository.getAvarageSalaryByPosition(id);
	}
	
	public List<Company> findAllWithEmployees() {
		return companyRepository.getAllCompanyWithEmployees();
	}
	
	public Optional<Company> findByIdWithEmployees(long id) {
		return companyRepository.findByIdWithEmployees(id);
	}
}
