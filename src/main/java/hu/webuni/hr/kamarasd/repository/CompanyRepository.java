package hu.webuni.hr.kamarasd.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hu.webuni.hr.kamarasd.model.AverageSalary;
import hu.webuni.hr.kamarasd.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>{

	@Query("SELECT DISTINCT c FROM Company c JOIN c.employeeList e WHERE e.salary > :minSalary")
	public Page<Company> findByCompanyWhereSalaryGraterThan(Pageable pageable, int minSalary);
	
	@Query("SELECT DISTINCT c FROM Company c LEFT JOIN FETCH c.employeeList WHERE SIZE(c.employeeList) > :employeeLimit")
	public List<Company> getCompanyWhereEmployeesMoreThan(int employeeLimit);
	
	@Query("SELECT e.position.posName AS posName, avg(e.salary) as avgSalary FROM Company c "
			+ "LEFT JOIN c.employeeList e WHERE c.id = :id "
			+ "GROUP BY e.position.posName "
			+ "ORDER BY avg(e.salary) DESC")
	public List<AverageSalary> getAvarageSalaryByPosition(long id);
	
	//@EntityGraph(attributePaths = "employeeList")
	@EntityGraph(attributePaths = {"employeeList", "employeeList.position"})
	//@Query("SELECT DISTINCT c FROM Company c LEFT JOIN FETCH c.employeeList")
	@Query("SELECT c FROM Company c")
	public List<Company> getAllCompanyWithEmployees();
	
	@EntityGraph(attributePaths = {"employeeList", "employeeList.position"})
	//@Query("SELECT DISTINCT c FROM Company c LEFT JOIN FETCH c.employeeList WHERE c.id = :id")
	@Query("SELECT c FROM Company c WHERE c.id = :id")
	public Optional<Company> findByIdWithEmployees(long id);
}
