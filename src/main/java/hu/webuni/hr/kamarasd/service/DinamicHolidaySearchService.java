package hu.webuni.hr.kamarasd.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import hu.webuni.hr.kamarasd.model.Approved;
import hu.webuni.hr.kamarasd.model.Holiday;
import hu.webuni.hr.kamarasd.model.Holiday_;

public class DinamicHolidaySearchService {
	
	@Autowired
	static 
	EntityManager entityManager;
	
	public static Specification<Holiday> searchById(Long id) {
		return (root, cq, cb) -> cb.equal(root.get(Holiday_.id), id);
	}
	
	public static Specification<Holiday> searchByApprove(Approved approved) {
		return (root, cq, cb) -> cb.equal(root.get(Holiday_.approved), approved);
	}
	
	public static Specification<Holiday> searchByName(String name) {
		return (root, cq, cb) -> cb.like(cb.lower(root.get(Holiday_.createdBy)), name.toLowerCase() + "%"); 
	}
	
	public static Specification<Holiday> searchBySuperior(String name) {
		return (root, cq, cb) -> cb.like(cb.lower(root.get(Holiday_.superior)), name.toLowerCase() + "%"); 
	}
	
	public static Specification<Holiday> searchByCreatedDate(LocalDateTime dateFrom, LocalDateTime dateTo) {
		return (root, cq, cb) -> cb.between(root.get(Holiday_.createdDate), dateFrom, dateTo);
	}
	
	public static Specification<Holiday> searchByHolidayDate(LocalDate dateFrom, LocalDate dateTo) {
		return (root, cq, cb) -> cb.between(root.get(Holiday_.holidayStart), dateFrom, dateTo);
	}


}
