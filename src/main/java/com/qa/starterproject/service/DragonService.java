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
	
	private DragonBreedingFunction breedFunction;
	
	@Autowired
	public DragonService(DragonRepository repo, DragonBreedingFunction breedFunction) {
		this.repo = repo;
		this.breedFunction = breedFunction;
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
	
	static String[] possibleColours = {"Red", "Blue", "Green", "Black", "White", "Pink", "Purple", "Orange", "Yellow", "Cyan"};
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
		Dragon parentA = this.getById(a);
		Dragon parentB = this.getById(b);
		// check if parents are viable
		String check = breedFunction.checkViable(parentA, parentB);
		if (check.equals("viable")) {
			// if viable, call breeding function
			Dragon offspring = breedFunction.breed(parentA, parentB);
			this.repo.save(offspring);
			return ("New dragon born, a " + offspring.getSex() + ", saved at index " + this.repo.findTopByOrderByIdDesc().getId());
		} else {
			return check;
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
	
	// FIND BEST BREEDING PAIRS FOR CERTAIN TRAIT
	public List<String> getIdealPairs(String trait) {
		List<String> pairs = new ArrayList<String>();
		List<Dragon> candidates = new ArrayList<Dragon>();
		switch (trait) {
		case "scaleQuality":
			candidates = this.repo.findTop10ByOrderByScaleQualityDesc();
			break;
		case "flyingSpeed":
			candidates = this.repo.findTop10ByOrderByFlyingSpeedDesc();
			break;
		case "eggSize":
			candidates = this.repo.findTop10ByOrderByEggSizeDesc();
			break;
		case "eggQuality":
			candidates = this.repo.findTop10ByOrderByEggQualityDesc();
			break;
		case "breathTemperature":
			candidates = this.repo.findTop10ByOrderByBreathTemperatureDesc();
			break;
		}
		pairs = breedFunction.getPairs(trait, pairs, candidates);
		return pairs;
	}
}
