package hu.webuni.hr.kamarasd.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.webuni.hr.kamarasd.dto.EmployeeDto;
import hu.webuni.hr.kamarasd.model.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
	
	List<EmployeeDto> employeesToDtos(List<Employee> employees);
	
	@Mapping(target = "employeeId", source = "employeeId")
	@Mapping(target = "post", source = "position.posName")
	@Mapping(target = "workingDate", source = "workingDate")
	@Mapping(target = "company.employeeList", ignore = true)
	EmployeeDto employeeToDto(Employee employee);
	
	@InheritInverseConfiguration
	Employee dtoToEmployee(EmployeeDto employeeDto);
	
	List<Employee> dtosToEmployees(List<EmployeeDto> employees);
}
