package hu.webuni.hr.kamarasd;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hu.webuni.hr.kamarasd.service.InitDbService;
import hu.webuni.hr.kamarasd.service.SalaryService;

import hu.webuni.hr.kamarasd.model.Employee;
import hu.webuni.hr.kamarasd.model.Company;


@SpringBootApplication
public class HrApplication implements CommandLineRunner {
	
	@Autowired
	SalaryService salaryService;
	
	@Autowired
	InitDbService initDbService;

	public static void main(String[] args) {
		SpringApplication.run(HrApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		
		initDbService.clearDb();
		
		//Create employees
		
		initDbService.insertTestData();
		
		/*
		 * salaryService.getNewSalary(e1); System.out.println(e1.getName() +
		 * " new salary: " + e1.getSalary());
		 * 
		 * salaryService.getNewSalary(e2); System.out.println(e2.getName() +
		 * " new salary: " + e2.getSalary());
		 * 
		 * salaryService.getNewSalary(e3); System.out.println(e3.getName() +
		 * " new salary: " + e3.getSalary());
		 */
		
		
	}
	
}
