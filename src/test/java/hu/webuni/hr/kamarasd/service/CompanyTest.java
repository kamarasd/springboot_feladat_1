package hu.webuni.hr.kamarasd.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.assertj.core.data.TemporalUnitWithinOffset;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
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
	
	@Test
	public void testAddEmployeeToCompany() throws Exception {
		initDbService.clearDb();
		EmployeeDto employeeDto = createEmployee();
		Company company = createCompany();
		
		CompanyDto returnedCompany = addTestCompany(companyMapper.companyToDto(company));
		CompanyDto returnedEmployee = addTestEmployeeToCompany(employeeDto, returnedCompany.getId());
		
		List<Company> savedCompanyWithEmployee = getTestCompany(returnedCompany.getId());

		assertThat(savedCompanyWithEmployee).isNotEmpty();
		assertThat(savedCompanyWithEmployee.get(0).getEmployeeList().get(0).getName())
			.isEqualTo(employeeDto.getName());
		assertThat(returnedCompany.getCompanyName())
			.isEqualTo(savedCompanyWithEmployee.get(0).getCompanyName());
		assertThat(returnedEmployee.getEmployeeList().get(0).getName())
			.isEqualTo(savedCompanyWithEmployee.get(0).getEmployeeList().get(0).getName());
		assertThat(returnedEmployee.getEmployeeList().get(0).getSalary())
			.isEqualTo(savedCompanyWithEmployee.get(0).getEmployeeList().get(0).getSalary());
		assertThat(savedCompanyWithEmployee.get(0).getEmployeeList().get(0).getWorkingDate())
			.isCloseTo(returnedEmployee.getEmployeeList().get(0).getWorkingDate(), new TemporalUnitWithinOffset(1, ChronoUnit.MICROS));
		
	}
	
	@Test
	public void testDeleteEmployeeFromCompany() throws Exception {
		  initDbService.clearDb(); 
		  EmployeeDto employeeDto = createEmployee(); 
		  Company company = createCompany();
 
		  CompanyDto returnedCompany = addTestCompany(companyMapper.companyToDto(company));
		  CompanyDto returnedEmployee = addTestEmployeeToCompany(employeeDto, returnedCompany.getId());
			
		  List<Company> savedCompanyWithEmployee = getTestCompany(returnedCompany.getId());
		  
		  Long savedEmployeeId = savedCompanyWithEmployee.get(0).getEmployeeList().get(0).getEmployeeId();

		  deleteTestEmployeeInCompany(returnedCompany.getId(), savedEmployeeId);
		  
		  List<Company> savedCompDeletedEmpOpt = getTestCompany(returnedCompany.getId());
		  assertThat(savedCompDeletedEmpOpt)
		  	.isNotEmpty(); 
		  assertThat(savedCompDeletedEmpOpt.get(0).getEmployeeList())
		  	.isEmpty();
		  assertThat(savedCompanyWithEmployee.get(0).getEmployeeList())
		  	.isNotEmpty();
		  assertThat(savedCompDeletedEmpOpt.get(0).getEmployeeList())
		  	.isNotEqualTo(savedCompanyWithEmployee.get(0).getEmployeeList());
	}
	
	@Test
	public void testModifyEmployeeInCompany() throws Exception {
		 initDbService.clearDb(); 
		 EmployeeDto employeeDto = createEmployee(); 
		 Company company = createCompany();
		 
		 CompanyDto returnedCompany = addTestCompany(companyMapper.companyToDto(company));
		 CompanyDto returnedEmployee = addTestEmployeeToCompany(employeeDto, returnedCompany.getId());
			
		 addTestEmployeeToCompany(employeeDto, returnedCompany.getId());
		 List<Company> savedCompanyWithOriginalEmployee = getTestCompany(returnedCompany.getId());

		 returnedEmployee.getEmployeeList().get(0).setName("Teszt Aladár"); 
		 
		 changeTestEmployeeInCompany(returnedEmployee.getEmployeeList(), returnedCompany.getId());
		 List<Company> savedCompanyWithModifiedEmployee = getTestCompany(returnedCompany.getId());

		 assertThat(savedCompanyWithModifiedEmployee)
		 	.isNotEmpty(); 
		 assertThat(savedCompanyWithOriginalEmployee.get(0).getEmployeeList().get(0).getName())
		 	.isNotEqualTo(savedCompanyWithModifiedEmployee.get(0).getEmployeeList().get(0).getName());
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
		.bodyValue(employeeDto)
		.exchange()
		.expectBody(CompanyDto.class)
		.returnResult()
		.getResponseBody();		
	}
	
	public List<Company> getTestCompany(Long companyId) {
		String uri = BASE_URI + "/" + companyId;
		
		return webTestClient
				.get()
				.uri(uri)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBodyList(Company.class)
				.returnResult()
				.getResponseBody();
	}
	
	public void changeTestEmployeeInCompany(List<EmployeeDto> employeeDto, Long companyId) {
		String uri = BASE_URI + "/updateAllEmployee/" + companyId;
		
		webTestClient
			.post()
			.uri(uri)
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
			.exchange()
			.expectStatus()
			.isOk();
		
	}
}
