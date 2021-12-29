package hu.webuni.hr.kamarasd.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import hu.webuni.hr.kamarasd.model.Employee;

@Controller
public class EmployeeTLController {
	
	private List<Employee> employeeList = new ArrayList<>();
	
	{
		employeeList.add(new Employee(1, "Bekő Tóni", "Alkalmazott", 170000, LocalDateTime.parse("2005-05-13T08:00:00")));
	}

	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/employees")
	public String ListEmployees(Map<String, Object> model) {
		model.put("employees", employeeList);
		model.put("newEmployee", new Employee());
		return "employees";	
	}
	
	@PostMapping("/employees")
	public String addEmployee(Employee employee) {
		employeeList.add(employee);
		return "redirect:employees";
	}
	
}
