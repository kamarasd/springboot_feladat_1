package hu.webuni.hr.kamarasd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import hu.webuni.hr.kamarasd.service.DefaultEmployeeService;
import hu.webuni.hr.kamarasd.service.EmployeeService;

@Configuration
@Profile("!smart")
public class EmployeeConfiguration {

	@Bean
	public EmployeeService employeeService() {
		return new DefaultEmployeeService();
	}
}
