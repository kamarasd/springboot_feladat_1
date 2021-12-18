package hu.webuni.hr.kamarasd.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Collector;

import org.springframework.beans.factory.annotation.Autowired;
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

import hu.webuni.hr.kamarasd.Employee;
import hu.webuni.hr.kamarasd.service.EmployeeService;
import hu.webuni.kamarasd.dto.CompanyDto;
import hu.webuni.kamarasd.dto.EmployeeDto;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
	
	private Map<Long, CompanyDto> companies = new HashMap<>();
	
	@Autowired
	EmployeeService employeeService;

	
	@GetMapping
	public List<CompanyDto> getAll(@RequestParam(required = false) Boolean type) {
			if(type == true && type != null) { 
				return companies
						.values()
						.stream()
						.map(comp -> new CompanyDto(comp.getId(), comp.getCompanyNo(), comp.getCompanyName(), comp.getCompanyAddress(), null))
						.collect(Collectors.toList());
			} else {
				return new ArrayList<>(companies.values());	
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
			//Save employees to the modified company
			List<EmployeeDto> saveEmployees = companies.get(id).getEmployeeList();
			companies.put(id, companyDto);
			companies.get(id).setEmployeeList(saveEmployees);
			return ResponseEntity.ok(companyDto);
		}
		
	}
	
	@DeleteMapping("{id}")
	public void deleteCompany(@PathVariable Long id) {
		companies.remove(id);
	}
	
	@PostMapping("/addEmployee/{id}")
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
		
		CompanyDto company = companies.get(id);
		company.getEmployeeList().removeIf(employee -> employee.getEmployeeId() == employeeId);
		
		return ResponseEntity.ok(company);
	}
	
	@PutMapping("/updateEmployee/{id}")
	public ResponseEntity<CompanyDto> updateEmployeeInCompany(@PathVariable Long id, @RequestBody List<EmployeeDto> employeeDto) {
		if(!companies.containsKey(id)) {
			return ResponseEntity.notFound().build();
		}
		
		companies.get(id).setEmployeeList(employeeDto);
		return ResponseEntity.ok(companies.get(id));	
	}
	
	@GetMapping("/raiseSalary")
	public int getSalary(@RequestBody Employee employee) {
		return employeeService.getPayRaisePercent(employee);
		
	}
}

