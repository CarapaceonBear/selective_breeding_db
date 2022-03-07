package com.qa.starterproject.persistence;

import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;

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
	
	// for keeping doubles at 2 decimal places, for neatness
	private static DecimalFormat df = new DecimalFormat("0.00");
	
	public Dragon() {}
	
	// constructor with set values
	public Dragon(String name, String sex, String colour, double scaleQuality, double flyingSpeed,
				  double eggSize, double eggQuality, double breathTemperature) {
		super();
		this.name = name;
		this.sex = sex;
		this.colour = colour;
		this.scaleQuality = Double.valueOf(df.format(scaleQuality));
		this.flyingSpeed = Double.valueOf(df.format(flyingSpeed));
		this.eggSize = Double.valueOf(df.format(eggSize));
		this.eggQuality = Double.valueOf(df.format(eggQuality));
		this.breathTemperature = Double.valueOf(df.format(breathTemperature));
	}

	// constructor with random values
	public Dragon(String name, String sex) {
		super();
		this.name = name;
		this.sex = sex;
		
		double min = 1;
		double max = 3;
		double random = Math.random() * max + min;
		this.scaleQuality = Double.valueOf(df.format(random));
		random = Math.random() * max + min;
		this.flyingSpeed = Double.valueOf(df.format(random));
		random = Math.random() * max + min;
		this.eggSize = Double.valueOf(df.format(random));
		random = Math.random() * max + min;
		this.eggQuality = Double.valueOf(df.format(random));
		random = Math.random() * max + min;
		this.breathTemperature = Double.valueOf(df.format(random));
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
		this.scaleQuality = Double.valueOf(df.format(scaleQuality));
	}

	public double getFlyingSpeed() {
		return flyingSpeed;
	}
	public void setFlyingSpeed(double flyingSpeed) {
		this.flyingSpeed = Double.valueOf(df.format(flyingSpeed));
	}

	public double getEggSize() {
		return eggSize;
	}
	public void setEggSize(double eggSize) {
		this.eggSize = Double.valueOf(df.format(eggSize));
	}

	public double getEggQuality() {
		return eggQuality;
	}
	public void setEggQuality(double eggQuality) {
		this.eggQuality = Double.valueOf(df.format(eggQuality));
	}

	public double getBreathTemperature() {
		return breathTemperature;
	}
	public void setBreathTemperature(double breathTemperature) {
		this.breathTemperature = Double.valueOf(df.format(breathTemperature));
	}
	
	
	
	
	
}
