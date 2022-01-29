package hu.webuni.hr.kamarasd.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Optional;

import org.assertj.core.data.TemporalUnitWithinOffset;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.hr.kamarasd.model.Employee;
import hu.webuni.hr.kamarasd.model.Position;
import hu.webuni.hr.kamarasd.repository.CompanyRepository;
import hu.webuni.hr.kamarasd.repository.EmployeeRepository;
import hu.webuni.hr.kamarasd.repository.PositionRepository;
import hu.webuni.hr.kamarasd.mapper.EmployeeMapper;
import hu.webuni.hr.kamarasd.model.Company;

@SpringBootTest
@AutoConfigureTestDatabase
public class CompanyTest {
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	PositionRepository positionRepository;
	
	@Autowired
	EmployeeMapper employeeMapper;
	
	@Autowired
	InitDbService initDbService;
	
	@Test
	public void testAddEmployeeToCompany() throws Exception {
		initDbService.clearDb();
		Employee employee = createEmployee();
		Company company = createCompany(employee);
		
		Long savedCompanyId = companyService.saveCompany(company).getId();
		Long savedEmployeeId = employeeService.saveEmployee(employee).getEmployeeId();
		
		Optional<Employee> savedEmpOpt = employeeService.findById(savedEmployeeId);
		assertThat(savedEmpOpt).isNotEmpty();
		Employee savedEmp = savedEmpOpt.get();
		assertThat(employee.getName()).isEqualTo(savedEmp.getName());
		
		Optional<Company> savedCompOpt = companyService.findByIdWithEmployees(savedCompanyId);
		assertThat(savedCompOpt).isNotEmpty();
		Company savedComp = savedCompOpt.get();
		assertThat(company.getCompanyName()).isEqualTo(savedComp.getCompanyName());
		assertThat(company.getEmployeeList().get(0).getName()).isEqualTo(savedComp.getEmployeeList().get(0).getName());
		assertThat(company.getEmployeeList().get(0).getSalary()).isEqualTo(savedComp.getEmployeeList().get(0).getSalary());
		assertThat(company.getEmployeeList().get(0).getPosition().posName).isEqualTo(savedComp.getEmployeeList().get(0).getPosition().posName);
		assertThat(company.getEmployeeList().get(0).getWorkingDate())
			.isCloseTo(savedComp.getEmployeeList().get(0).getWorkingDate(), new TemporalUnitWithinOffset(1, ChronoUnit.MICROS));
	}
	
	@Test
	public void testDeleteEmployeeFromCompany() throws Exception {
		initDbService.clearDb();
		Employee employee = createEmployee();
		Company company = createCompany(employee);
		
		Long savedCompanyId = companyService.saveCompany(company).getId();
		Long savedEmployeeId = employeeService.saveEmployee(employee).getEmployeeId();
		
		Optional<Employee> savedEmpOpt = employeeService.findById(savedEmployeeId);
		assertThat(savedEmpOpt).isNotEmpty();
		Employee willDeletableEmp = savedEmpOpt.get();
			
		Optional<Company> savedCompanyFirstly = companyService.findByIdWithEmployees(savedCompanyId);
		assertThat(savedCompanyFirstly).isNotEmpty();
		Company savedCompanyOriginal = savedCompanyFirstly.get();
		
		willDeletableEmp.setCompany(null);
		company.getEmployeeList().remove(willDeletableEmp);
		employeeService.saveEmployee(willDeletableEmp);
		
		Optional<Company> savedCompDeletedEmpOpt = companyService.findByIdWithEmployees(savedCompanyId);
		assertThat(savedCompDeletedEmpOpt).isNotEmpty();
		Company savedCompDeletedEmp = savedCompDeletedEmpOpt.get();
		
		assertThat(savedCompDeletedEmp.getEmployeeList()).isEmpty();
		assertThat(savedCompanyOriginal.getEmployeeList()).isNotEmpty();
		assertThat(savedCompDeletedEmp.getEmployeeList()).isNotEqualTo(savedCompanyOriginal.getEmployeeList());
		
	}
	
	@Test
	public void testModifyEmployeeInCompany() throws Exception {
		initDbService.clearDb();
		Employee employee = createEmployee();
		Company company = createCompany(employee);
		
		Long savedCompanyId = companyService.saveCompany(company).getId();
		Long savedEmployeeId = employeeService.saveEmployee(employee).getEmployeeId();
		
		Optional<Company> savedOrigCompQ = companyService.findByIdWithEmployees(savedCompanyId);
		assertThat(savedOrigCompQ).isNotEmpty();
		Company savedOrigComp = savedOrigCompQ.get();
		
		Optional<Employee> savedEmpOptOrig = employeeService.findById(savedEmployeeId);
		assertThat(savedEmpOptOrig).isNotEmpty();
		Employee savedEmpOrig = savedEmpOptOrig.get();
		
		employee.setName("Teszt Aladár");
		employeeService.saveEmployee(employee);
		Optional<Employee> savedModOptEmp = employeeService.findById(savedEmployeeId);
		Employee savedModEmp = savedModOptEmp.get();
		assertThat(savedEmpOrig.getName()).isNotEqualTo(savedModEmp.getName());
		
		Optional<Company> savedCompModOpt = companyService.findByIdWithEmployees(savedCompanyId);
		assertThat(savedCompModOpt).isNotEmpty();
		Company savedCompMod = savedCompModOpt.get();
		assertThat(savedOrigComp.getEmployeeList().get(0).getName())
			.isNotEqualTo(savedCompMod.getEmployeeList().get(0).getName());
	}
	
	
	@Transactional
	public Employee createEmployee() {
		Employee employee = new Employee("Teszt Alajos", 150000, LocalDateTime.now());
		Position position = positionRepository.save(new Position("Tester"));
		employee.setPosition(position);
		return employee;
	}
	
	public Company createCompany(Employee employee) {
		Company company = new Company("TST001", "Teszt cég", "Teszt út 13", null);
		company.addEmployee(employee);
		return company;
	}	
}
