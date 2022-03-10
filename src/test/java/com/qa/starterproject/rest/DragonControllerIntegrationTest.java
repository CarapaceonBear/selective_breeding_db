package com.qa.starterproject.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.starterproject.persistence.Dragon;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = {"classpath:dragon-schema.sql", "classpath:dragon-data.sql"},
		executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@AutoConfigureMockMvc

public class DragonControllerIntegrationTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	// =================== BASIC CRUD ====================== //
	
	@Test
	void createIntegrationTest() throws Exception {
		Dragon x = new Dragon("Example", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		String xJSON = this.mapper.writeValueAsString(x);
		Dragon y = new Dragon(5L, 1, "Example", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		String yJSON = this.mapper.writeValueAsString(y);
		
		RequestBuilder request = post("/create").contentType(MediaType.APPLICATION_JSON).content(xJSON);
		
		ResultMatcher responseStatus = status().isCreated();
		ResultMatcher responseContent = content().json(yJSON);
		
		this.mvc.perform(request).andExpect(responseStatus).andExpect(responseContent);		
	}
	
	@Test
	void readAllIntegrationTest() throws Exception {
		List<Dragon> populated = new ArrayList<>();
		populated.add(new Dragon(1L, 1, "Example1", "Male", "Red", 1.0, 1.0, 1.0, 1.0, 1.0));
		populated.add(new Dragon(2L, 1, "Example2", "Female", "Blue", 2.0, 2.0, 2.0, 2.0, 2.0));
		populated.add(new Dragon(3L, 2, "Example3", "Male", "Green", 3.0, 3.0, 3.0, 3.0, 3.0));
		populated.add(new Dragon(4L, 2, "Example4", "Female", "Red", 4.0, 4.0, 4.0, 4.0, 4.0));
		String populatedJSON = this.mapper.writeValueAsString(populated);
		
		RequestBuilder request = get("/readAll");
		
		ResultMatcher responseStatus = status().isFound();
		ResultMatcher responseContent = content().json(populatedJSON);
		
		this.mvc.perform(request).andExpect(responseStatus).andExpect(responseContent);		
	}
	
	@Test
	void readByIdIntegrationTest() throws Exception {
		Dragon x = new Dragon(1L, 1, "Example1", "Male", "Red", 1.0, 1.0, 1.0, 1.0, 1.0);
		String xJSON = this.mapper.writeValueAsString(x);
		
		RequestBuilder request = get("/read/1");
		
		ResultMatcher responseStatus = status().isFound();
		ResultMatcher reponseContent = content().json(xJSON);
		
		this.mvc.perform(request).andExpect(responseStatus).andExpect(reponseContent);
	}
	
	@Test
	void updateDragonIntegrationTest() throws Exception {
		Dragon x = new Dragon("ExampleNew", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		String xJSON = this.mapper.writeValueAsString(x);
		Dragon y = new Dragon(1L, 1, "ExampleNew", "Male", "Red", 2.0, 2.0, 2.0, 2.0, 2.0);
		String yJSON = this.mapper.writeValueAsString(y);
		
		RequestBuilder request = put("/update/1")
								.contentType(MediaType.APPLICATION_JSON)
								.content(xJSON);
		
		ResultMatcher responseStatus = status().isAccepted();
		ResultMatcher responseContent = content().json(yJSON);
		
		this.mvc.perform(request).andExpect(responseStatus).andExpect(responseContent);				
	}
	
	@Test
	void deleteDragonIntegrationTest() throws Exception {
		String response = "Example1 deleted at index 1";
		
		RequestBuilder request = delete("/delete/1");
		
		ResultMatcher responseStatus = status().isAccepted();
		ResultMatcher responseContent = content().string(response);
		
		this.mvc.perform(request).andExpect(responseStatus).andExpect(responseContent);
	}
	
	@Test
	void clearDbIntegrationTest() throws Exception {
		String response = "Database cleared. 4 entities removed.";
		
		RequestBuilder request = delete("/clearDb");
		
		ResultMatcher responseStatus = status().isAccepted();
		ResultMatcher responseContent = content().string(response);
		
		this.mvc.perform(request).andExpect(responseStatus).andExpect(responseContent);
	}
	
	// ================ CUSTOM QUERIES ===================== //
	
	@Test
	void createRandomIntegrationTest() throws Exception {
		RequestBuilder request = post("/createRandom/Example/Male");
		RequestBuilder requestRead = get("/read/5");
		
		ResultMatcher responseStatus = status().isCreated();
		ResultMatcher responseStatusRead = status().isFound();
		
		this.mvc.perform(request).andExpect(responseStatus);
		this.mvc.perform(requestRead).andExpect(responseStatusRead);
	}
	
	@Test
	// test that a new dragon is made when /breed is called
	// don't need to test the random values, that's covered by the unit test
	// however, do need to /update the new dragon, to overwrite the random values
	void breedDragonsIntegrationTest() throws Exception {
		String response1 = "New dragon born, a Male, saved at index 5";
		String response2 = "New dragon born, a Female, saved at index 5";
		Dragon x = new Dragon("Newborn", "Male", "Red", 1.0, 1.0, 1.0, 1.0, 1.0);
		String xJSON = this.mapper.writeValueAsString(x);
		Dragon y = new Dragon("Newborn", "Female", "Red", 1.0, 1.0, 1.0, 1.0, 1.0);
		String yJSON = this.mapper.writeValueAsString(y);
		//if male
		Dragon xRead = new Dragon(5L, 2, "Newborn", "Male", "Red", 1.0, 1.0, 1.0, 1.0, 1.0);
		String xReadJSON = this.mapper.writeValueAsString(xRead);
		//if female
		Dragon yRead = new Dragon(5L, 2, "Newborn", "Female", "Red", 1.0, 1.0, 1.0, 1.0, 1.0);
		String yReadJSON = this.mapper.writeValueAsString(yRead);
		
		RequestBuilder request = post("/breed/1/2/");
		RequestBuilder requestUpdateMale = put("/update/5")
											.contentType(MediaType.APPLICATION_JSON)
											.content(xJSON);
		RequestBuilder requestUpdateFemale = put("/update/5")
											.contentType(MediaType.APPLICATION_JSON)
											.content(yJSON);
		RequestBuilder requestRead = get("/read/5");
		
		ResultMatcher responseStatus = status().isCreated();
		ResultMatcher responseStatusRead = status().isFound();
		ResultMatcher responseContentRead1 = content().json(xReadJSON);
		ResultMatcher responseContentRead2 = content().json(yReadJSON);
		
		String testOutput = this.mvc.perform(request).andExpect(responseStatus)
							.andReturn().getResponse().getContentAsString();
		if (testOutput.equals(response1)) {
			// if male
			this.mvc.perform(requestUpdateMale);
			this.mvc.perform(requestRead).andExpect(responseStatusRead).andExpect(responseContentRead1);
		} else if (testOutput.equals(response2)) {
			// if female
			this.mvc.perform(requestUpdateFemale);
			this.mvc.perform(requestRead).andExpect(responseStatusRead).andExpect(responseContentRead2);
		}
	}
	
	@Test
	void renameIntegrationTest() throws Exception {
		Dragon y = new Dragon(1L, 1, "NewName", "Male", "Red", 1.0, 1.0, 1.0, 1.0, 1.0);
		String yJSON = this.mapper.writeValueAsString(y);
		
		RequestBuilder request = put("/rename/1/NewName");
		
		ResultMatcher responseStatus = status().isAccepted();
		ResultMatcher responseContent = content().json(yJSON);
		
		this.mvc.perform(request).andExpect(responseStatus).andExpect(responseContent);				
	}
	
	@Test
	void readAllOfGenerationIntegrationTest() throws Exception {
		List<Dragon> foundList = new ArrayList<>();
		foundList.add(new Dragon(3L, 2, "Example3", "Male", "Green", 3.0, 3.0, 3.0, 3.0, 3.0));
		foundList.add(new Dragon(4L, 2, "Example4", "Female", "Red", 4.0, 4.0, 4.0, 4.0, 4.0));
		String foundListJSON = this.mapper.writeValueAsString(foundList);
		
		RequestBuilder request = get("/readAllOfGeneration/2");
		
		ResultMatcher responseStatus = status().isFound();
		ResultMatcher responseContent = content().json(foundListJSON);
		
		this.mvc.perform(request).andExpect(responseStatus).andExpect(responseContent);	
	}
	
	@Test
	void readBestOfScaleQualityIntegrationTest() throws Exception {
		List<Dragon> foundList = new ArrayList<>();
		foundList.add(new Dragon(1L, 1, "Example1", "Male", "Red", 1.0, 1.0, 1.0, 1.0, 1.0));
		foundList.add(new Dragon(2L, 1, "Example2", "Female", "Blue", 2.0, 2.0, 2.0, 2.0, 2.0));
		foundList.add(new Dragon(3L, 2, "Example3", "Male", "Green", 3.0, 3.0, 3.0, 3.0, 3.0));
		foundList.add(new Dragon(4L, 2, "Example4", "Female", "Red", 4.0, 4.0, 4.0, 4.0, 4.0));
		String foundListJSON = this.mapper.writeValueAsString(foundList);
		foundList.sort(Comparator.comparing(Dragon::getScaleQuality).reversed());
		
		RequestBuilder request = get("/readBestOfScaleQuality");
		
		ResultMatcher responseStatus = status().isFound();
		ResultMatcher responseContent = content().json(foundListJSON);
		
		this.mvc.perform(request).andExpect(responseStatus).andExpect(responseContent);	
	}
	
	@Test
	void readBestOfFlyingSpeedIntegrationTest() throws Exception {
		List<Dragon> foundList = new ArrayList<>();
		foundList.add(new Dragon(1L, 1, "Example1", "Male", "Red", 1.0, 1.0, 1.0, 1.0, 1.0));
		foundList.add(new Dragon(2L, 1, "Example2", "Female", "Blue", 2.0, 2.0, 2.0, 2.0, 2.0));
		foundList.add(new Dragon(3L, 2, "Example3", "Male", "Green", 3.0, 3.0, 3.0, 3.0, 3.0));
		foundList.add(new Dragon(4L, 2, "Example4", "Female", "Red", 4.0, 4.0, 4.0, 4.0, 4.0));
		String foundListJSON = this.mapper.writeValueAsString(foundList);
		foundList.sort(Comparator.comparing(Dragon::getFlyingSpeed).reversed());
		
		RequestBuilder request = get("/readBestOfFlyingSpeed");
		
		ResultMatcher responseStatus = status().isFound();
		ResultMatcher responseContent = content().json(foundListJSON);
		
		this.mvc.perform(request).andExpect(responseStatus).andExpect(responseContent);	
	}
	
	@Test
	void readBestOfEggSizeIntegrationTest() throws Exception {
		List<Dragon> foundList = new ArrayList<>();
		foundList.add(new Dragon(1L, 1, "Example1", "Male", "Red", 1.0, 1.0, 1.0, 1.0, 1.0));
		foundList.add(new Dragon(2L, 1, "Example2", "Female", "Blue", 2.0, 2.0, 2.0, 2.0, 2.0));
		foundList.add(new Dragon(3L, 2, "Example3", "Male", "Green", 3.0, 3.0, 3.0, 3.0, 3.0));
		foundList.add(new Dragon(4L, 2, "Example4", "Female", "Red", 4.0, 4.0, 4.0, 4.0, 4.0));
		String foundListJSON = this.mapper.writeValueAsString(foundList);
		foundList.sort(Comparator.comparing(Dragon::getEggSize).reversed());
		
		RequestBuilder request = get("/readBestOfEggSize");
		
		ResultMatcher responseStatus = status().isFound();
		ResultMatcher responseContent = content().json(foundListJSON);
		
		this.mvc.perform(request).andExpect(responseStatus).andExpect(responseContent);	
	}
	
	@Test
	void readBestOfEggQualityIntegrationTest() throws Exception {
		List<Dragon> foundList = new ArrayList<>();
		foundList.add(new Dragon(1L, 1, "Example1", "Male", "Red", 1.0, 1.0, 1.0, 1.0, 1.0));
		foundList.add(new Dragon(2L, 1, "Example2", "Female", "Blue", 2.0, 2.0, 2.0, 2.0, 2.0));
		foundList.add(new Dragon(3L, 2, "Example3", "Male", "Green", 3.0, 3.0, 3.0, 3.0, 3.0));
		foundList.add(new Dragon(4L, 2, "Example4", "Female", "Red", 4.0, 4.0, 4.0, 4.0, 4.0));
		String foundListJSON = this.mapper.writeValueAsString(foundList);
		foundList.sort(Comparator.comparing(Dragon::getEggQuality).reversed());
		
		RequestBuilder request = get("/readBestOfEggQuality");
		
		ResultMatcher responseStatus = status().isFound();
		ResultMatcher responseContent = content().json(foundListJSON);
		
		this.mvc.perform(request).andExpect(responseStatus).andExpect(responseContent);	
	}
	
	@Test
	void readBestOfBreathTemperatureIntegrationTest() throws Exception {
		List<Dragon> foundList = new ArrayList<>();
		foundList.add(new Dragon(1L, 1, "Example1", "Male", "Red", 1.0, 1.0, 1.0, 1.0, 1.0));
		foundList.add(new Dragon(2L, 1, "Example2", "Female", "Blue", 2.0, 2.0, 2.0, 2.0, 2.0));
		foundList.add(new Dragon(3L, 2, "Example3", "Male", "Green", 3.0, 3.0, 3.0, 3.0, 3.0));
		foundList.add(new Dragon(4L, 2, "Example4", "Female", "Red", 4.0, 4.0, 4.0, 4.0, 4.0));
		String foundListJSON = this.mapper.writeValueAsString(foundList);
		foundList.sort(Comparator.comparing(Dragon::getBreathTemperature).reversed());
		
		RequestBuilder request = get("/readBestOfBreathTemperature");
		
		ResultMatcher responseStatus = status().isFound();
		ResultMatcher responseContent = content().json(foundListJSON);
		
		this.mvc.perform(request).andExpect(responseStatus).andExpect(responseContent);	
	}
	
	@Test 
	void readIdealPairs() throws Exception {
		String output1 = "ID: 4, Example4, Quality: 4.0 / ID: 3, Example3, Quality: 3.0";
		String output2 = "ID: 2, Example2, Quality: 2.0 / ID: 1, Example1, Quality: 1.0";
		List<String> returnString = new ArrayList<String>();
		returnString.add(output1);
		returnString.add(output2);
		String returnJSON = this.mapper.writeValueAsString(returnString);
		
		RequestBuilder request = get("/readIdealPairs/scaleQuality");
		
		ResultMatcher responseStatus = status().isFound();
		ResultMatcher responseContent = content().json(returnJSON);
		
		this.mvc.perform(request).andExpect(responseStatus).andExpect(responseContent);
	}
}

