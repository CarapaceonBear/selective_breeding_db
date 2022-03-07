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
	public Dragon updateById(Dragon d, long x) {
		Dragon updated = this.readById(x);
		updated.setName(d.getName());
		updated.setSex(d.getSex());
		updated.setColour(d.getColour());
		updated.setScaleQuality(d.getScaleQuality());
		updated.setFlyingSpeed(d.getFlyingSpeed());
		updated.setEggSize(d.getEggSize());
		updated.setEggQuality(d.getEggQuality());
		updated.setBreathTemperature(d.getBreathTemperature());
		return this.repo.save(updated);
	}
	
	// DELETE
	public String deleteById(long x) {
		Dragon deleted = this.readById(x);
		this.repo.deleteById(x);
		return (deleted.getName() + " deleted at index " + x);
	}
	
}
