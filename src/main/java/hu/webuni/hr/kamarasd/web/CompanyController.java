package hu.webuni.hr.kamarasd.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.kamarasd.dto.CompanyDto;
import hu.webuni.kamarasd.dto.EmployeeDto;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
	
	private Map<Long, CompanyDto> companies = new HashMap<>();
	
	@GetMapping
	public Collection<CompanyDto> getAll(@RequestParam(defaultValue = "false") String type) {
			if(type != "true") { 
				Map<Object, CompanyDto> companies2 = companies.entrySet().stream().collect(Collectors.toMap(key -> (Long)key.getKey(), value -> {value.getValue().setEmployeeList(null); return (CompanyDto)value;}));
				return companies2.values();
			} else {
				return companies.values();
			}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CompanyDto> getCompany(@PathVariable Long id) {
		CompanyDto companyDto = companies.get(id);
		return (companyDto != null) ?  ResponseEntity.ok(companyDto) : ResponseEntity.notFound().build();
	} 
	
	@PostMapping
	public CompanyDto addCompany(@RequestBody CompanyDto companyDto) {
		companies.put(companyDto.getId(), companyDto);
		return companyDto; 
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CompanyDto> changeCompany(@PathVariable Long id, @RequestBody CompanyDto companyDto) {
		if(!companies.containsKey(id)) {
			return ResponseEntity.notFound().build();
		} else {
			companyDto.setId(id);
			companies.put(id, companyDto);
			return ResponseEntity.ok(companyDto);
		}
		
	}
	
	@DeleteMapping("{id}")
	public void deleteCompany(@PathVariable Long id) {
		companies.remove(id);
	}
	
	@PutMapping("/addEmployee/{id}")
	public ResponseEntity<CompanyDto> addEmployeeToCompany(@PathVariable Long id, @RequestBody EmployeeDto employeeDto) {
		if(!companies.containsKey(id)) {
			return ResponseEntity.notFound().build();
		}
		
		List<EmployeeDto> addEmployee = companies.get(id).getEmployeeList(); 
		addEmployee.add(employeeDto);
		companies.get(id).setEmployeeList(addEmployee);
		return ResponseEntity.ok(companies.get(id));
	}
	
	@DeleteMapping("/deleteEmployee/{id}/{employeeId}")
	public ResponseEntity<CompanyDto> deleteEmployeeFromCompany(@PathVariable Long id, @PathVariable Long employeeId) {
		if(!companies.containsKey(id)) {
			return ResponseEntity.notFound().build();
		}
		
		List<EmployeeDto> modifyEmployee = companies.get(id).getEmployeeList();
		modifyEmployee.remove(employeeId);
		companies.get(id).setEmployeeList(modifyEmployee);
		
		return ResponseEntity.ok(companies.get(id));
	}
	
	@PostMapping("/updateEmployee/{id}")
	public ResponseEntity<CompanyDto> updateEmployeeInCompany(@PathVariable Long id, @RequestBody List<EmployeeDto> employeeDto) {
		if(!companies.containsKey(id)) {
			return ResponseEntity.notFound().build();
		}
		
		companies.get(id).setEmployeeList(employeeDto);
		return ResponseEntity.ok(companies.get(id));
		
	}
}

