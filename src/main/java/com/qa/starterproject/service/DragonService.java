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
	
	// =================== BASIC CRUD ====================== //
	
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
	
	// ================ CUSTOM QUERIES ===================== //
	
	// BREED TWO DRAGONS TOGETHER
	public String breed(long a, long b) {
		Dragon parentA = this.repo.getById(a);
		Dragon parentB = this.repo.getById(b);
		if ((parentA.getSex().equals("Male") && parentB.getSex().equals("Male")) ||
			(parentA.getSex().equals("Female") && parentB.getSex().equals("Female"))) {
			return ("Dragons not breedable (same sex)");
		} else {
			// create blank offspring entry
			Dragon offspring = new Dragon();
			offspring.setName("unnamed");
			offspring.setGeneration(parentA.getGeneration() + 1);
			// assign random sex
			double min = 0;
			double max = 1;
			double random = Math.random() * max + min;
			if (random >= 0.5) {
				offspring.setSex("male");
			} else {
				offspring.setSex("female");
			}
			// inherit colour from either mother or father
			random = Math.random() * max + min;
			if (random >= 0.5) {
				offspring.setColour(parentA.getColour());
			} else {
				offspring.setColour(parentB.getColour());
			}
			// inherited values are the average of both parents and a random value (based on higher trait + 1)
			min = 1;
			max = (Math.max(parentA.getScaleQuality(), parentB.getScaleQuality())) + 1;
			random = Math.random() * max + min;
			offspring.setScaleQuality((parentA.getScaleQuality() + parentB.getScaleQuality() + random) / 3);
					
			max = (Math.max(parentA.getFlyingSpeed(), parentB.getFlyingSpeed())) + 1;
			random = Math.random() * max + min;
			offspring.setFlyingSpeed((parentA.getFlyingSpeed() + parentB.getFlyingSpeed() + random) / 3);
			
			max = (Math.max(parentA.getEggSize(), parentB.getEggSize())) + 1;
			random = Math.random() * max + min;
			offspring.setEggSize((parentA.getEggSize() + parentB.getEggSize() + random) / 3);
			
			
			max = (Math.max(parentA.getEggQuality(), parentB.getEggQuality())) + 1;
			random = Math.random() * max + min;
			offspring.setEggQuality((parentA.getEggQuality() + parentB.getEggQuality() + random) / 3);
			
			max = (Math.max(parentA.getBreathTemperature(), parentB.getBreathTemperature())) + 1;
			random = Math.random() * max + min;
			offspring.setBreathTemperature((parentA.getBreathTemperature() + parentB.getBreathTemperature() + random) / 3);
			
			this.repo.save(offspring);
			return ("New dragon born, saved at index " + this.repo.findTopByOrderByIdDesc().getId());
		}
	}
	
	
	
	
	
}
