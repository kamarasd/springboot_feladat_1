package hu.webuni.hr.kamarasd.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import hu.webuni.hr.kamarasd.model.Employee;
import hu.webuni.hr.kamarasd.model.Position;
import hu.webuni.hr.kamarasd.repository.EmployeeRepository;
import hu.webuni.hr.kamarasd.repository.PositionRepository;

@Service
public class EmployeeServiceClass {

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	PositionRepository positionRepository;
	
	public List<Employee> getAll() {
		return employeeRepository.findAll();
	}
	
	public Optional<Employee> findById(Long id) {
		return employeeRepository.findById(id);
	}
	
	@Transactional
	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}
	
	@Transactional
	public Employee changeEmployee(Long id, Employee employee) {
		return employeeRepository.save(employee);
	}
	
	@Transactional
	public void deleteEmployee(Long id) {
		employeeRepository.deleteById(id);
	}
	
	 public List<Employee> salaryLimitCheck(Integer limit) {
		 return employeeRepository.findBySalaryGreaterThan(limit);
	 }
	 
	
	public List<Employee> findEmployeeByPost(String post) {
		return employeeRepository.findEmployeeByPosName(post);
	}
	
	public List<Employee> findEmployeeByName(String name) {
		return employeeRepository.findByNameStartingWithIgnoreCase(name);
	}
	
	public List<Employee> findEmployeeBetweenDate(LocalDateTime dateFrom, LocalDateTime dateTo) {
		return employeeRepository.findAllByWorkingDateBetween(dateFrom, dateTo);
	}
	
	@Transactional
	public void setPositionForEmployee(Employee employee) {
		String posName = employee.getPosition().getPosName();	
		Position position;
		
		if(posName != null) {
			List<Position> foundPosition = positionRepository.findByPosName(posName);
			if(foundPosition.isEmpty()) {
				position = positionRepository.save(new Position(posName));
				employee.setPosition(position);
			} else {
				employee.setPosition(foundPosition.get(0));
			}
		}
	}
	
	public List<Employee> findEmployeeByExample(Employee employee) {
		Long id = employee.getEmployeeId() != null ? employee.getEmployeeId() : 0;
		String name = employee.getName() != null ? employee.getName() : "";
		String post = employee.getPosition().getPosName() != null ? employee.getPosition().getPosName() : "";
		int salary = employee.getSalary() != null ? employee.getSalary() : 0;
		LocalDateTime workingDate = LocalDateTime.of(0001, 01, 01, 00, 00, 00);
		if(employee.getWorkingDate() != null) {
			workingDate = employee.getWorkingDate();
		}
		String company = employee.getCompany().getCompanyName() != null ? employee.getCompany().getCompanyName() : "";
		
		Specification<Employee> spec = Specification.where(null);
		
		if(id > 0 && id != null) {
			spec = spec.and(DinamicEmployeeSearchService.hasId(id));
		}
		
		if(StringUtils.hasText(name) && name != null) {
			spec = spec.and(DinamicEmployeeSearchService.nameStartingWithIgnoreCase(name));
		}
		
		if(StringUtils.hasText(post) && post != null) {
			spec = spec.and(DinamicEmployeeSearchService.hasPost(post));
		}
		
		if(salary > 0) {
			spec = spec.and(DinamicEmployeeSearchService.hasSalary(salary));
		}
		
		if(workingDate != LocalDateTime.of(0001, 01, 01, 00, 00, 00)) {
			spec = spec.and(DinamicEmployeeSearchService.startWorkingDate(workingDate));
		}
		
		if(StringUtils.hasText(company) || company != null) {
			spec = spec.and(DinamicEmployeeSearchService.hasCompany(company));
		}
		
		return employeeRepository.findAll(spec, Sort.by("employeeId"));
	}
	 
	
}
