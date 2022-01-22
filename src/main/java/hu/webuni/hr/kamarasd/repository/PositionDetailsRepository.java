package hu.webuni.hr.kamarasd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hu.webuni.hr.kamarasd.model.PositionDetails;

public interface PositionDetailsRepository extends JpaRepository<PositionDetails, Long> {
	
}
