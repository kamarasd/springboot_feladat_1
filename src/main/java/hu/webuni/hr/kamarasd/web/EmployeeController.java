package hu.webuni.hr.kamarasd.web;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<EmployeeDto> getById(@PathVariable Long id) {
		
		Employee employee = employeeService.findById(id);
		return (employee != null ) ? ResponseEntity.ok(employeeMapper.employeeToDto(employee)) : ResponseEntity.notFound().build();
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
	public ResponseEntity<Collection<Employee>> salaryLimitCheck(@PathVariable Integer limit) {
		
	Collection<Employee> employee = employeeService.salaryLimitCheck(limit);
	
	return (employee != null) ? ResponseEntity.ok(employee) : ResponseEntity.notFound().build();
	}

}
