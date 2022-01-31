package hu.webuni.hr.kamarasd.service;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import hu.webuni.hr.kamarasd.model.Company_;
import hu.webuni.hr.kamarasd.model.Employee;
import hu.webuni.hr.kamarasd.model.Employee_;
import hu.webuni.hr.kamarasd.model.Position_;

public class DinamicEmployeeSearchService {
	
	public static Specification<Employee> hasId(Long employeeId) {
		return (root, cq, cb) -> cb.equal(root.get(Employee_.employeeId), employeeId);
	}
	
	public static Specification<Employee> nameStartingWithIgnoreCase(String name) {
		return (root, cq, cb) -> cb.like(cb.lower(root.get(Employee_.name)), name.toLowerCase() + "%");
	}

	public static Specification<Employee> hasPost(String post) {
		return (root, cq, cb) -> cb.equal(root.get(Employee_.position).get(Position_.posName), post);
	}

	public static Specification<Employee> hasSalary(int salary) {
		int salaryPlus5percent = salary + (salary/100)*5;
		int salaryMinus5percent = salary - (salary/100)*5;

		return (root, cq, cb) -> cb.between(root.get(Employee_.salary), salaryMinus5percent, salaryPlus5percent);
	}

	public static Specification<Employee> startWorkingDate(LocalDateTime workingDate) {
		return (root, cq, cb) -> cb.equal(root.get(Employee_.workingDate), workingDate);
	}

	public static Specification<Employee> hasCompany(String company) {
		return (root, cq, cb) -> cb.like(cb.lower(root.get(Employee_.company).get(Company_.companyName)), company.toLowerCase() + "%");
	}

	
	
}
