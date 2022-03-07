package com.qa.starterproject.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DragonRepository extends JpaRepository <Dragon, Long> {

	
	// find last entry in repository, for getting the final Id
	Dragon findTopByOrderByIdDesc();
	
}
