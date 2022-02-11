package hu.webuni.hr.kamarasd.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.hr.kamarasd.model.Approved;
import hu.webuni.hr.kamarasd.model.Holiday;
import hu.webuni.hr.kamarasd.model.HolidaySearch;
import hu.webuni.hr.kamarasd.repository.HolidayRepository;

@Service
public class HolidayService {
	
	@Autowired
	HolidayRepository holidayRepository;
	
	public List<Holiday> getAllHolidays() {
		return holidayRepository.findAll();
	}

	@Transactional
	public Holiday saveHoliday(Holiday holiday) {
		return holidayRepository.save(holiday);
	}

	public Optional<Holiday> getHolidayById(Long id) {
		return holidayRepository.findById(id);
	}
	
	@Transactional
	public void deleteHolidayById(Long id) {
		holidayRepository.deleteById(id);
	}

	public List<Holiday> findHolidayBySpringDataFinder(HolidaySearch holiday) {
		
		Specification<Holiday> spec = Specification.where(null);
		
		if(holiday.getId() != null) {
			spec = spec.and(DinamicHolidaySearchService.searchById(holiday.getId()));
		}
		
		if(holiday.getApproved() != null) {
			spec = spec.and(DinamicHolidaySearchService.searchByApprove(holiday.getApproved()));
		}
		
		if(holiday.getSuperiorName() != null) {
			spec = spec.and(DinamicHolidaySearchService.searchBySuperior(holiday.getSuperiorName()));
		}
		
		if(holiday.getCreatorName() != null) {
			spec = spec.and(DinamicHolidaySearchService.searchByCreator(holiday.getCreatorName()));
		}
		
		if(holiday.getSetDateFrom().isBefore(LocalDateTime.of(0001, 01, 01, 00, 00)) && holiday.getSetDateTo().isBefore(LocalDateTime.of(0001, 01, 01, 00, 00))) {
			spec = spec.and(DinamicHolidaySearchService.searchByCreatedDate(holiday.getSetDateFrom(), holiday.getSetDateTo()));
		}
		
		if(holiday.getHolidayDateFrom().isBefore(LocalDate.of(0001, 01, 01)) && holiday.getHolidayDateTo().isBefore(LocalDate.of(0001, 01, 01))) {
			spec = spec.and(DinamicHolidaySearchService.searchByHolidayDate(holiday.getHolidayDateFrom(), holiday.getHolidayDateTo()));
		}
			
		return holidayRepository.findAll(spec, Sort.by("id"));
		
//	    {   "id": ,
//	        "approved": "PENDING" or "NOT_APPROVED" or "APPROVED",
//	        "superiorName": "",
//			"creatorName": "",
//	        "holidayDateFrom": "0001-01-01",
//	        "holidayDateTo": "0001-01-01",
//	        "setDateFrom": "0001-01-01T01:01:01",
//	        "setDateTo": "0001-01-01T01:01:01"
//	            }
		
	}
	
	@Transactional
	public Holiday saveHolidayWithId(Holiday holiday, Long id) {
		return holidayRepository.save(holiday);
	}
	
	public Page<Holiday> findPageableHoliday(Pageable pageable) {
		return holidayRepository.findHolidaysPageable(pageable);
	}
	
	public boolean HolidayIsPending(Approved approved) {
		if(approved == Approved.PENDING) {
			return true;
		} else {
			return false;
		}
	}
	
	public Approved getApproveType(Boolean approve) {
		return approve == true ? Approved.APPROVED : Approved.NOT_APPROVED;
	}
	
	

}
