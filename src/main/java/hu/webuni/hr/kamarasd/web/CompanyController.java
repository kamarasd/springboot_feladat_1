package hu.webuni.hr.kamarasd.web;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.hr.kamarasd.model.Employee;
import hu.webuni.hr.kamarasd.dto.CompanyDto;
import hu.webuni.hr.kamarasd.mapper.CompanyMapper;
import hu.webuni.hr.kamarasd.mapper.EmployeeMapper;
import hu.webuni.hr.kamarasd.model.Company;
import hu.webuni.hr.kamarasd.service.CompanyService;
import hu.webuni.hr.kamarasd.service.EmployeeService;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	CompanyMapper companyMapper;
	
	@Autowired
	EmployeeMapper employeeMapper;
	
	@Autowired
	EmployeeService employeeService;

	
	@GetMapping
	public List<CompanyDto> getAll(@RequestParam(required = false) Boolean type) {
		 return companyMapper.companyToDtos(companyService.findAll(type));
	}
	
	@GetMapping("/{id}")
	public CompanyDto getCompany(@PathVariable Long id) {
		Company company = companyService.findById(id);
		
		if(company != null) {
			return companyMapper.companyToDto(company);
		} else {
			return null;
		}
	} 
	
	@PostMapping
	public CompanyDto addCompany(@RequestBody CompanyDto companyDto) {
		Company company = companyService.saveCompany(companyMapper.dtoToCompany(companyDto));
		return companyMapper.companyToDto(company); 
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CompanyDto> changeCompany(@PathVariable Long id, @RequestBody CompanyDto companyDto) {
		Company company = companyService.findById(id);
		
		if(company == null) {
			return ResponseEntity.notFound().build();
		} else {
			company.setId(id);
			//Save employees to the modified company
			List<Employee> employee = companyService.getEmployeeFromCompany(id);
			company = companyService.saveCompany(companyMapper.dtoToCompany(companyDto));
			companyService.addEmployeeToCompany(id, employee);
			return ResponseEntity.ok(companyMapper.companyToDto(company));
		}
		
	}
	
	@DeleteMapping("{id}")
	public void deleteCompany(@PathVariable Long id) {
		companyService.deleteCompany(id);
	}
	
	@PostMapping("/addEmployee/{id}")
	public ResponseEntity<CompanyDto> addEmployeeToCompany(@PathVariable Long id, @RequestBody @Valid Employee employeeList) {
		Company company = companyService.findById(id);
		if(company == null) {
			return ResponseEntity.notFound().build();
		}

		company = companyService.saveEmployeeToCompany(id, employeeList);
		return ResponseEntity.ok(companyMapper.companyToDto(company));
	}
	
	@DeleteMapping("/deleteEmployee/{id}/{employeeId}")
	public ResponseEntity<CompanyDto> deleteEmployeeFromCompany(@PathVariable Long id, @PathVariable Long employeeId) {
		Company company = companyService.findById(id);
		if(company == null) {
			return ResponseEntity.notFound().build();
		}
		
		companyService.deleteEmployeeFromCompany(id, employeeId);		
		return ResponseEntity.ok(companyMapper.companyToDto(company));
	}
	
	@PostMapping("/updateAllEmployee/{id}")
	public ResponseEntity<CompanyDto> updateEmployeesInCompany(@PathVariable Long id, @RequestBody @Valid List<Employee> employeeList) {
		Company company = companyService.findById(id);
		if(company == null) {
			return ResponseEntity.notFound().build();
		}
	
		company = companyService.changeAllEmployeeInCompany(id, employeeList);
		return ResponseEntity.ok(companyMapper.companyToDto(company));	
	}
	
	@GetMapping("/raiseSalary")
	public int getSalary(@RequestBody Employee employee) {
		return employeeService.getPayRaisePercent(employee);
		
	}
}

