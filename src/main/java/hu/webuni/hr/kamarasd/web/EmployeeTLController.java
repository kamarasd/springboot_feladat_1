package hu.webuni.hr.kamarasd.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hu.webuni.hr.kamarasd.model.Employee;

@Controller
public class EmployeeTLController {
	
	private List<Employee> employeeList = new ArrayList<>();
	
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
		long id = (long) employeeList.size();
		employee.setEmployeeId(id);
		employeeList.add(employee);
		return "redirect:employees";
	}
	
	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable int id) {
		employeeList.remove(id);			
		return "redirect:/employees";
	}
	
	@GetMapping("/editEmployee/{id}")
	public String editEmployee(Map<String, Object> model, @PathVariable Long id) {
		model.put("editEmployee", employeeList.stream().filter(e -> e.getEmployeeId().equals(id)).collect(Collectors.toList()));
		return "modifyEmployee";
	}
	
	@PostMapping("/modifyEmployee")
	public String modifyEmployee(Employee employee) {
		for(Employee emp : employeeList) {
			if(emp.getEmployeeId() == employee.getEmployeeId() && emp != null) {
				employeeList.set(employeeList.indexOf(emp), employee);		
			}
		}
		
		return "redirect:/employees";
	}
	
	
}