package hu.webuni.hr.kamarasd.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import hu.webuni.hr.kamarasd.model.Employee;

@Service
public class EmployeeServiceClass {

	private Map<Long, Employee> employees = new HashMap<>();
	
	{
		employees.put(1L, new Employee(1, "Bekő Tóni", "Alkalmazott", 200000, LocalDateTime.parse("2005-01-01T08:00:00")));
		employees.put(2L, new Employee(2, "Matr Ica", "Alkalmazott", 150000, LocalDateTime.parse("2004-05-12T08:00:00")));
	}
	
	public List<Employee> getAll() {
		return new ArrayList<>(employees.values());
	}
	
	public Employee findById(Long id) {
		return employees.get(id);
	}
	
	public Employee saveEmployee(Employee employee) {
		employees.put(employee.getEmployeeId(), employee);
		return employee;
	}
	
	public Employee changeEmployee(Long id, Employee employee) {
		employees.put(id, employee);
		return employee;
	}
	
	public void deleteEmployee(Long id) {
		employees.remove(id);
	}
	
	public Collection<Employee> salaryLimitCheck(Integer limit) {
		Collection<Employee> emp = employees.entrySet().stream().
				filter(employee -> employee.getValue().getSalary() > limit).
				collect(Collectors.toMap(employee -> employee.getKey(),employee -> employee.getValue())).values();
		return emp;
	}
	
}
