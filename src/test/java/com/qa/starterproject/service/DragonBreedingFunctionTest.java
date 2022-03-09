package com.qa.starterproject.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.qa.starterproject.persistence.Dragon;

@SpringBootTest
public class DragonBreedingFunctionTest {

	@Autowired
	private DragonBreedingFunction breedingFunction;
	
	
	@Test
	void checkViableTest1() {
		Dragon x = new Dragon(1L, 1, "Example1", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		Dragon y = new Dragon(2L, 1, "Example2", "Female", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		String viableMessage = "viable";

		assertThat(this.breedingFunction.checkViable(x, y).equals(viableMessage));
	}
	
	@Test
	void checkViableTest2() {
		Dragon x = new Dragon(1L, 1, "Example1", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		Dragon y = new Dragon(2L, 1, "Example2", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		String viableMessage = "Dragons not breedable (same sex)";

		assertThat(this.breedingFunction.checkViable(x, y).equals(viableMessage));
	}
	
	@Test
	void checkViableTest3() {
		Dragon x = new Dragon(1L, 1, "Example1", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		Dragon y = new Dragon(2L, 2, "Example2", "Female", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		String viableMessage = "Dragons not breedable (different generations)";

		assertThat(this.breedingFunction.checkViable(x, y).equals(viableMessage));
	}
	
	// ================== breed tests ====================== //
	
	@Test
	void breedTestName() {
		Dragon x = new Dragon(1L, 1, "Example1", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		Dragon y = new Dragon(2L, 1, "Example2", "Female", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		String offspringName = "unnamed";
		assertThat(this.breedingFunction.breed(x, y).getName() == offspringName);
	}
	@Test
	void breedTestGeneration() {
		Dragon x = new Dragon(1L, 1, "Example1", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		Dragon y = new Dragon(2L, 1, "Example2", "Female", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		int generation = 2;
		assertThat(this.breedingFunction.breed(x, y).getGeneration() == generation);	
	}
	@Test
	void breedTestSex() {
		Dragon x = new Dragon(1L, 1, "Example1", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		Dragon y = new Dragon(2L, 1, "Example2", "Female", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		assertThat(this.breedingFunction.breed(x, y).getSex().equals("Male") ||
				   this.breedingFunction.breed(x, y).getSex().equals("Female"));	
	}
	@Test
	void breedTestColour() {
		Dragon x = new Dragon(1L, 1, "Example1", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		Dragon y = new Dragon(2L, 1, "Example2", "Female", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		String[] colours = DragonService.possibleColours;
		assertThat(this.breedingFunction.breed(x, y).getColour().equals(colours[0]) ||
				   this.breedingFunction.breed(x, y).getColour().equals(colours[1]) ||
				   this.breedingFunction.breed(x, y).getColour().equals(colours[2]) ||
				   this.breedingFunction.breed(x, y).getColour().equals(colours[3]) ||
				   this.breedingFunction.breed(x, y).getColour().equals(colours[4]) ||
				   this.breedingFunction.breed(x, y).getColour().equals(colours[5]) ||
				   this.breedingFunction.breed(x, y).getColour().equals(colours[6]) ||
				   this.breedingFunction.breed(x, y).getColour().equals(colours[7]) ||
				   this.breedingFunction.breed(x, y).getColour().equals(colours[8]) ||
				   this.breedingFunction.breed(x, y).getColour().equals(colours[9]));
	}
	@Test
	void breedTestScaleQuality() {
		Dragon x = new Dragon(1L, 1, "Example1", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		Dragon y = new Dragon(2L, 1, "Example2", "Female", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		double minValue = 5/3;
		double maxValue = 7/3;
		assertThat(this.breedingFunction.breed(x, y).getScaleQuality() >= minValue &&
				   this.breedingFunction.breed(x, y).getScaleQuality() <= maxValue);	
	}
	@Test
	void breedTestFlyingSpeed() {
		Dragon x = new Dragon(1L, 1, "Example1", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		Dragon y = new Dragon(2L, 1, "Example2", "Female", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		double minValue = 5/3;
		double maxValue = 7/3;
		assertThat(this.breedingFunction.breed(x, y).getFlyingSpeed() >= minValue &&
				   this.breedingFunction.breed(x, y).getFlyingSpeed() <= maxValue);	
	}
	@Test
	void breedTestEggSize() {
		Dragon x = new Dragon(1L, 1, "Example1", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		Dragon y = new Dragon(2L, 1, "Example2", "Female", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		double minValue = 5/3;
		double maxValue = 7/3;
		assertThat(this.breedingFunction.breed(x, y).getEggSize() >= minValue &&
				   this.breedingFunction.breed(x, y).getEggSize() <= maxValue);	
	}
	@Test
	void breedTestEggQuality() {
		Dragon x = new Dragon(1L, 1, "Example1", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		Dragon y = new Dragon(2L, 1, "Example2", "Female", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		double minValue = 5/3;
		double maxValue = 7/3;
		assertThat(this.breedingFunction.breed(x, y).getEggQuality() >= minValue &&
				   this.breedingFunction.breed(x, y).getEggQuality() <= maxValue);	
	}
	@Test
	void breedTestBreathTemperature() {
		Dragon x = new Dragon(1L, 1, "Example1", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		Dragon y = new Dragon(2L, 1, "Example2", "Female", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		double minValue = 5/3;
		double maxValue = 7/3;
		assertThat(this.breedingFunction.breed(x, y).getBreathTemperature() >= minValue &&
				   this.breedingFunction.breed(x, y).getBreathTemperature() <= maxValue);	
	}

	// ================ getPairs tests ===================== //
	
	@Test
	void getPairsTestScaleQuality() {
		String trait = "scaleQuality";
		List<String> pairs = new ArrayList<String>();
		Dragon x = new Dragon(1L, 1, "Example1", "Female", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		Dragon y = new Dragon(2L, 1, "Example2", "Male", "Red", 3.0, 3.0, 3.0, 3.0, 3.0);
		Dragon z = new Dragon(3L, 1, "Example3", "Male", "Red", 4.0, 4.0, 4.0, 4.0, 4.0);
		List<Dragon> candidates = new ArrayList<Dragon>();
		candidates.add(x);
		candidates.add(y);
		candidates.add(z);
		candidates.sort(Comparator.comparing(Dragon::getScaleQuality).reversed());
		List<String> pairsOutput = new ArrayList<String>();
		pairsOutput.add("ID: 3, Example3, Quality: 4.0 / ID: 1, Example1, Quality: 2.0");
		pairsOutput.add("ID: 2, Example2, Quality: 3.0 / ID: 1, Example1, Quality: 2.0");
		
		assertThat(this.breedingFunction.getPairs(trait, pairs, candidates)
				   .equals(pairsOutput));		
	}
	@Test
	void getPairsTestFlyingSpeed() {
		String trait = "flyingSpeed";
		List<String> pairs = new ArrayList<String>();
		Dragon x = new Dragon(1L, 1, "Example1", "Female", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		Dragon y = new Dragon(2L, 1, "Example2", "Male", "Red", 3.0, 3.0, 3.0, 3.0, 3.0);
		Dragon z = new Dragon(3L, 1, "Example3", "Male", "Red", 4.0, 4.0, 4.0, 4.0, 4.0);
		List<Dragon> candidates = new ArrayList<Dragon>();
		candidates.add(x);
		candidates.add(y);
		candidates.add(z);
		candidates.sort(Comparator.comparing(Dragon::getFlyingSpeed).reversed());
		List<String> pairsOutput = new ArrayList<String>();
		pairsOutput.add("ID: 3, Example3, Speed: 4.0 / ID: 1, Example1, Speed: 2.0");
		pairsOutput.add("ID: 2, Example2, Speed: 3.0 / ID: 1, Example1, Speed: 2.0");
		
		assertThat(this.breedingFunction.getPairs(trait, pairs, candidates)
				   .equals(pairsOutput));		
	}
	@Test
	void getPairsTestEggSize() {
		String trait = "eggSize";
		List<String> pairs = new ArrayList<String>();
		Dragon x = new Dragon(1L, 1, "Example1", "Female", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		Dragon y = new Dragon(2L, 1, "Example2", "Male", "Red", 3.0, 3.0, 3.0, 3.0, 3.0);
		Dragon z = new Dragon(3L, 1, "Example3", "Male", "Red", 4.0, 4.0, 4.0, 4.0, 4.0);
		List<Dragon> candidates = new ArrayList<Dragon>();
		candidates.add(x);
		candidates.add(y);
		candidates.add(z);
		candidates.sort(Comparator.comparing(Dragon::getEggSize).reversed());
		List<String> pairsOutput = new ArrayList<String>();
		pairsOutput.add("ID: 3, Example3, Size: 4.0 / ID: 1, Example1, Size: 2.0");
		pairsOutput.add("ID: 2, Example2, Size: 3.0 / ID: 1, Example1, Size: 2.0");
		
		assertThat(this.breedingFunction.getPairs(trait, pairs, candidates)
				   .equals(pairsOutput));		
	}
	@Test
	void getPairsTestEggQuality() {
		String trait = "eggQuality";
		List<String> pairs = new ArrayList<String>();
		Dragon x = new Dragon(1L, 1, "Example1", "Female", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		Dragon y = new Dragon(2L, 1, "Example2", "Male", "Red", 3.0, 3.0, 3.0, 3.0, 3.0);
		Dragon z = new Dragon(3L, 1, "Example3", "Male", "Red", 4.0, 4.0, 4.0, 4.0, 4.0);
		List<Dragon> candidates = new ArrayList<Dragon>();
		candidates.add(x);
		candidates.add(y);
		candidates.add(z);
		candidates.sort(Comparator.comparing(Dragon::getEggQuality).reversed());
		List<String> pairsOutput = new ArrayList<String>();
		pairsOutput.add("ID: 3, Example3, Quality: 4.0 / ID: 1, Example1, Quality: 2.0");
		pairsOutput.add("ID: 2, Example2, Quality: 3.0 / ID: 1, Example1, Quality: 2.0");
		
		assertThat(this.breedingFunction.getPairs(trait, pairs, candidates)
				   .equals(pairsOutput));		
	}
	@Test
	void getPairsTestBreathTemperature() {
		String trait = "breathTemperature";
		List<String> pairs = new ArrayList<String>();
		Dragon x = new Dragon(1L, 1, "Example1", "Female", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		Dragon y = new Dragon(2L, 1, "Example2", "Male", "Red", 3.0, 3.0, 3.0, 3.0, 3.0);
		Dragon z = new Dragon(3L, 1, "Example3", "Male", "Red", 4.0, 4.0, 4.0, 4.0, 4.0);
		List<Dragon> candidates = new ArrayList<Dragon>();
		candidates.add(x);
		candidates.add(y);
		candidates.add(z);
		candidates.sort(Comparator.comparing(Dragon::getBreathTemperature).reversed());
		List<String> pairsOutput = new ArrayList<String>();
		pairsOutput.add("ID: 3, Example3, Temperature: 4.0 / ID: 1, Example1, Temperature: 2.0");
		pairsOutput.add("ID: 2, Example2, Temperature: 3.0 / ID: 1, Example1, Temperature: 2.0");
		
		assertThat(this.breedingFunction.getPairs(trait, pairs, candidates)
				   .equals(pairsOutput));		
	}
	@Test
	void getPairsTestNoResults() {
		String trait = "scaleQuality";
		List<String> pairs = new ArrayList<String>();
		Dragon x = new Dragon(1L, 1, "Example1", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		Dragon y = new Dragon(2L, 1, "Example2", "Male", "Red", 3.0, 3.0, 3.0, 3.0, 3.0);
		Dragon z = new Dragon(3L, 1, "Example3", "Male", "Red", 4.0, 4.0, 4.0, 4.0, 4.0);
		List<Dragon> candidates = new ArrayList<Dragon>();
		candidates.add(x);
		candidates.add(y);
		candidates.add(z);
		candidates.sort(Comparator.comparing(Dragon::getScaleQuality).reversed());
		List<String> pairsOutput = new ArrayList<String>();
		
		assertThat(this.breedingFunction.getPairs(trait, pairs, candidates)
				   .equals(pairsOutput));		
	}
}
