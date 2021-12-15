package hu.webuni.hr.kamarasd.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.kamarasd.dto.EmployeeDto;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	
	private Map<Long, EmployeeDto> employees = new HashMap<>();
	
	{
		employees.put(1L, new EmployeeDto(1, "Bekő Tóni", "Alkalmazott", 200000, LocalDateTime.parse("2005-01-01T08:00:00")));
		employees.put(2L, new EmployeeDto(2, "Matr Ica", "Alkalmazott", 150000, LocalDateTime.parse("2004-05-12T08:00:00")));
	}

	@GetMapping
	public List<EmployeeDto> getAll() {
		return new ArrayList<>(employees.values());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EmployeeDto> getById(@PathVariable Long id) {
		EmployeeDto employeeDto = employees.get(id);
		return (employeeDto != null ) ? ResponseEntity.ok(employeeDto) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public EmployeeDto createEmployee(@RequestBody EmployeeDto employeeDto) {
		employees.put(employeeDto.getEmployeeId(), employeeDto);
		return employeeDto;
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EmployeeDto> changeEmployee(@PathVariable Long id,@RequestBody EmployeeDto employeeDto) {
		if(!employees.containsKey(id)) {
			return ResponseEntity.notFound().build();
		}
		
		employeeDto.setEmployeeId(id);
		employees.put(id, employeeDto);
		return ResponseEntity.ok(employeeDto);
	}
	
	@DeleteMapping("/{id}")
	public void deleteEmployee(@PathVariable Long id) {
		employees.remove(id);
	}
	
	@GetMapping("/salaryLimit/{limit}")
	public ResponseEntity<Collection<EmployeeDto>> salaryLimitCheck(@PathVariable Integer limit) {
	
	Collection<EmployeeDto> employeeDto = employees.entrySet().stream().
			filter(employee -> employee.getValue().getSalary() > limit).
			collect(Collectors.toMap(employee -> employee.getKey(),employee -> employee.getValue())).values();
	
	return (!employeeDto.isEmpty()) ? ResponseEntity.ok(employeeDto) : ResponseEntity.notFound().build();
	}

}
