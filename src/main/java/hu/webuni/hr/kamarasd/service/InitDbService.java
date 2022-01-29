package hu.webuni.hr.kamarasd.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.hr.kamarasd.model.Company;
import hu.webuni.hr.kamarasd.model.Employee;
import hu.webuni.hr.kamarasd.model.Position;
import hu.webuni.hr.kamarasd.model.Qualification;
import hu.webuni.hr.kamarasd.repository.CompanyDetailsRepository;
import hu.webuni.hr.kamarasd.repository.CompanyRepository;
import hu.webuni.hr.kamarasd.repository.EmployeeRepository;
import hu.webuni.hr.kamarasd.repository.PositionRepository;

@Service
public class InitDbService {

	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	PositionRepository positionRepository;
	
	@Autowired
	CompanyDetailsRepository companyDetailsRepository ;
	
	@Transactional
	public void clearDb() {
		employeeRepository.deleteAll();
		companyRepository.deleteAll();
		positionRepository.deleteAll();
		companyDetailsRepository.deleteAll();
	}
	
	@Transactional
	public void insertTestData() {
		String pos1 = "Tester";
		String pos2 = "Developer";
		
		Position p1 = positionRepository.save(new Position(pos1, Qualification.HIGH_SCHOOL, 200000));
		Position p2 = positionRepository.save(new Position(pos2, Qualification.UNIVERSITY, 300000));
		
		Company c1 = companyRepository.save(new Company("TC002", "Teszt cég 2", "3070 Bátonyterenye Fő út 1", null));
		companyRepository.save(c1);
		Employee e1 = new Employee("Bekő Tóni", 200000, LocalDateTime.parse("2005-05-13T08:00:00"));
		e1.setPosition(p1);
		Employee e2 = new Employee("Matr Ica", 170000, LocalDateTime.parse("2015-05-13T08:00:00"));
		e2.setPosition(p2);
		
		e1.setCompany(c1);
		employeeRepository.save(e1);
		e2.setCompany(c1);
		employeeRepository.save(e2);
		
		Company c2 = companyRepository.save(new Company("TC001", "Teszt cég", "1149 Budapest Teszt utca 13", null));
		companyRepository.save(c2);
		Employee e3 = new Employee("Teszt Elek", 135000, LocalDateTime.parse("2021-05-13T08:00:00"));
		e3.setPosition(p1);
		e3.setCompany(c2);
		employeeRepository.save(e3);
	}
}
