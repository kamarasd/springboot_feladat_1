package hu.webuni.hr.kamarasd.web;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import hu.webuni.hr.kamarasd.model.Employee;
import hu.webuni.hr.kamarasd.dto.EmployeeDto;
import hu.webuni.hr.kamarasd.mapper.EmployeeMapper;
import hu.webuni.hr.kamarasd.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;	
	
	@Autowired
	EmployeeMapper employeeMapper;

	
	@GetMapping
	public List<EmployeeDto> getAll() {
		return employeeMapper.employeeToDtos(employeeService.getAll());
	}
	
	@GetMapping("/{id}")
	public EmployeeDto getById(@PathVariable Long id) {
		
		Employee employee = employeeService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return employeeMapper.employeeToDto(employee);
	}
	
	@PostMapping
	public EmployeeDto createEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
		Employee employee = employeeService.saveEmployee(employeeMapper.dtoToEmployee(employeeDto));
		return employeeMapper.employeeToDto(employee);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EmployeeDto> changeEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeDto employeeDto) {
		if(employeeService.findById(id) == null) {
			return ResponseEntity.notFound().build();
		}
		
		Employee employee = employeeService.changeEmployee(id, employeeMapper.dtoToEmployee(employeeDto));
		return ResponseEntity.ok(employeeMapper.employeeToDto(employee));
	}
	
	@DeleteMapping("/{id}")
	public void deleteEmployee(@PathVariable Long id) {
		employeeService.deleteEmployee(id);
	}
	
	 @GetMapping("/salaryLimit/{limit}") 
	 public List<EmployeeDto> salaryLimitCheck(@PathVariable Integer limit) {
		 return employeeMapper.employeeToDtos(employeeService.salaryLimitCheck(limit));
	 }
	 
	 @GetMapping("/findByPost/{post}") 
	 public List<EmployeeDto> findEmployeeByPost (@PathVariable String post) {
		 return employeeMapper.employeeToDtos(employeeService.findEmployeeByPost(post));
	 }
	 
	 @GetMapping("/findByName/{employeeName}")
	 public List<EmployeeDto> findEmployeeByName (@PathVariable String employeeName) {
		 return employeeMapper.employeeToDtos(employeeService.findEmployeeByName(employeeName));
	 }
	 
	 @GetMapping("/dateBetween/{dateFrom}/{dateTo}")
	 public List<EmployeeDto> findEmployeeBetweenDate(@PathVariable String dateFrom, @PathVariable String dateTo) {
		 return employeeMapper.employeeToDtos(employeeService.findEmployeeBetweenDate(LocalDateTime.parse(dateFrom), LocalDateTime.parse(dateTo)));
	 }
	 

}
