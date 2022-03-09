package com.qa.starterproject.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.starterproject.persistence.Dragon;
import com.qa.starterproject.persistence.DragonRepository;

@SpringBootTest
public class DragonServiceUnitTest {

	@Autowired
	private DragonService service;
	
	@MockBean
	private DragonRepository repo;
	
	@MockBean
	private DragonBreedingFunction breedFunction;
		
	// =================== BASIC CRUD ====================== //
	
	// CREATE
	@Test
	void createTest() {
		//GIVEN
		Dragon x = new Dragon("Example", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		Dragon y = new Dragon(1L, 1, "Example", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		//WHEN
		Mockito.when(this.repo.save(x)).thenReturn(y);
		//THEN
		assertThat(this.service.create(x)).isEqualTo(y);
		//Verify
		Mockito.verify(this.repo, Mockito.times(1)).save(Mockito.any(Dragon.class));
	}
	
	//READ
	@Test
	void getAllTest() {
		//GIVEN
		Dragon x = new Dragon(1L, 1, "Example1", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		Dragon y = new Dragon(2L, 1, "Example2", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		Dragon z = new Dragon(3L, 1, "Example3", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		List<Dragon> list = new ArrayList<Dragon>();
		list.add(x);
		list.add(y);
		list.add(z);
		//WHEN
		Mockito.when(this.repo.findAll()).thenReturn(list);
		//THEN
		assertThat(this.service.getAll()).isEqualTo(list);
		//Verify
		Mockito.verify(this.repo, Mockito.times(1)).findAll();
	}
	
	@Test
	void getByIdTest() {
		//GIVEN
		long id = 1;
		Dragon x = new Dragon(1L, 1, "Example", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		//WHEN
		Mockito.when(this.repo.findById(id)).thenReturn(Optional.of(x));
		//THEN
		assertThat(this.service.getById(id)).isEqualTo(x);
		//Verify
		Mockito.verify(this.repo, Mockito.times(1)).findById(Mockito.anyLong());
	}
	
	//UPDATE
	@Test
	void updateByIdTest() {
		//GIVEN
		long id = 1;
		Dragon x = new Dragon(1L, 1, "Example1", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		Dragon update = new Dragon("Example2", "Female", "Blue", 2.0, 3.0, 4.0, 3.0, 2.0);
		Dragon y = new Dragon(1L, 1, "Example2", "Female", "Blue", 2.0, 3.0, 4.0, 3.0, 2.0);
		//WHEN
		Mockito.when(this.repo.findById(id)).thenReturn(Optional.of(x));
		Mockito.when(this.repo.save(y)).thenReturn(y);
		//THEN
		assertThat(this.service.updateById(update, id)).isEqualTo(y);
		//Verify
		Mockito.verify(this.repo, Mockito.times(1)).findById(Mockito.anyLong());
		Mockito.verify(this.repo, Mockito.times(1)).save(Mockito.any(Dragon.class));
	}
	
	//DELETE
	@Test
	void deleteById() {
		//GIVEN
		long id = 1;
		Dragon x = new Dragon(1L, 1, "Example1", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		//WHEN
		Mockito.when(this.repo.findById(id)).thenReturn(Optional.of(x));
//		Mockito.doThrow(new IllegalArgumentException("delete"))
//			   .when(this.repo)
//			   .deleteById(id);
		Mockito.doNothing()
			   .when(this.repo)
			   .deleteById(id);	
		//THEN
		assertThat(this.service.deleteById(id)).isNotBlank();
		//Verify
		Mockito.verify(this.repo, Mockito.times(1)).findById(Mockito.anyLong());
		Mockito.verify(this.repo, Mockito.times(1)).deleteById(Mockito.anyLong());
	}
	
	@Test
	void clearDbTest() {
		//GIVEN
		long count = 1;
		//WHEN
		Mockito.when(this.repo.count()).thenReturn(count);
		Mockito.doNothing()
		   .when(this.repo)
		   .deleteAll();	
		//THEN
		assertThat(this.service.clearDb()).isNotBlank();
		//Verify
		Mockito.verify(this.repo, Mockito.times(1)).count();
		Mockito.verify(this.repo, Mockito.times(1)).deleteAll();
	}
	
	// ================ CUSTOM QUERIES ===================== //
	
	@Test
	void createRandomTest() {
		//GIVEN
		String name = "Example";
		String sex = "Male";
		Dragon x = new Dragon(1L, 1, "Example", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		//WHEN
		Mockito.when(this.repo.save(Mockito.any(Dragon.class))).thenReturn(x);
		//THEN
		assertThat(this.service.createRandom(name, sex)).isEqualTo(x);
		//Verify
		Mockito.verify(this.repo, Mockito.times(1)).save(Mockito.any(Dragon.class));
	}
	
	@Test
	void breedTest() {
		//GIVEN
		long xId = 1;
		Dragon x = new Dragon(1L, 1, "Example1", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		long yId = 2;
		Dragon y = new Dragon(2L, 1, "Example2", "Female", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		Dragon xy = new Dragon(3L, 2, "unnamed", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		String viableMessage = "viable";
		//WHEN
		Mockito.when(this.repo.findById(xId)).thenReturn(Optional.of(x));
		Mockito.when(this.repo.findById(yId)).thenReturn(Optional.of(y));
		Mockito.when(this.breedFunction.checkViable(x, y)).thenReturn(viableMessage);
		Mockito.when(this.breedFunction.breed(x, y)).thenReturn(xy);
		Mockito.when(this.repo.save(Mockito.any(Dragon.class))).thenReturn(xy);
		Mockito.when(this.repo.findTopByOrderByIdDesc()).thenReturn(xy);
		//THEN
		assertThat(this.service.breed(xId, yId)).isNotBlank();
		//Verify
		Mockito.verify(this.repo, Mockito.times(2)).findById(Mockito.anyLong());
		Mockito.verify(this.breedFunction, Mockito.times(1)).checkViable(Mockito.any(Dragon.class), Mockito.any(Dragon.class));
		Mockito.verify(this.breedFunction, Mockito.times(1)).breed(Mockito.any(Dragon.class), Mockito.any(Dragon.class));
		Mockito.verify(this.repo, Mockito.times(1)).save(Mockito.any(Dragon.class));
		Mockito.verify(this.repo, Mockito.times(1)).findTopByOrderByIdDesc();
	}
	
	@Test
	void breedTestFailure1() {
		//GIVEN
		long xId = 1;
		Dragon x = new Dragon(1L, 1, "Example1", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		long yId = 2;
		Dragon y = new Dragon(2L, 1, "Example2", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		String viableMessage = "Dragons not breedable (same sex)";
		//WHEN
		Mockito.when(this.repo.findById(xId)).thenReturn(Optional.of(x));
		Mockito.when(this.repo.findById(yId)).thenReturn(Optional.of(y));
		Mockito.when(this.breedFunction.checkViable(x, y)).thenReturn(viableMessage);
		//THEN
		assertThat(this.service.breed(xId, yId)).isNotBlank();
		//Verify
		Mockito.verify(this.repo, Mockito.times(2)).findById(Mockito.anyLong());
		Mockito.verify(this.breedFunction, Mockito.times(1)).checkViable(Mockito.any(Dragon.class), Mockito.any(Dragon.class));
	}
	
	@Test
	void breedTestFailure2() {
		//GIVEN
		long xId = 1;
		Dragon x = new Dragon(1L, 1, "Example1", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		long yId = 2;
		Dragon y = new Dragon(2L, 2, "Example2", "Female", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		String viableMessage = "Dragons not breedable (different generations)";
		//WHEN
		Mockito.when(this.repo.findById(xId)).thenReturn(Optional.of(x));
		Mockito.when(this.repo.findById(yId)).thenReturn(Optional.of(y));
		Mockito.when(this.breedFunction.checkViable(x, y)).thenReturn(viableMessage);
		//THEN
		assertThat(this.service.breed(xId, yId)).isNotBlank();
		//Verify
		Mockito.verify(this.repo, Mockito.times(2)).findById(Mockito.anyLong());
		Mockito.verify(this.breedFunction, Mockito.times(1)).checkViable(Mockito.any(Dragon.class), Mockito.any(Dragon.class));
	}
	
	@Test
	void renameTest() {
		//GIVEN
		long id = 1;
		String name = "Example2";
		Dragon x = new Dragon(1L, 1, "Example1", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		Dragon y = new Dragon(1L, 1, "Example2", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		//WHEN
		Mockito.when(this.repo.findById(id)).thenReturn(Optional.of(x));
		Mockito.when(this.repo.save(y)).thenReturn(y);
		//THEN
		assertThat(this.service.rename(id, name)).isEqualTo(y);
		//Verify
		Mockito.verify(this.repo, Mockito.times(1)).findById(Mockito.anyLong());
		Mockito.verify(this.repo, Mockito.times(1)).save(Mockito.any(Dragon.class));
	}
	
	@Test
	void getByGenerationTest() {
		//GIVEN
		int generation = 2;
		Dragon x = new Dragon(1L, 2, "Example1", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		Dragon y = new Dragon(2L, 2, "Example2", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		Dragon z = new Dragon(3L, 2, "Example3", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		List<Dragon> list = new ArrayList<Dragon>();
		list.add(x);
		list.add(y);
		list.add(z);
		//WHEN
		Mockito.when(this.repo.findDragonByGeneration(generation)).thenReturn(list);
		//THEN
		assertThat(this.service.getByGeneration(generation)).isEqualTo(list);
		//Verify
		Mockito.verify(this.repo, Mockito.times(1)).findDragonByGeneration(Mockito.anyInt());
	}
	
	@Test
	void getBestOfScaleQualityTest() {
		//GIVEN
		Dragon x = new Dragon(1L, 1, "Example1", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		Dragon y = new Dragon(2L, 1, "Example2", "Male", "Red", 3.0, 3.0, 3.0, 3.0, 3.0);
		Dragon z = new Dragon(3L, 1, "Example3", "Male", "Red", 4.0, 4.0, 4.0, 4.0, 4.0);
		List<Dragon> list = new ArrayList<Dragon>();
		list.add(x);
		list.add(y);
		list.add(z);
		list.sort(Comparator.comparing(Dragon::getScaleQuality).reversed());
		//WHEN
		Mockito.when(this.repo.findTop10ByOrderByScaleQualityDesc()).thenReturn(list);
		//THEN
		assertThat(this.service.getBestOfScaleQuality()).isEqualTo(list);
		//Verify
		Mockito.verify(this.repo, Mockito.times(1)).findTop10ByOrderByScaleQualityDesc();
	}
	
	@Test
	void getBestOfFlyingSpeedTest() {
		//GIVEN
		Dragon x = new Dragon(1L, 1, "Example1", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		Dragon y = new Dragon(2L, 1, "Example2", "Male", "Red", 3.0, 3.0, 3.0, 3.0, 3.0);
		Dragon z = new Dragon(3L, 1, "Example3", "Male", "Red", 4.0, 4.0, 4.0, 4.0, 4.0);
		List<Dragon> list = new ArrayList<Dragon>();
		list.add(x);
		list.add(y);
		list.add(z);
		list.sort(Comparator.comparing(Dragon::getFlyingSpeed).reversed());
		//WHEN
		Mockito.when(this.repo.findTop10ByOrderByFlyingSpeedDesc()).thenReturn(list);
		//THEN
		assertThat(this.service.getBestOfFlyingSpeed()).isEqualTo(list);
		//Verify
		Mockito.verify(this.repo, Mockito.times(1)).findTop10ByOrderByFlyingSpeedDesc();
	}
	
	@Test
	void getBestOfEggSizeTest() {
		//GIVEN
		Dragon x = new Dragon(1L, 1, "Example1", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		Dragon y = new Dragon(2L, 1, "Example2", "Male", "Red", 3.0, 3.0, 3.0, 3.0, 3.0);
		Dragon z = new Dragon(3L, 1, "Example3", "Male", "Red", 4.0, 4.0, 4.0, 4.0, 4.0);
		List<Dragon> list = new ArrayList<Dragon>();
		list.add(x);
		list.add(y);
		list.add(z);
		list.sort(Comparator.comparing(Dragon::getEggSize).reversed());
		//WHEN
		Mockito.when(this.repo.findTop10ByOrderByEggSizeDesc()).thenReturn(list);
		//THEN
		assertThat(this.service.getBestOfEggSize()).isEqualTo(list);
		//Verify
		Mockito.verify(this.repo, Mockito.times(1)).findTop10ByOrderByEggSizeDesc();
	}
	
	@Test
	void getBestOfEggQualityTest() {
		//GIVEN
		Dragon x = new Dragon(1L, 1, "Example1", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		Dragon y = new Dragon(2L, 1, "Example2", "Male", "Red", 3.0, 3.0, 3.0, 3.0, 3.0);
		Dragon z = new Dragon(3L, 1, "Example3", "Male", "Red", 4.0, 4.0, 4.0, 4.0, 4.0);
		List<Dragon> list = new ArrayList<Dragon>();
		list.add(x);
		list.add(y);
		list.add(z);
		list.sort(Comparator.comparing(Dragon::getEggQuality).reversed());
		//WHEN
		Mockito.when(this.repo.findTop10ByOrderByEggQualityDesc()).thenReturn(list);
		//THEN
		assertThat(this.service.getBestOfEggQuality()).isEqualTo(list);
		//Verify
		Mockito.verify(this.repo, Mockito.times(1)).findTop10ByOrderByEggQualityDesc();
	}
	
	@Test
	void getBestOfBreathTemperatureTest() {
		//GIVEN
		Dragon x = new Dragon(1L, 1, "Example1", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		Dragon y = new Dragon(2L, 1, "Example2", "Male", "Red", 3.0, 3.0, 3.0, 3.0, 3.0);
		Dragon z = new Dragon(3L, 1, "Example3", "Male", "Red", 4.0, 4.0, 4.0, 4.0, 4.0);
		List<Dragon> list = new ArrayList<Dragon>();
		list.add(x);
		list.add(y);
		list.add(z);
		list.sort(Comparator.comparing(Dragon::getBreathTemperature).reversed());
		//WHEN
		Mockito.when(this.repo.findTop10ByOrderByBreathTemperatureDesc()).thenReturn(list);
		//THEN
		assertThat(this.service.getBestOfBreathTemperature()).isEqualTo(list);
		//Verify
		Mockito.verify(this.repo, Mockito.times(1)).findTop10ByOrderByBreathTemperatureDesc();
	}
	
	@Test
	void getIdealPairsTest1() {
		//GIVEN
		String trait = "scaleQuality";
		List<String> pairs = new ArrayList<String>();
		Dragon y = new Dragon(2L, 1, "Example2", "Male", "Red", 3.0, 3.0, 3.0, 3.0, 3.0);
		Dragon z = new Dragon(3L, 1, "Example3", "Female", "Red", 4.0, 4.0, 4.0, 4.0, 4.0);
		List<Dragon> candidates = new ArrayList<Dragon>();
		candidates.add(y);
		candidates.add(z);
		candidates.sort(Comparator.comparing(Dragon::getScaleQuality).reversed());
		List<String> pairsOutput = new ArrayList<String>();
		pairsOutput.add("ID: 3, Example3, Quality: 4.0 / ID: 2, Example2, Quality: 3.0");
		//WHEN
		Mockito.when(this.repo.findTop10ByOrderByScaleQualityDesc()).thenReturn(candidates);
		Mockito.when(this.breedFunction.getPairs(trait, pairs, candidates)).thenReturn(pairsOutput);
		//THEN
		assertThat(this.service.getIdealPairs(trait).equals(pairsOutput));
		//Verify
		Mockito.verify(this.repo, Mockito.times(1)).findTop10ByOrderByScaleQualityDesc();
		Mockito.verify(this.breedFunction, Mockito.times(1))
					   .getPairs(Mockito.anyString(), Mockito.anyList(), Mockito.anyList());
	}
	
	@Test
	void getIdealPairsTest2() {
		//GIVEN
		String trait = "flyingSpeed";
		List<String> pairs = new ArrayList<String>();
		Dragon y = new Dragon(2L, 1, "Example2", "Male", "Red", 3.0, 3.0, 3.0, 3.0, 3.0);
		Dragon z = new Dragon(3L, 1, "Example3", "Female", "Red", 4.0, 4.0, 4.0, 4.0, 4.0);
		List<Dragon> candidates = new ArrayList<Dragon>();
		candidates.add(y);
		candidates.add(z);
		candidates.sort(Comparator.comparing(Dragon::getFlyingSpeed).reversed());
		List<String> pairsOutput = new ArrayList<String>();
		pairsOutput.add("ID: 3, Example3, Quality: 4.0 / ID: 2, Example2, Quality: 3.0");
		//WHEN
		Mockito.when(this.repo.findTop10ByOrderByFlyingSpeedDesc()).thenReturn(candidates);
		Mockito.when(this.breedFunction.getPairs(trait, pairs, candidates)).thenReturn(pairsOutput);
		//THEN
		assertThat(this.service.getIdealPairs(trait).equals(pairsOutput));
		//Verify
		Mockito.verify(this.repo, Mockito.times(1)).findTop10ByOrderByFlyingSpeedDesc();
		Mockito.verify(this.breedFunction, Mockito.times(1))
					   .getPairs(Mockito.anyString(), Mockito.anyList(), Mockito.anyList());
	}
	
	@Test
	void getIdealPairsTest3() {
		//GIVEN
		String trait = "eggSize";
		List<String> pairs = new ArrayList<String>();
		Dragon y = new Dragon(2L, 1, "Example2", "Male", "Red", 3.0, 3.0, 3.0, 3.0, 3.0);
		Dragon z = new Dragon(3L, 1, "Example3", "Female", "Red", 4.0, 4.0, 4.0, 4.0, 4.0);
		List<Dragon> candidates = new ArrayList<Dragon>();
		candidates.add(y);
		candidates.add(z);
		candidates.sort(Comparator.comparing(Dragon::getEggSize).reversed());
		List<String> pairsOutput = new ArrayList<String>();
		pairsOutput.add("ID: 3, Example3, Quality: 4.0 / ID: 2, Example2, Quality: 3.0");
		//WHEN
		Mockito.when(this.repo.findTop10ByOrderByEggSizeDesc()).thenReturn(candidates);
		Mockito.when(this.breedFunction.getPairs(trait, pairs, candidates)).thenReturn(pairsOutput);
		//THEN
		assertThat(this.service.getIdealPairs(trait).equals(pairsOutput));
		//Verify
		Mockito.verify(this.repo, Mockito.times(1)).findTop10ByOrderByEggSizeDesc();
		Mockito.verify(this.breedFunction, Mockito.times(1))
					   .getPairs(Mockito.anyString(), Mockito.anyList(), Mockito.anyList());
	}
	
	@Test
	void getIdealPairsTest4() {
		//GIVEN
		String trait = "eggQuality";
		List<String> pairs = new ArrayList<String>();
		Dragon y = new Dragon(2L, 1, "Example2", "Male", "Red", 3.0, 3.0, 3.0, 3.0, 3.0);
		Dragon z = new Dragon(3L, 1, "Example3", "Female", "Red", 4.0, 4.0, 4.0, 4.0, 4.0);
		List<Dragon> candidates = new ArrayList<Dragon>();
		candidates.add(y);
		candidates.add(z);
		candidates.sort(Comparator.comparing(Dragon::getEggQuality).reversed());
		List<String> pairsOutput = new ArrayList<String>();
		pairsOutput.add("ID: 3, Example3, Quality: 4.0 / ID: 2, Example2, Quality: 3.0");
		//WHEN
		Mockito.when(this.repo.findTop10ByOrderByEggQualityDesc()).thenReturn(candidates);
		Mockito.when(this.breedFunction.getPairs(trait, pairs, candidates)).thenReturn(pairsOutput);
		//THEN
		assertThat(this.service.getIdealPairs(trait).equals(pairsOutput));
		//Verify
		Mockito.verify(this.repo, Mockito.times(1)).findTop10ByOrderByEggQualityDesc();
		Mockito.verify(this.breedFunction, Mockito.times(1))
					   .getPairs(Mockito.anyString(), Mockito.anyList(), Mockito.anyList());
	}
	
	@Test
	void getIdealPairsTest5() {
		//GIVEN
		String trait = "breathTemperature";
		List<String> pairs = new ArrayList<String>();
		Dragon y = new Dragon(2L, 1, "Example2", "Male", "Red", 3.0, 3.0, 3.0, 3.0, 3.0);
		Dragon z = new Dragon(3L, 1, "Example3", "Female", "Red", 4.0, 4.0, 4.0, 4.0, 4.0);
		List<Dragon> candidates = new ArrayList<Dragon>();
		candidates.add(y);
		candidates.add(z);
		candidates.sort(Comparator.comparing(Dragon::getBreathTemperature).reversed());
		List<String> pairsOutput = new ArrayList<String>();
		pairsOutput.add("ID: 3, Example3, Quality: 4.0 / ID: 2, Example2, Quality: 3.0");
		//WHEN
		Mockito.when(this.repo.findTop10ByOrderByBreathTemperatureDesc()).thenReturn(candidates);
		Mockito.when(this.breedFunction.getPairs(trait, pairs, candidates)).thenReturn(pairsOutput);
		//THEN
		assertThat(this.service.getIdealPairs(trait).equals(pairsOutput));
		//Verify
		Mockito.verify(this.repo, Mockito.times(1)).findTop10ByOrderByBreathTemperatureDesc();
		Mockito.verify(this.breedFunction, Mockito.times(1))
					   .getPairs(Mockito.anyString(), Mockito.anyList(), Mockito.anyList());
	}	
}
