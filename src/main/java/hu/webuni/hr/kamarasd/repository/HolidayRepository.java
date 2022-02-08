package hu.webuni.hr.kamarasd.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import hu.webuni.hr.kamarasd.model.Holiday;

public interface HolidayRepository extends JpaRepository<Holiday, Long>, JpaSpecificationExecutor<Holiday>, PagingAndSortingRepository<Holiday, Long> {

	@Query("SELECT DISTINCT h FROM Holiday h")
	public Page<Holiday> findHolidaysPageable(Pageable pageable);
}
 