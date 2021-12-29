package hu.webuni.hr.kamarasd.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import hu.webuni.hr.kamarasd.dto.CompanyDto;
import hu.webuni.hr.kamarasd.model.Company;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
	
	List<CompanyDto> companyToDtos(List<Company> company);

	CompanyDto companyToDto(Company company);

	Company dtoToCompany(CompanyDto companyDto);
	

}
