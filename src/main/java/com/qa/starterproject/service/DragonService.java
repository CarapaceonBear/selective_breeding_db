package com.qa.starterproject.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.qa.starterproject.persistence.DragonRepository;

public class DragonService {

	private DragonRepository repo;
	
	@Autowired
	public DragonService(DragonRepository repo) {
		this.repo = repo;
	}
	
	// CREATE
	// READ
	// UPDATE
	// DELETE
}
