package com.qa.starterproject.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Dragon {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	private String sex;
	private int generation = 1; // not defined in constructor, increments if created through breeding
	
	// inheritable traits
	private String colour;
	private double scaleQuality;
	private double flyingSpeed;
	private double eggSize;
	private double eggQuality;
	private double breathTemperature;
	
	public Dragon() {}
	
	public Dragon(String name, String sex, String colour, double scaleQuality, double flyingSpeed,
				  double eggSize, double eggQuality, double breathTemperature) {
		super();
		this.name = name;
		this.sex = sex;
		this.colour = colour;
		this.scaleQuality= scaleQuality;
		this.flyingSpeed = flyingSpeed;
		this.eggSize = eggSize;
		this.eggQuality = eggQuality;
		this.breathTemperature = breathTemperature;
	}
	
	public Dragon(String name, String sex, String colour) {
		super();
		this.name = name;
		this.sex = sex;
		this.colour = colour;
		// I want a constructor which randomises the other values WORK OUT LATER
	}

	// ===== setters / getters ===== //
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getGeneration() {
		return generation;
	}
	public void setGeneration(int generation) {
		this.generation = generation;
	}

	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		this.colour = colour;
	}

	public double getScaleQuality() {
		return scaleQuality;
	}
	public void setScaleQuality(double scaleQuality) {
		this.scaleQuality = scaleQuality;
	}

	public double getFlyingSpeed() {
		return flyingSpeed;
	}
	public void setFlyingSpeed(double flyingSpeed) {
		this.flyingSpeed = flyingSpeed;
	}

	public double getEggSize() {
		return eggSize;
	}
	public void setEggSize(double eggSize) {
		this.eggSize = eggSize;
	}

	public double getEggQuality() {
		return eggQuality;
	}
	public void setEggQuality(double eggQuality) {
		this.eggQuality = eggQuality;
	}

	public double getBreathTemperature() {
		return breathTemperature;
	}
	public void setBreathTemperature(double breathTemperature) {
		this.breathTemperature = breathTemperature;
	}
	
	
	
	
	
}
