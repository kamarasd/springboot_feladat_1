package hu.webuni.hr.kamarasd.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.assertj.core.data.TemporalUnitWithinOffset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;

import hu.webuni.hr.kamarasd.model.Employee;
import hu.webuni.hr.kamarasd.model.Position;
import hu.webuni.hr.kamarasd.repository.CompanyRepository;
import hu.webuni.hr.kamarasd.repository.EmployeeRepository;
import hu.webuni.hr.kamarasd.repository.PositionRepository;
import hu.webuni.hr.kamarasd.dto.CompanyDto;
import hu.webuni.hr.kamarasd.dto.EmployeeDto;
import hu.webuni.hr.kamarasd.mapper.CompanyMapper;
import hu.webuni.hr.kamarasd.mapper.EmployeeMapper;
import hu.webuni.hr.kamarasd.model.Company;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CompanyTest {
	
	private static final String BASE_URI = "api/companies";
	
	@Autowired
	WebTestClient webTestClient;
	
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
	CompanyMapper companyMapper;
	
	@Autowired
	InitDbService initDbService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	private String username = "testuser";
	private String password = "password";
	
	@BeforeEach
	public void init () {
		if(employeeRepository.findByUsername(username).isEmpty()) {
			Employee employee = new Employee();
			employee.setName("Teszt Ember");
			employee.setUsername(username);
			employee.setWorkingDate("2022-01-01T08:00:00");
			employee.setPassword(passwordEncoder.encode(password));
			employeeRepository.save(employee);
		}
	}
	
	@Test
	public void testAddEmployeeToCompany() throws Exception {
		EmployeeDto employeeDto = createEmployee();
		Company company = createCompany();
		
		CompanyDto returnedCompany = addTestCompany(companyMapper.companyToDto(company));
		CompanyDto returnedEmployee = addTestEmployeeToCompany(employeeDto, returnedCompany.getId());
		
		CompanyDto savedCompanyWithEmployee = getTestCompany(returnedCompany.getId());

		assertThat(savedCompanyWithEmployee).isNotNull();
		assertThat(savedCompanyWithEmployee.getEmployeeList().get(0).getName())
			.isEqualTo(employeeDto.getName());
		assertThat(returnedCompany.getCompanyName())
			.isEqualTo(savedCompanyWithEmployee.getCompanyName());
		assertThat(returnedEmployee.getEmployeeList().get(0).getName())
			.isEqualTo(savedCompanyWithEmployee.getEmployeeList().get(0).getName());
		assertThat(returnedEmployee.getEmployeeList().get(0).getPost())
			.isEqualTo(savedCompanyWithEmployee.getEmployeeList().get(0).getPost());
		assertThat(returnedEmployee.getEmployeeList().get(0).getSalary())
			.isEqualTo(savedCompanyWithEmployee.getEmployeeList().get(0).getSalary());
		assertThat(savedCompanyWithEmployee.getEmployeeList().get(0).getWorkingDate())
			.isCloseTo(returnedEmployee.getEmployeeList().get(0).getWorkingDate(), new TemporalUnitWithinOffset(1, ChronoUnit.MICROS));
		
	}
	
	@Test
	public void testDeleteEmployeeFromCompany() throws Exception {
		  EmployeeDto employeeDto = createEmployee(); 
		  Company company = createCompany();
 
		  CompanyDto returnedCompany = addTestCompany(companyMapper.companyToDto(company));
		  CompanyDto returnedEmployee = addTestEmployeeToCompany(employeeDto, returnedCompany.getId());
			
		  CompanyDto savedCompanyWithEmployee = getTestCompany(returnedCompany.getId());
		  
		  Long savedEmployeeId = savedCompanyWithEmployee.getEmployeeList().get(0).getEmployeeId();

		  deleteTestEmployeeInCompany(returnedCompany.getId(), savedEmployeeId);
		  
		  CompanyDto savedCompDeletedEmpOpt = getTestCompany(returnedCompany.getId());
		  
		  assertThat(savedCompDeletedEmpOpt.getEmployeeList())
		  	.isEmpty();
		  assertThat(savedCompanyWithEmployee.getEmployeeList())
		  	.isNotEmpty();
		  assertThat(savedCompDeletedEmpOpt.getEmployeeList())
		  	.isNotEqualTo(savedCompanyWithEmployee.getEmployeeList());
	}
	
	@Test
	public void testModifyEmployeeInCompany() throws Exception {
		 EmployeeDto employeeDto = createEmployee(); 
		 Company company = createCompany();
		 
		 CompanyDto returnedCompany = addTestCompany(companyMapper.companyToDto(company));
		 CompanyDto returnedEmployee = addTestEmployeeToCompany(employeeDto, returnedCompany.getId());
			
		 addTestEmployeeToCompany(employeeDto, returnedCompany.getId());
		 CompanyDto savedCompanyWithOriginalEmployee = getTestCompany(returnedCompany.getId());

		 returnedEmployee.getEmployeeList().get(0).setName("Teszt Aladár"); 
		 returnedEmployee.getEmployeeList().get(0).setPost("Developer"); 
		 
		 changeTestEmployeeInCompany(returnedEmployee.getEmployeeList(), returnedCompany.getId());
		 CompanyDto savedCompanyWithModifiedEmployee = getTestCompany(returnedCompany.getId());

		 assertThat(savedCompanyWithModifiedEmployee.getEmployeeList())
		 	.isNotEmpty();
		 assertThat(savedCompanyWithOriginalEmployee.getEmployeeList().get(0).getName())
		 	.isNotEqualTo(savedCompanyWithModifiedEmployee.getEmployeeList().get(0).getName());
		 assertThat(savedCompanyWithOriginalEmployee.getEmployeeList().get(0).getPost())
		 	.isNotEqualTo(savedCompanyWithModifiedEmployee.getEmployeeList().get(0).getPost());
	}
	
	public EmployeeDto createEmployee() {
		EmployeeDto employeeDto = new EmployeeDto(1L, "Teszt Alajos", "Tester",  150000, LocalDateTime.now());
		return employeeDto;
	}
	
	public Company createCompany() {
		Company company = new Company("TST001", "Teszt cég", "Teszt út 13", null);
		return company;
	}	
	
	public CompanyDto addTestCompany(CompanyDto companyDto) {
		 return webTestClient
			.post()
			.uri(BASE_URI)
			.headers(headers -> headers.setBasicAuth(username, password))
			.bodyValue(companyDto)
			.exchange()
			.expectBody(CompanyDto.class)
			.returnResult()
			.getResponseBody();	
	}
	
	public CompanyDto addTestEmployeeToCompany(EmployeeDto employeeDto, Long companyId) {
		String url = BASE_URI + "/addEmployee/" + companyId;
		
		return webTestClient
			.post()
			.uri(url)
			.headers(headers -> headers.setBasicAuth(username, password))
			.bodyValue(employeeDto)
			.exchange()
			.expectBody(CompanyDto.class)
			.returnResult()
			.getResponseBody();		
	}
	
	public CompanyDto getTestCompany(Long companyId) {
		String uri = BASE_URI + "/" + companyId;
		
		return webTestClient
			.get()
			.uri(uri)
			.headers(headers -> headers.setBasicAuth(username, password))
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody(CompanyDto.class)
			.returnResult()
			.getResponseBody();
	}
	
	public void changeTestEmployeeInCompany(List<EmployeeDto> employeeDto, Long companyId) {
		String uri = BASE_URI + "/updateAllEmployee/" + companyId;
		
		webTestClient
			.post()
			.uri(uri)
			.headers(headers -> headers.setBasicAuth(username, password))
			.bodyValue(employeeDto)
			.exchange()
			.expectStatus()
			.isOk();

	}
	
	public void deleteTestEmployeeInCompany(Long companyId, Long employeeId) {
		String uri = BASE_URI + "/deleteEmployee/" + companyId + "/" + employeeId;
		
		webTestClient
			.delete()
			.uri(uri)
			.headers(headers -> headers.setBasicAuth(username, password))
			.exchange()
			.expectStatus()
			.isOk();
		
	}
}
