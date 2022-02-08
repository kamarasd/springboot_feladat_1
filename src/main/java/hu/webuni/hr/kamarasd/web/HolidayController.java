package hu.webuni.hr.kamarasd.web;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import hu.webuni.hr.kamarasd.dto.HolidayDto;
import hu.webuni.hr.kamarasd.mapper.HolidayMapper;
import hu.webuni.hr.kamarasd.model.Approved;
import hu.webuni.hr.kamarasd.model.Company;
import hu.webuni.hr.kamarasd.model.Holiday;
import hu.webuni.hr.kamarasd.model.HolidaySearch;
import hu.webuni.hr.kamarasd.repository.HolidayRepository;
import hu.webuni.hr.kamarasd.model.Employee;
import hu.webuni.hr.kamarasd.service.EmployeeService;
import hu.webuni.hr.kamarasd.service.HolidayService;

@RestController
@RequestMapping("/api/holiday")
public class HolidayController {

	@Autowired
	HolidayService holidayService;
	
	@Autowired
	HolidayMapper holidayMapper;
	
	@Autowired
	EmployeeService employeeService;
	
	@GetMapping
	public List<Holiday> getAll() {
		return holidayService.getAllHolidays();
	}
	
	@GetMapping("/{id}")
	public HolidayDto getById(@PathVariable Long id) {
		Holiday holiday = holidayService.getHolidayById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return holidayMapper.holidayToDto(holiday);
	}
	
	@GetMapping("/pageable/{fromPage}/{tillPage}/{sorting}")
	public List<HolidayDto> getOrderedPageableHoliday(@PathVariable int fromPage, @PathVariable int tillPage, @PathVariable String sorting) {
		sorting = sorting.isEmpty() || sorting == null ? "id" : sorting;
		Pageable page = PageRequest.of(fromPage, tillPage, Sort.by(sorting));
		Page<Holiday> holidayPage = holidayService.findPageableHoliday(page);
		System.out.println(holidayPage.getTotalElements());
		System.out.println(holidayPage.isLast());  
		List<Holiday> holiday = holidayPage.getContent();
		return holidayMapper.holidayToDtos(holiday);
	}
	
	@PostMapping
	public ResponseEntity<Holiday> saveHoliday(@RequestBody Holiday holiday) {
		List<Employee> createdBy = employeeService.findEmployeeByName(holiday.getCreatedBy());
		List<Employee> approver = employeeService.findEmployeeByName(holiday.getSuperior());
		if(createdBy.isEmpty() || approver.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		holidayService.saveHoliday(holiday);
		return ResponseEntity.ok(holiday);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HolidayDto> deleteById(@PathVariable Long id) {
		Holiday holiday = holidayService.getHolidayById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		if(holidayService.HolidayIsPending(holiday.approved)) {
			holidayService.deleteHolidayById(id);
			return ResponseEntity.ok(holidayMapper.holidayToDto(holiday));
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PutMapping("approveHoliday/{id}/{approving}/{approverId}")
	public ResponseEntity<HolidayDto> approveHoliday(@PathVariable Long id, @PathVariable Boolean approving, @PathVariable Long approverId) {
		Holiday holiday = holidayService.getHolidayById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		Employee employee = employeeService.findById(approverId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		if(holiday.getSuperior().equals(employee.getName()) && holiday.getApproved() == Approved.PENDING) {
			Approved approved = holidayService.getApproveType(approving);
			holiday.setApproved(approved);
			return  ResponseEntity.ok(holidayMapper.holidayToDto(holidayService.saveHoliday(holiday)));
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/dataFinder") 
	public ResponseEntity<List<HolidayDto>> findHolidayWithDataFinder(@RequestBody HolidaySearch holidaySearch){
		List<Holiday> holiday = holidayService.findHolidayBySpringDataFinder(holidaySearch);
		
		if(holiday.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(holidayMapper.holidayToDtos(holidayService.findHolidayBySpringDataFinder(holidaySearch)));	
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<HolidayDto> modifyHolidayByCreator(@PathVariable Long id, @RequestBody HolidayDto holidayDto) {
		Holiday holiday = holidayService.getHolidayById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		if(holidayService.HolidayIsPending(holiday.approved)) {
			holidayDto.setId(id);
			System.out.println(holidayDto.getHolidayEnd());
			holidayService.saveHoliday(holidayMapper.dtoToHoliday(holidayDto));
			return ResponseEntity.ok(holidayDto);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	
	
	
}
