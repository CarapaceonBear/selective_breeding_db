package com.qa.starterproject.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.qa.starterproject.persistence.Dragon;
import com.qa.starterproject.service.DragonService;

@RestController
public class DragonController {

	private DragonService service;
	
	public DragonController(DragonService service) {
		super();
		this.service = service;
	}
	
	@PostMapping("/create")
	public ResponseEntity<Dragon> create(@RequestBody Dragon d) {
		return new ResponseEntity<Dragon>(this.service.create(d), HttpStatus.CREATED);
	}
		
	@GetMapping("/readAll")
	public ResponseEntity<List<Dragon>> readAll() {
		return new ResponseEntity<List<Dragon>>(this.service.getAll(), HttpStatus.FOUND);
	}
	
	@GetMapping("/read/{x}")
	public ResponseEntity<Dragon> readById(@PathVariable long x) {
		return new ResponseEntity<Dragon>(this.service.getById(x), HttpStatus.FOUND);
	}
	
	@PutMapping("/update/{x}")
	public ResponseEntity<Dragon> updateDragon(@RequestBody Dragon d, @PathVariable long x) {
		return new ResponseEntity<Dragon>(this.service.updateById(d, x), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/delete/{x}")
	public ResponseEntity<String> deleteDragon(@PathVariable long x) {
		return new ResponseEntity<String>(this.service.deleteById(x), HttpStatus.ACCEPTED);
	}
	
	// ================ CUSTOM QUERIES ===================== //
	@PostMapping("/createRandom/{name}/{sex}")
	public ResponseEntity<Dragon> create(@PathVariable String name, @PathVariable String sex) {
		return new ResponseEntity<Dragon>(this.service.createRandom(name, sex), HttpStatus.CREATED);
	}
	
	@PostMapping("/breed/{a}/{b}")
	public ResponseEntity<String> breedDragons(@PathVariable long a, @PathVariable long b) {
		return new ResponseEntity<String>(this.service.breed(a, b), HttpStatus.CREATED);
	}
	
	@PutMapping("/rename/{x}/{name}")
	public ResponseEntity<Dragon> rename(@PathVariable long x, @PathVariable String name) {
		return new ResponseEntity<Dragon>(this.service.rename(x, name), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/readAllOfGeneration/{g}")
	public ResponseEntity<List<Dragon>> readAllOfGeneration(@PathVariable int g) {
		return new ResponseEntity<List<Dragon>>(this.service.getByGeneration(g), HttpStatus.FOUND);
	}
	
}
