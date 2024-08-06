package com.person.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.person.constants.Constants;
import com.person.constants.ErrorConstants;
import com.person.entity.Person;
import com.person.request.PersonRequest;
import com.person.response.Response;
import com.person.service.PersonService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/person/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PersonController {

	private final PersonService personService;
	
	@PostMapping("savePerson")
	public ResponseEntity<Response> savePersonDetails(@RequestBody PersonRequest personRequest){
		
		Object data = personService.savePersonDetails(personRequest);
		if(ObjectUtils.isEmpty(data)) {
			log.debug("Error occurred while saving the person details");
			return ResponseEntity.ok(new Response<>(false, ErrorConstants.PERSON_SAVED_ERROR));
		}
		else if(data.equals(ErrorConstants.EMAIL_ALREADY_EXISTS)) {
			log.debug("Email id already exists and the given email is :{}",personRequest.getEmail());
			return ResponseEntity.ok(new Response<>(false, ErrorConstants.EMAIL_ALREADY_EXISTS));
		}
		else if(data.equals(ErrorConstants.DEPARTMENT_NOT_FOUND)) {
			log.debug("Department not found for this given department name :{}",personRequest.getDepartment().getName());
			return ResponseEntity.ok(new Response<>(false, ErrorConstants.DEPARTMENT_NOT_FOUND));
		}
		else if(data.equals(ErrorConstants.ROLE_NOT_FOUND)) {
			log.debug("Role data is not found");
			return ResponseEntity.ok(new Response<>(false, ErrorConstants.ROLE_NOT_FOUND));
		}
		log.debug("Person details saved successfully");
		return ResponseEntity.ok(new Response<>(true, Constants.PERSON_DETAILS_SAVED, data));
	}
	
	@GetMapping("getPerson")
	public ResponseEntity<Response> getPersonList(){
		
		List<Person> data = personService.getPersonList();
		if(CollectionUtils.isEmpty(data)) {
			log.debug("Person details list is empty");
			return ResponseEntity.ok(new Response<>(false, ErrorConstants.PERSON_LIST_EMPTY));
		}
		log.debug("Person details list fetched successfully");
		return ResponseEntity.ok(new Response<>(true, Constants.PERSON_LIST_FETCHED, data));
	}
	
	@GetMapping("getPersonById")
	public ResponseEntity<Response> getPersonDetailsById(@RequestParam Integer id){
		
		Object data = personService.getPersonDetailsById(id);
		if(ObjectUtils.isEmpty(data)) {
			log.debug("Person details not found for this given id :{}", id);
			return ResponseEntity.ok(new Response<>(false, ErrorConstants.PERSON_NOT_FOUND));
		}
		log.debug("Person details fetched successfully for this given id :{}",id);
		return ResponseEntity.ok(new Response<>(true, Constants.PERSON_DETAILS_FETCHED, data));
	}
	
	@DeleteMapping("deletePerson")
	public ResponseEntity<Response> deletePersonDetails(@RequestParam Integer id){
		
		Object data = personService.deletePersonDetails(id);
		if(data.equals(ErrorConstants.PERSON_NOT_FOUND)) {
			log.debug("Person details not found for this given id :{}",id);
			return ResponseEntity.ok(new Response<>(false, ErrorConstants.PERSON_NOT_FOUND));
		}
		log.debug("Person details deleted successfully for this given id :{}", id);
		return ResponseEntity.ok(new Response<>(true, Constants.PERSON_DETAILS_DELETED, data));
	}
	
	@PutMapping("updatePerson")
	public ResponseEntity<Response> updatePersonDetails(@RequestBody PersonRequest personRequest){
		
		Object data = personService.updatePersonDetails(personRequest);
		if(ObjectUtils.isEmpty(data)) {
			log.debug("Error occured while updating person details");
			return ResponseEntity.ok(new Response<>(false, ErrorConstants.PERSON_UPDATE_ERROR));
		}
		else if(data.equals(ErrorConstants.PERSON_NOT_FOUND)) {
			log.debug("Person details not found for this given id :{}",personRequest.getId());
			return ResponseEntity.ok(new Response<>(false, ErrorConstants.PERSON_NOT_FOUND));
		}
		else if (data.equals(ErrorConstants.EMAIL_ALREADY_EXISTS)) {
			log.debug("Email id already exists and the given email is :{}",personRequest.getEmail());
			return ResponseEntity.ok(new Response<>(false, ErrorConstants.EMAIL_ALREADY_EXISTS));
		}
		else if(data.equals(ErrorConstants.DEPARTMENT_NOT_FOUND)) {
			log.debug("Department data not found");
			return ResponseEntity.ok(new Response<>(false, ErrorConstants.DEPARTMENT_NOT_FOUND));
		}
		else if(data.equals(ErrorConstants.ROLE_NOT_FOUND)) {
			log.debug("Role data not found");
			return ResponseEntity.ok(new Response<>(false, ErrorConstants.ROLE_NOT_FOUND));
		}
		log.debug("Person details updated successfully for this given id :{}", personRequest.getId());
		return ResponseEntity.ok(new Response<>(true, Constants.PERSON_DETAILS_UPDATED, data));
	}
}
