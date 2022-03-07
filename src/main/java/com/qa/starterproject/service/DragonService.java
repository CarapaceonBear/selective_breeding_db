package com.qa.starterproject.service;

//import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.starterproject.persistence.Dragon;
import com.qa.starterproject.persistence.DragonRepository;

@Service
public class DragonService {

	private DragonRepository repo;
	
	@Autowired
	public DragonService(DragonRepository repo) {
		this.repo = repo;
	}
	
	// CREATE
	public Dragon create(Dragon d) {
		return this.repo.save(d);
	}
	// READ
//	public Dragon readById(long x) {
//		Optional<Dragon> d = this.repo.findById(x);
//		return d.get();
//	}
	// UPDATE
	
	// DELETE
}
