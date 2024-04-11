package com.example.demo;

import com.example.demo.concept.domain.Concept;
import com.example.demo.concept.domain.ConceptTitle;
import com.example.demo.concept.request.ConceptCreationRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private UbiquitousLanguageController ubiquitousLanguageController;
	@Test
	void contextLoads() {
	}
//	@Test
//	void beAbleToCreateAConcept() {
//		ResponseEntity<Concept> createdConcept = ubiquitousLanguageController.createConcept(new ConceptCreationRequest("Hey"));
//		Assertions.assertEquals(createdConcept.getStatusCode(), HttpStatus.OK);
//
//		Assertions.assertTrue(createdConcept.hasBody());
//
//		//noinspection ConstantConditions
//		Assertions.assertEquals(createdConcept.getBody().conceptTitle, new ConceptTitle("Hey"));
//
//		Assertions.assertNotNull(createdConcept.getBody().conceptId);
//	}

//	@Test
//	void twoConceptsShouldNotShareSameId() {
//		ResponseEntity<Concept> createdConceptOne = ubiquitousLanguageController.createConcept(new ConceptCreationRequest("Hey"));
//		ResponseEntity<Concept> createdConceptTwo = ubiquitousLanguageController.createConcept(new ConceptCreationRequest("Hey"));
//
//		Assertions.assertEquals(createdConceptOne.getStatusCode(), HttpStatus.OK);
//		Assertions.assertEquals(createdConceptTwo.getStatusCode(), HttpStatus.OK);
//
//		Assertions.assertTrue(createdConceptOne.hasBody());
//		Assertions.assertTrue(createdConceptTwo.hasBody());
//
//		//noinspection ConstantConditions
//		Assertions.assertEquals(createdConceptOne.getBody().conceptTitle, new ConceptTitle("Hey"));
//		//noinspection ConstantConditions
//		Assertions.assertEquals(createdConceptTwo.getBody().conceptTitle, new ConceptTitle("Hey"));
//
//		Assertions.assertNotNull(createdConceptOne.getBody().conceptId);
//		Assertions.assertNotNull(createdConceptTwo.getBody().conceptId);
//		Assertions.assertNotEquals(createdConceptOne.getBody().conceptId, createdConceptTwo.getBody().conceptId);
//
//	}

}
