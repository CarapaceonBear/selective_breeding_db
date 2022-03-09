package com.qa.starterproject.service;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Component;

import com.qa.starterproject.persistence.Dragon;

@Component
public class DragonBreedingFunction {
	
	public String checkViable(Dragon parentA, Dragon parentB) {
		if ((parentA.getSex().equals("Male") && parentB.getSex().equals("Male")) ||
			(parentA.getSex().equals("Female") && parentB.getSex().equals("Female"))) {
			return ("Dragons not breedable (same sex)");
		} else if (parentA.getGeneration() != parentB.getGeneration()) {
			return ("Dragons not breedable (different generations)");
		} else {	
			return ("viable");
		}
	}
	
	public Dragon breed(Dragon parentA, Dragon parentB) {
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
			int randomColour = ThreadLocalRandom.current().nextInt(0, 10);	
			offspring.setColour(DragonService.possibleColours[randomColour]);
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
		
		return offspring;
	}
	
	
}
