package hu.webuni.hr.kamarasd.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hu.webuni.hr.kamarasd.model.AvarageSalaryByPosition;
import hu.webuni.hr.kamarasd.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>{

	@Query("SELECT DISTINCT c FROM Company c JOIN c.employeeList e WHERE e.salary > :minSalary")
	public Page<Company> findByCompanyWhereSalaryGraterThan(Pageable pageable, int minSalary);
	
	@Query("SELECT c FROM Company c WHERE SIZE(c.employeeList) > :employeeLimit")
	public List<Company> getCompanyWhereEmployeesMoreThan(int employeeLimit);
	
	@Query("SELECT e.position.posName as positionName, avg(e.salary) as avarageSalary FROM Company c "
			+ "LEFT JOIN c.employeeList e WHERE c.id = :id "
			+ "GROUP BY e.position.posName "
			+ "ORDER BY avg(e.salary) DESC")
	public List<AvarageSalaryByPosition> getAvarageSalaryByPosition(long id);
}
