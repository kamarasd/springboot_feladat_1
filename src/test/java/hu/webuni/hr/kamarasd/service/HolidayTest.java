package hu.webuni.hr.kamarasd.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.assertj.core.data.TemporalUnitWithinOffset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;

import hu.webuni.hr.kamarasd.dto.LoginDto;
import hu.webuni.hr.kamarasd.model.Approved;
import hu.webuni.hr.kamarasd.model.Employee;
import hu.webuni.hr.kamarasd.model.Holiday;
import hu.webuni.hr.kamarasd.model.Position;
import hu.webuni.hr.kamarasd.repository.EmployeeRepository;
import hu.webuni.hr.kamarasd.repository.PositionRepository;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HolidayTest {
	
	private static final String BASE_URI = "api/holiday";
	private static final String LOGIN_URI = "api/login";
	
	private String username = "testuser";
	private String password = "password";
	
	@Autowired
	HolidayService holidayService;
	
	@Autowired
	EmployeeServiceClass employeeService;
	
	@Autowired
	InitDbService initDbService;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	WebTestClient webTestClient;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	PositionRepository positionRepository;
	
	@BeforeEach
	public void init () {
		if(employeeRepository.findByUsername(username).isEmpty()) {
			Position position = positionRepository.save(new Position("Teszter"));
			
			Employee employee = new Employee();
			employee.setName("Teszt Ember");
			employee.setUsername(username);
			employee.setWorkingDate("2022-01-01T08:00:00");
			employee.setPassword(passwordEncoder.encode(password));
			employee.setPosition(position);
			employeeRepository.save(employee);
		}
	}
	
	@Test
	public void testAddHoliday() throws Exception {
		LoginDto login = new LoginDto(username, password);
		String token = getToken(login);
		
		Holiday holiday = createNewTestHoliday();
		Holiday returnedHoliday = addTestHoliday(holiday, token);
		
		assertThat(returnedHoliday.getApproved()).isEqualTo(holiday.getApproved());
		assertThat(returnedHoliday.getCreatedBy()).isEqualTo(holiday.getCreatedBy());
		assertThat(returnedHoliday.getHolidayStart()).isEqualTo(holiday.getHolidayStart());
		assertThat(returnedHoliday.getHolidayEnd()).isEqualTo(holiday.getHolidayEnd());
		assertThat(returnedHoliday.getSuperior()).isEqualTo(holiday.getSuperior());
	}
	
	@Test
	public void testChangeApproved() throws Exception {
		LoginDto login = new LoginDto(username, password);
		String token = getToken(login);
		
		Holiday holiday = createNewTestHoliday();
		Holiday returnedHoliday = addTestHoliday(holiday, token);
		
		List<Employee> employee = employeeService.findEmployeeByName(holiday.getSuperior());

		Holiday approveChanged = changeApprove(returnedHoliday.getId(), true, employee.get(0).getEmployeeId(), token);
		assertThat(returnedHoliday.getApproved()).isNotEqualTo(approveChanged.getApproved());

	}
	
	
	public Holiday createNewTestHoliday() {
		String creator = "Teszt Ember";
		String boss = getHolidayEmployee();
		Holiday holiday = new Holiday(LocalDate.now().plusDays(1), LocalDate.now().plusDays(10), Approved.PENDING, creator, boss);
		return holiday;
	}
	
	public String getHolidayEmployee() {
		List<Employee> employee = employeeService.getAll();
		int size = employee.size();
		Random r = new Random();
		int rand = r.nextInt(size);
		return employee.get(rand).getName();
	
	}
	
	public Holiday addTestHoliday(Holiday holiday, String token) {

		return webTestClient
				.post()
				.uri(BASE_URI)
				.headers(headers -> headers.setBearerAuth(token))
				.bodyValue(holiday)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody(Holiday.class)
				.returnResult()
				.getResponseBody();	
	}
	
	public Holiday changeApprove(Long holidayId, Boolean approve, Long approverId, String token) {
		String uri = BASE_URI + "/approveHoliday/" + holidayId + "/" + approve + "/" + approverId;
		
		return webTestClient
				.get()
				.uri(uri)
				.headers(headers -> headers.setBearerAuth(token))
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody(Holiday.class)
				.returnResult()
				.getResponseBody();	
	}
	
	public String getToken(LoginDto loginDto) {
			
			return webTestClient
				.post()
				.uri(LOGIN_URI)
				.bodyValue(loginDto)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody(String.class)
				.returnResult()
				.getResponseBody();
		}

}
