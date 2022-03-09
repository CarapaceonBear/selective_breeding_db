package com.qa.starterproject.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
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
	
}
