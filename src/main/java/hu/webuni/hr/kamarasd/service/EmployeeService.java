package hu.webuni.hr.kamarasd.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import hu.webuni.hr.kamarasd.model.Employee;

public abstract class EmployeeService extends EmployeeServiceClass {
	
private Map<Long, Employee> employees = new HashMap<>();
	
	{
		employees.put(1L, new Employee(1, "Bekő Tóni", "Alkalmazott", 200000, LocalDateTime.parse("2005-01-01T08:00:00")));
		employees.put(2L, new Employee(2, "Matr Ica", "Alkalmazott", 150000, LocalDateTime.parse("2004-05-12T08:00:00")));
	}
	
	public abstract int getPayRaisePercent(Employee employee);

}
