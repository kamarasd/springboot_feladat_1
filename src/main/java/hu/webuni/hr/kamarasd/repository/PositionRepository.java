package hu.webuni.hr.kamarasd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.hr.kamarasd.model.Position;

public interface PositionRepository extends JpaRepository<Position, Integer>{
	
	public List<Position> findByPosName(String posName);

}
