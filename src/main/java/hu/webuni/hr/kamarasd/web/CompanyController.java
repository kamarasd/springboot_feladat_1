package hu.webuni.hr.kamarasd.web;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.hr.kamarasd.model.Employee;
import hu.webuni.hr.kamarasd.repository.CompanyRepository;
import hu.webuni.hr.kamarasd.dto.CompanyDto;
import hu.webuni.hr.kamarasd.dto.EmployeeDto;
import hu.webuni.hr.kamarasd.mapper.CompanyMapper;
import hu.webuni.hr.kamarasd.mapper.EmployeeMapper;
import hu.webuni.hr.kamarasd.model.AverageSalary;
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
	
	@Autowired
	CompanyRepository companyRepository;

	
	@GetMapping
	public List<CompanyDto> getAll(@RequestParam(required = false) Boolean type) {
		 List<Company> company = null;
		 
		 if(type == true) {
			 company = companyService.findAllWithEmployees();
			 return companyMapper.companiesToDtos(company);
		 } else {
			 company = companyService.findAll();
			 return companyMapper.companiesToSummaryDtos(company);
		 }
		 
	}
	
	@GetMapping("/{id}")
	public CompanyDto getCompany(@PathVariable Long id) {
		Company company = companyService.findByIdWithEmployees(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return companyMapper.companyToDto(company);
		
	} 
	
	@PostMapping
	public CompanyDto addCompany(@RequestBody CompanyDto companyDto) {
		Company company = companyService.saveCompany(companyMapper.dtoToCompany(companyDto));
		return companyMapper.companyToDto(company); 
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CompanyDto> changeCompany(@PathVariable Long id, @RequestBody CompanyDto companyDto) {
		if(companyService.findById(id) == null) {
			return ResponseEntity.notFound().build();
		} else {
			//Save employees to the modified company
			//List<Employee> employee = companyService.getEmployeeFromCompany(id);
			Company company = companyService.saveCompany(companyMapper.dtoToCompany(companyDto));
			//companyService.saveEmployeeListToCompany(id, employee);
			return ResponseEntity.ok(companyMapper.companyToDto(company));
		}
		
	}
	
	@DeleteMapping("/{id}")
	public void deleteCompany(@PathVariable Long id) {
		companyService.deleteCompany(id);
	}
	
	@PostMapping("/addEmployee/{id}")
	public CompanyDto addEmployeeToCompany(@PathVariable Long id, @RequestBody @Valid EmployeeDto employeeDto) {
		try {
			return companyMapper
					.companyToDto(companyService.addEmployeeToCompany(id, employeeMapper.dtoToEmployee(employeeDto)));
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/deleteEmployee/{id}/{employeeId}")
	public ResponseEntity<CompanyDto> deleteEmployeeFromCompany(@PathVariable Long id, @PathVariable Long employeeId) {
		if(companyService.findById(id) == null) {
			return ResponseEntity.notFound().build();
		}
		
		Company company = companyService.deleteEmployeeFromCompany(id, employeeId);		
		return ResponseEntity.ok(companyMapper.companyToDto(company));
	}
	
	@PostMapping("/updateAllEmployee/{id}")
	public ResponseEntity<CompanyDto> updateEmployeesInCompany(@PathVariable Long id, @RequestBody List<EmployeeDto> employeeList) {
		if(companyService.findById(id) == null) {
			return ResponseEntity.notFound().build();
		}
	
		Company company = companyService.changeAllEmployeeInCompany(id, employeeMapper.dtosToEmployees(employeeList));
		return ResponseEntity.ok(companyMapper.companyToDto(company));	
	}
	
	@GetMapping("/raiseSalary")
	public int getSalary(@RequestBody Employee employee) {
		return companyService.getPayRaisePercent(employee);
		
	}
	
	@GetMapping("/getSalaryLimit/{limit}/{pageable}")
	public List<CompanyDto> getCompaniesBySalaryLimit(@PathVariable int limit, @SortDefault("id") Pageable pageable) {
		Page<Company> companyPage = companyRepository.findByCompanyWhereSalaryGraterThan(pageable, limit);
		System.out.println(companyPage.getTotalElements());
		System.out.println(companyPage.isLast());
		List<Company> companies = companyPage.getContent();
		return companyMapper.companiesToSummaryDtos(companies);
	}
	
	@GetMapping("/countEmployee/{limit}")
	public List<CompanyDto> countEmployeesInCompanyByLimit(@PathVariable int limit) {
		List<Company> company = companyRepository.getCompanyWhereEmployeesMoreThan(limit);
		
		return companyMapper.companiesToDtos(company);
	}
	
	@GetMapping("/getCompanyAvgSalary/{id}")
	public List<AverageSalary> countEmployeesInCompanyByLimit(@PathVariable long id) {
		return companyService.getAvarageSalaryByPosition(id);		
	}
}

