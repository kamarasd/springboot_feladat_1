package hu.webuni.hr.kamarasd.service;


import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;

import hu.webuni.hr.kamarasd.dto.EmployeeDto;
import hu.webuni.hr.kamarasd.model.Employee;
import hu.webuni.hr.kamarasd.model.Position;
import hu.webuni.hr.kamarasd.repository.EmployeeRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HrTest {
	
	private static final String BASE_URI = "api/employees";
	
	private String username = "testuser";
	private String password = "password";
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
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
	
	@Autowired
	WebTestClient webTestClient;
	
	@Test
	public void listEmployeesTest() throws Exception {
		
		EmployeeDto employee = new EmployeeDto(13, "Teszt Elemér", "Tesztelő", 150000, LocalDateTime.parse("2022-01-04T17:00:00"));
		employee.setPassword(password);
		
		List<EmployeeDto> employeeDtoBefore = getEmployees();
		addEmployee(employee);
		List<EmployeeDto> employeeDtoAfter = getEmployees();
		
		assertThat(!employeeDtoBefore.contains(employee));
		assertThat(employeeDtoAfter.contains(employee));
	}
	
	@Test
	public void changeEmployeeTest() throws Exception {
		EmployeeDto employee = new EmployeeDto(1, "Teszt Elek", "Tesztelő", 150000, LocalDateTime.parse("2022-01-04T17:00:00"));	
		employee.setPassword(password);
		List<EmployeeDto> employeeDtoBefore = getEmployees();
		EmployeeDto modifiedEmployeeDto = changeEmployee(employee, employeeDtoBefore.get(0).getEmployeeId());
		List<EmployeeDto> employeeDtoAfter = getEmployees();
		
		assertThat(modifiedEmployeeDto.equals(employee));
		assertThat(!employeeDtoBefore.contains(employee));
		assertThat(employeeDtoAfter.contains(employee));
	}
	
	@Test
	public void deleteEmployeeTest() throws Exception {
		EmployeeDto employee = new EmployeeDto(13, "Teszt Elemér", "Tesztelő", 150000, LocalDateTime.parse("2022-01-04T17:00:00"));
		employee.setPassword(password);
		List<EmployeeDto> employeeDtoBefore = getEmployees();
		deleteEmployee(employee, employeeDtoBefore.get(0).getEmployeeId());
		List<EmployeeDto> employeeDtoAfter = getEmployees();
		
		assertThat(employeeDtoAfter.contains(employee));
		assertThat(!employeeDtoBefore.contains(employee));
		
	}
	
	@Test
	public void addBadEmployeeTest() throws Exception {
		EmployeeDto employee = new EmployeeDto(12, "", "Tesztelő", 100000, LocalDateTime.parse("2022-01-04T17:00:00"));

		List<EmployeeDto> employeeDtoBefore = getEmployees();
		addBadEmployee(employee);
		List<EmployeeDto> employeeDtoAfter = getEmployees();
		
		assertThat(!employeeDtoBefore.contains(employee));
		assertThat(!employeeDtoAfter.contains(employee));
	}
	
	
	private EmployeeDto changeEmployee(EmployeeDto employeeDto, Long id) {
		String uri = BASE_URI + "/" + id;
		
		return webTestClient
				.put()
				.uri(uri)
				.headers(headers -> headers.setBasicAuth(username, password))
				.bodyValue(employeeDto)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody(EmployeeDto.class)
				.returnResult()
				.getResponseBody();
	}
	
	private List<EmployeeDto> getEmployees() {
			
			return webTestClient
						.get()
						.uri(BASE_URI)
						.headers(headers -> headers.setBasicAuth(username, password))
						.exchange()
						.expectStatus()
						.isOk()
						.expectBodyList(EmployeeDto.class)
						.returnResult()
						.getResponseBody();
		}
	
	private void addEmployee(EmployeeDto employeeDto) {
		
		webTestClient
			.post()
			.uri(BASE_URI)
			.headers(headers -> headers.setBasicAuth(username, password))
			.bodyValue(employeeDto)
			.exchange()
			.expectStatus()
			.isOk();	
		
	}
	
	private void deleteEmployee(EmployeeDto employeeDto, Long id ) {
		
		String uri = BASE_URI + "/" + id;
		
		webTestClient
				.delete()
				.uri(uri)
				.headers(headers -> headers.setBasicAuth(username, password))
				.exchange()
				.expectStatus()
				.isOk();
	}
	
	private void addBadEmployee(EmployeeDto employeeDto) {
		
		webTestClient
			.post()
			.uri(BASE_URI)
			.headers(headers -> headers.setBasicAuth(username, password))
			.bodyValue(employeeDto)
			.exchange()
			.expectStatus()
			.isBadRequest();
	}

}
