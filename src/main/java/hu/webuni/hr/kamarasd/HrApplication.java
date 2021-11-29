package hu.webuni.hr.kamarasd;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hu.webuni.hr.kamarasd.service.SalaryService;


@SpringBootApplication
public class HrApplication implements CommandLineRunner {
	
	@Autowired
	SalaryService salaryService;

	public static void main(String[] args) {
		SpringApplication.run(HrApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//Create employees
		Employee e1 = new Employee(1, "Bekő Tóni", "Főnök", 200000, LocalDateTime.parse("2005-05-13T08:00:00"));
		Employee e2 = new Employee(2, "Matr Ica", "Beosztott", 170000, LocalDateTime.parse("2015-05-13T08:00:00"));
		Employee e3 = new Employee(3, "Teszt Elek", "Diák", 135000, LocalDateTime.parse("2021-05-13T08:00:00"));
		
		salaryService.getNewSalary(e1);
		System.out.println(e1.getName() + " new salary: " + e1.getSalary());
		
		salaryService.getNewSalary(e2);
		System.out.println(e2.getName() + " new salary: " + e2.getSalary());
		
		salaryService.getNewSalary(e3);
		System.out.println(e3.getName() + " new salary: " + e3.getSalary());
	}
	
}
