package hu.webuni.hr.kamarasd.service;

import org.springframework.stereotype.Service;

import hu.webuni.hr.kamarasd.model.Employee;

@Service
public class SalaryService{
	
	private EmployeeService employeeService;
	
	public SalaryService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void getNewSalary(Employee employee) {
		employee.setSalary((employee.getSalary() * employeeService.getPayRaisePercent(employee) / 100) + employee.getSalary());
	}
	

}
