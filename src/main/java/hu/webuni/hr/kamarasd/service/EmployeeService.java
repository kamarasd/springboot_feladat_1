package hu.webuni.hr.kamarasd.service;

import java.util.HashMap;
import java.util.Map;

import hu.webuni.hr.kamarasd.model.Employee;

public abstract class EmployeeService extends EmployeeServiceClass {
	
private Map<Long, Employee> employees = new HashMap<>();
	
	public abstract int getPayRaisePercent(Employee employee);

}
