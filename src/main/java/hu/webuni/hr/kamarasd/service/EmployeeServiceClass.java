package hu.webuni.hr.kamarasd.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.hr.kamarasd.model.Employee;
import hu.webuni.hr.kamarasd.repository.EmployeeRepository;

@Service
public class EmployeeServiceClass {

	@Autowired
	EmployeeRepository employeeRepository;
	
	public List<Employee> getAll() {
		return employeeRepository.findAll();
	}
	
	public Optional<Employee> findById(Long id) {
		return employeeRepository.findById(id);
	}
	
	@Transactional
	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}
	
	@Transactional
	public Employee changeEmployee(Long id, Employee employee) {
		return employeeRepository.save(employee);
	}
	
	public void deleteEmployee(Long id) {
		employeeRepository.deleteById(id);
	}
	
	 public List<Employee> salaryLimitCheck(Integer limit) {
		 return employeeRepository.findBySalaryGreaterThan(limit);
	 }
	 
	
	public List<Employee> findEmployeeByPost(String post) {
		return employeeRepository.findEmployeeByPosName(post);
	}
	
	public List<Employee> findEmployeeByName(String name) {
		return employeeRepository.findByNameContainsIgnoreCase(name);
	}
	
	public List<Employee> findEmployeeBetweenDate(LocalDateTime dateFrom, LocalDateTime dateTo) {
		return employeeRepository.findAllByWorkingDateBetween(dateFrom, dateTo);
	}
	 
	
}
