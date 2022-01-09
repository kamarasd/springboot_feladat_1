package hu.webuni.hr.kamarasd.service;


import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import hu.webuni.hr.kamarasd.dto.EmployeeDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HrTest {
	
	private static final String BASE_URI = "api/employees";
	
	@Autowired
	WebTestClient webTestClient;
	
	@Test
	public void listEmployeesTest() throws Exception {
		
		EmployeeDto employee = new EmployeeDto(13, "Teszt Elemér", "Tesztelő", 150000, LocalDateTime.parse("2022-01-04T17:00:00"));
		
		
		List<EmployeeDto> employeeDtoBefore = getEmployees();
		addEmployee(employee);
		List<EmployeeDto> employeeDtoAfter = getEmployees();
		
		assertThat(!employeeDtoBefore.contains(employee));
		assertThat(employeeDtoAfter.contains(employee));
	}
	
	@Test
	public void changeEmployeeTest() throws Exception {
		EmployeeDto employee = new EmployeeDto(1, "Teszt Elek", "Tesztelő", 150000, LocalDateTime.parse("2022-01-04T17:00:00"));
				
				
		List<EmployeeDto> employeeDtoBefore = getEmployees();
		EmployeeDto modifiedEmployeeDto = changeEmployee(employee);
		List<EmployeeDto> employeeDtoAfter = getEmployees();
		
		assertThat(modifiedEmployeeDto.equals(employee));
		assertThat(!employeeDtoBefore.contains(employee));
		assertThat(employeeDtoAfter.contains(employee));
	}
	
	@Test
	public void deleteEmployeeTest() throws Exception {
		EmployeeDto employee = new EmployeeDto(13, "Teszt Elemér", "Tesztelő", 150000, LocalDateTime.parse("2022-01-04T17:00:00"));
		
		List<EmployeeDto> employeeDtoBefore = getEmployees();
		deleteEmployee(employee);
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
	
	
	private EmployeeDto changeEmployee(EmployeeDto employeeDto) {
		String uri = BASE_URI + "/" + employeeDto.getEmployeeId();
		
		return webTestClient
				.put()
				.uri(uri)
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
			.bodyValue(employeeDto)
			.exchange()
			.expectStatus()
			.isOk();	
		
	}
	
	private void deleteEmployee(EmployeeDto employeeDto) {
		
		String uri = BASE_URI + "/" + employeeDto.getEmployeeId();
		
		webTestClient
				.delete()
				.uri(uri)
				.exchange()
				.expectStatus()
				.isOk();
	}
	
	private void addBadEmployee(EmployeeDto employeeDto) {
		
		webTestClient
			.post()
			.uri(BASE_URI)
			.bodyValue(employeeDto)
			.exchange()
			.expectStatus()
			.isBadRequest();
	}

}
