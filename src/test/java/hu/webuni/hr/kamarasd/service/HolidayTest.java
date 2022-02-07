package hu.webuni.hr.kamarasd.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.assertj.core.data.TemporalUnitWithinOffset;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import hu.webuni.hr.kamarasd.dto.CompanyDto;
import hu.webuni.hr.kamarasd.model.Approved;
import hu.webuni.hr.kamarasd.model.Employee;
import hu.webuni.hr.kamarasd.model.Holiday;
import hu.webuni.hr.kamarasd.repository.EmployeeRepository;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HolidayTest {
	
	private static final String BASE_URI = "api/holiday";
	
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
	
	@Test
	public void testAddHoliday() throws Exception {
		initDbService.clearDb();
		initDbService.insertTestData();
		Holiday holiday = createNewTestHoliday();
		Holiday returnedHoliday = addTestHoliday(holiday);
		
		assertThat(returnedHoliday.getApproved()).isEqualTo(holiday.getApproved());
		assertThat(returnedHoliday.getCreatedBy()).isEqualTo(holiday.getCreatedBy());
		assertThat(returnedHoliday.getHolidayStart()).isEqualTo(holiday.getHolidayStart());
		assertThat(returnedHoliday.getHolidayEnd()).isEqualTo(holiday.getHolidayEnd());
		assertThat(returnedHoliday.getSuperior()).isEqualTo(holiday.getSuperior());
	}
	
	@Test
	public void testChangeApproved() throws Exception {
		initDbService.clearDb();
		initDbService.insertTestData();
		Holiday holiday = createNewTestHoliday();
		Holiday returnedHoliday = addTestHoliday(holiday);
		
		List<Employee> employee = employeeService.findEmployeeByName(holiday.getSuperior());
		
		Holiday approveChanged = changeApprove(returnedHoliday.getId(), true, employee.get(0).getEmployeeId());
		assertThat(returnedHoliday.getApproved()).isNotEqualTo(approveChanged.getApproved());
	}
	
	public Holiday createNewTestHoliday() {
		String creator = getHolidayEmployee();
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
	
	public Holiday addTestHoliday(Holiday holiday) {
		return webTestClient
				.post()
				.uri(BASE_URI)
				.bodyValue(holiday)
				.exchange()
				.expectBody(Holiday.class)
				.returnResult()
				.getResponseBody();	
	}
	
	public Holiday changeApprove(Long holidayId, Boolean approve, Long approverId) {
		String uri = BASE_URI + "/approveHoliday/" + holidayId + "/" + approve + "/" + approverId;
		
		return webTestClient
				.put()
				.uri(uri)
				.exchange()
				.expectBody(Holiday.class)
				.returnResult()
				.getResponseBody();	
	}
	
	

}
