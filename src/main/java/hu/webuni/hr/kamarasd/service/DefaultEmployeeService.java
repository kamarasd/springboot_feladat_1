package hu.webuni.hr.kamarasd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.kamarasd.Employee;
import hu.webuni.hr.kamarasd.config.EmployeeConfigProperties;

@Service
public class DefaultEmployeeService implements EmployeeService{
	
	@Autowired
	EmployeeConfigProperties config;
	
	@Override
	public int getPayRaisePercent(Employee employee) {
		return config.getSmart().getDef().getDefPercent();
	}
	
	
}
