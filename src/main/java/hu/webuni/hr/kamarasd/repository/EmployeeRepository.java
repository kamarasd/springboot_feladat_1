package hu.webuni.hr.kamarasd.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hu.webuni.hr.kamarasd.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	@Query("SELECT e FROM Employee e WHERE LOWER(e.position.posName) LIKE %:post%")
	List<Employee> findEmployeeByPosName(String post);
	
	List<Employee> findByNameContainsIgnoreCase(String name);
	
	List<Employee> findAllByWorkingDateBetween(LocalDateTime dateFrom, LocalDateTime dateTo);
	
	List<Employee> findBySalaryGreaterThan(Integer salary);

}
