package hu.webuni.hr.kamarasd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.kamarasd.model.Employee;
import hu.webuni.hr.kamarasd.config.EmployeeConfigProperties;

@Service
public class DefaultEmployeeService extends EmployeeService{
	
	@Autowired
	EmployeeConfigProperties config;
	
	@Override
	public int getPayRaisePercent(Employee employee) {
		return config.getSmart().getDef().getDefPercent();
	}
	
	
}
