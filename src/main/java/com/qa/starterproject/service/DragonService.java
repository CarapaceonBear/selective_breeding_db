package com.qa.starterproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
	public List<Dragon> getAll() {
		return this.repo.findAll();
	}
	
	public Dragon getById(long x) {
		return this.repo.findById(x).orElseThrow(() -> new EntityNotFoundException("Dragon not found"));
	}
	
	// UPDATE
	public Dragon updateById(Dragon d, long x) {
		Dragon updated = this.getById(x);
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
		Dragon deleted = this.getById(x);
		this.repo.deleteById(x);
		return (deleted.getName() + " deleted at index " + x);
	}
	
	public String clearDb() {
		long x = this.repo.count();
		this.repo.deleteAll();
		return ("Database cleared. " + x + " entities removed.");
	}
	
	// ================ CUSTOM QUERIES ===================== //
	
	String[] possibleColours = {"Red", "Blue", "Green", "Black", "White", "Pink", "Purple", "Orange", "Yellow", "Cyan"};
	int randomColour;
	
	// CREATE WITH RANDOM VALUES
	public Dragon createRandom(String name, String sex) {
		Dragon d = new Dragon(name, sex);
		randomColour = ThreadLocalRandom.current().nextInt(0, 10);	
		d.setColour(possibleColours[randomColour]);
		return this.repo.save(d);
	}
	
	
	// BREED TWO DRAGONS TOGETHER
	public String breed(long a, long b) {
		Dragon parentA = this.repo.getById(a);
		Dragon parentB = this.repo.getById(b);
		if ((parentA.getSex().equals("Male") && parentB.getSex().equals("Male")) ||
			(parentA.getSex().equals("Female") && parentB.getSex().equals("Female"))) {
			return ("Dragons not breedable (same sex)");
		} else if (parentA.getGeneration() != parentB.getGeneration()) {
			return ("Dragons not breedable (different generations)");
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
				offspring.setSex("Male");
			} else {
				offspring.setSex("Female");
			}
			// inherit colour from either mother or father
			random = Math.random() * max + min;
			if (random <= 0.45) {
				offspring.setColour(parentA.getColour());
			} else if (random <= 0.9) {
				offspring.setColour(parentB.getColour());
			} else {
				randomColour = ThreadLocalRandom.current().nextInt(0, 10);	
				offspring.setColour(possibleColours[randomColour]);
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
			return ("New dragon born, a " + offspring.getSex() + ", saved at index " + this.repo.findTopByOrderByIdDesc().getId());
		}
	}
	
	// RENAME NEW OFFSPRING
	public Dragon rename(long x, String name) {
		Dragon updated = this.getById(x);
		updated.setName(name);
		return this.repo.save(updated);
	}
	
	// READ ALL OF GENERATION
	public List<Dragon> getByGeneration(int generation) {
		return this.repo.findDragonByGeneration(generation);
	}
	
	// FIND BEST IN A CERTAIN TRAIT
	public List<Dragon> getBestOfScaleQuality() {
		return this.repo.findTop10ByOrderByScaleQualityDesc();
	}
	public List<Dragon> getBestOfFlyingSpeed() {
		return this.repo.findTop10ByOrderByFlyingSpeedDesc();
	}
	public List<Dragon> getBestOfEggSize() {
		return this.repo.findTop10ByOrderByEggSizeDesc();
	}
	public List<Dragon> getBestOfEggQuality() {
		return this.repo.findTop10ByOrderByEggQualityDesc();
	}
	public List<Dragon> getBestOfBreathTemperature() {
		return this.repo.findTop10ByOrderByBreathTemperatureDesc();
	}
	
	// FIND BEST BREEDING PAIR FOR CERTAIN TRAIT
	// first match the trait passed from the query
	// then iterate through the relevant top10, getting the top match for each entry
	public List<String> getIdealPairs(String trait) {
		List<String> pair = new ArrayList<String>();
		List<Dragon> candidates = new ArrayList<Dragon>();
		switch (trait) {
		case "scaleQuality":
			candidates = this.repo.findTop10ByOrderByScaleQualityDesc();
			for (int i = 0; i < candidates.size(); i++) {
				for (int j = (i + 1); j < candidates.size(); j++) {
					if ((candidates.get(i).getGeneration() == candidates.get(j).getGeneration()) && 
						(! candidates.get(i).getSex().equals(candidates.get(j).getSex()))) 
					{
						pair.add("ID: " + candidates.get(i).getId() + ", " + candidates.get(i).getName() + 
								 ", Quality: " + candidates.get(i).getScaleQuality() + " / " +
								 "ID: " + candidates.get(j).getId() + ", " + candidates.get(j).getName() + 
								 ", Quality: " + candidates.get(j).getScaleQuality());
						break;
					} else { continue; }
					}}
			break;
		case "flyingSpeed":
			candidates = this.repo.findTop10ByOrderByFlyingSpeedDesc();
			for (int i = 0; i < candidates.size(); i++) {
				for (int j = (i + 1); j < candidates.size(); j++) {
					if ((candidates.get(i).getGeneration() == candidates.get(j).getGeneration()) && 
						(! candidates.get(i).getSex().equals(candidates.get(j).getSex()))) 
					{
						pair.add("ID: " + candidates.get(i).getId() + ", " + candidates.get(i).getName() + 
								 ", Speed: " + candidates.get(i).getFlyingSpeed() + " / " +
								 "ID: " + candidates.get(j).getId() + ", " + candidates.get(j).getName() + 
								 ", Speed: " + candidates.get(j).getFlyingSpeed());
						break;
					} else { continue; }
					}}
			break;
		case "eggSize":
			candidates = this.repo.findTop10ByOrderByEggSizeDesc();
			for (int i = 0; i < candidates.size(); i++) {
				for (int j = (i + 1); j < candidates.size(); j++) {
					if ((candidates.get(i).getGeneration() == candidates.get(j).getGeneration()) && 
						(! candidates.get(i).getSex().equals(candidates.get(j).getSex()))) 
					{
						pair.add("ID: " + candidates.get(i).getId() + ", " + candidates.get(i).getName() + 
								 ", Size: " + candidates.get(i).getEggSize() + " / " +
								 "ID: " + candidates.get(j).getId() + ", " + candidates.get(j).getName() + 
								 ", Size: " + candidates.get(j).getEggSize());
						break;
					} else { continue; }
					}}
			break;
		case "eggQuality":
			candidates = this.repo.findTop10ByOrderByEggQualityDesc();
			for (int i = 0; i < candidates.size(); i++) {
				for (int j = (i + 1); j < candidates.size(); j++) {
					if ((candidates.get(i).getGeneration() == candidates.get(j).getGeneration()) && 
						(! candidates.get(i).getSex().equals(candidates.get(j).getSex()))) 
					{
						pair.add("ID: " + candidates.get(i).getId() + ", " + candidates.get(i).getName() + 
								 ", Quality: " + candidates.get(i).getEggQuality() + " / " +
								 "ID: " + candidates.get(j).getId() + ", " + candidates.get(j).getName() + 
								 ", Quality: " + candidates.get(j).getEggQuality());
						break;
					} else { continue; }
					}}
			break;
		case "breathTemperature":
			candidates = this.repo.findTop10ByOrderByBreathTemperatureDesc();
			for (int i = 0; i < candidates.size(); i++) {
				for (int j = (i + 1); j < candidates.size(); j++) {
					if ((candidates.get(i).getGeneration() == candidates.get(j).getGeneration()) && 
						(! candidates.get(i).getSex().equals(candidates.get(j).getSex()))) 
					{
						pair.add("ID: " + candidates.get(i).getId() + ", " + candidates.get(i).getName() + 
								 ", Temperature: " + candidates.get(i).getBreathTemperature() + " / " +
								 "ID: " + candidates.get(j).getId() + ", " + candidates.get(j).getName() + 
								 ", Temperature: " + candidates.get(j).getBreathTemperature());
						break;
					} else { continue; }
					}}
			break;
		}
		return pair;
	}
	
	
}
