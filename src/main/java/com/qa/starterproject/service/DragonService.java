package com.qa.starterproject.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

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
	public List<Dragon> readAll() {
		return this.repo.findAll();
	}
	
	public Dragon readById(long x) {
		return this.repo.findById(x).orElseThrow(() -> new EntityNotFoundException("Dragon not found"));
	}
	// UPDATE
	
	// DELETE
}
