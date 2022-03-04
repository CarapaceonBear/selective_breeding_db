package com.qa.starterproject.rest;

import org.springframework.web.bind.annotation.RestController;

import com.qa.starterproject.service.DragonService;

@RestController
public class DragonController {

	private DragonService service;
	
	public DragonController(DragonService service) {
		super();
		this.service = service;
	}
	
	@PostMapping("/create")
	//
	@GetMapping("/read")
	//
	@PutMapping("/update")
	//
	@DeleteMapping("/delete")
	//
}
