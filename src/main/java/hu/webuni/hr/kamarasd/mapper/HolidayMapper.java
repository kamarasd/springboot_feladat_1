package hu.webuni.hr.kamarasd.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import hu.webuni.hr.kamarasd.dto.HolidayDto;
import hu.webuni.hr.kamarasd.model.Holiday;

@Mapper(componentModel = "spring")
public interface HolidayMapper {

	List<HolidayDto> holidayToDtos(List<Holiday> holiday);
	
	HolidayDto holidayToDto(Holiday holiday);
	
	Holiday dtoToHoliday(HolidayDto holidayDto);
}
