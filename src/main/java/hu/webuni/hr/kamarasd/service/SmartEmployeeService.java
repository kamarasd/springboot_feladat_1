package hu.webuni.hr.kamarasd.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.kamarasd.model.Employee;
import hu.webuni.hr.kamarasd.config.EmployeeConfigProperties;

@Service
public class SmartEmployeeService extends EmployeeService {
	
	@Autowired
	EmployeeConfigProperties config;

	@Override
	public int getPayRaisePercent(Employee employee) {
		
		if(employee.getWorkingDate().until(LocalDateTime.now(), ChronoUnit.MONTHS) < config.getSmart().getRaise().getYearTwoHalf()) {
			 return config.getSmart().getPercent().getLessThenTwo();
		} else if (employee.getWorkingDate().until(LocalDateTime.now(), ChronoUnit.MONTHS) > config.getSmart().getRaise().getYearTwoHalf() && employee.getWorkingDate().until(LocalDateTime.now(), ChronoUnit.MONTHS) < config.getSmart().getRaise().getYearFive()) {
			return config.getSmart().getPercent().getMoreThanTwo();
		} else if (employee.getWorkingDate().until(LocalDateTime.now(), ChronoUnit.MONTHS) > config.getSmart().getRaise().getYearFive() && employee.getWorkingDate().until(LocalDateTime.now(), ChronoUnit.MONTHS) < config.getSmart().getRaise().getYearTen()) {
			return config.getSmart().getPercent().getFive();
		} else if (employee.getWorkingDate().until(LocalDateTime.now(), ChronoUnit.MONTHS) > config.getSmart().getRaise().getYearTen()) {
			return config.getSmart().getPercent().getTen();
		} else {
			return config.getSmart().getPercent().getLessThenTwo();
		}
	}

}
