package hu.webuni.hr.kamarasd.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import hu.webuni.hr.kamarasd.dto.CompanyDto;
import hu.webuni.hr.kamarasd.dto.EmployeeDto;
import hu.webuni.hr.kamarasd.model.Company;
import hu.webuni.hr.kamarasd.model.Employee;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
	
	List<CompanyDto> companiesToDtos(List<Company> company);
	
	@IterableMapping(qualifiedByName = "summary")
	List<CompanyDto> companiesToSummaryDtos(List<Company> companies);
	
	@Mapping(target = "employeeList", ignore = true)
	@Named("summary")
	CompanyDto companyToSummaryDtos(Company company);
	
	CompanyDto companyToDto(Company company);

	Company dtoToCompany(CompanyDto companyDto);
	
	List<Company> dtosToCompanies(List<CompanyDto> companyDto); 
	
	@Mapping(target = "employeeId", source = "employeeId")
	@Mapping(target = "post", source = "position.posName")
	@Mapping(target = "workingDate", source = "workingDate")
	@Mapping(target = "company", ignore = true)
	EmployeeDto employeeToDto(Employee employee);
	
	@InheritInverseConfiguration
	Employee dtoToEmployee(EmployeeDto employeeDto);

}
