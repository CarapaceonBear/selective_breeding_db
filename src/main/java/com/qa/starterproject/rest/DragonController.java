package com.qa.starterproject.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
	//@GetMapping("/read")
	//
	//@PutMapping("/update")
	//
	//@DeleteMapping("/delete")
	//
}
