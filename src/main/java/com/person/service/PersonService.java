package com.person.service;

import java.util.List;

import com.person.entity.Person;
import com.person.request.PersonRequest;

public interface PersonService {

	Object savePersonDetails(PersonRequest personRequest);

	List<Person> getPersonList();

	Object getPersonDetailsById(Integer id);

	Object deletePersonDetails(Integer id);

	Object updatePersonDetails(PersonRequest personRequest);

}
