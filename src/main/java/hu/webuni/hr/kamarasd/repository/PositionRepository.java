package hu.webuni.hr.kamarasd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.hr.kamarasd.model.Position;

public interface PositionRepository extends JpaRepository<Position, Integer>{
	
	//public List<Position> findByName(String name);

}
