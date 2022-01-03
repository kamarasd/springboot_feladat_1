package hu.webuni.hr.kamarasd.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import hu.webuni.hr.kamarasd.dto.EmployeeDto;
import hu.webuni.hr.kamarasd.model.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
	
	List<EmployeeDto> employeeToDtos(List<Employee> employee);
	
	EmployeeDto employeeToDto(Employee employee);
	
	Employee dtoToEmployee(EmployeeDto employeeDto);
}
