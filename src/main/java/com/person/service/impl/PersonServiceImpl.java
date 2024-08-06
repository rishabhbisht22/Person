package com.person.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.person.entity.Department;
import com.person.entity.Role;
import com.person.repository.DepartmentRepository;
import com.person.repository.RoleRepository;
import com.person.request.RoleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.person.constants.ErrorConstants;
import com.person.entity.Person;
import com.person.repository.PersonRepository;
import com.person.request.PersonRequest;
import com.person.service.PersonService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class PersonServiceImpl implements PersonService {

	private final PersonRepository personRepo;
	private final DepartmentRepository departmentRepo;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Object savePersonDetails(PersonRequest personRequest) {
	
		Optional<Person> findByEmail = personRepo.findByEmail(personRequest.getEmail());
		if(ObjectUtils.isEmpty(findByEmail)) {

			Optional<Department> findDepartment = departmentRepo.findDepartmentByName(personRequest.getDepartment().getName().toUpperCase());
			if(findDepartment.isPresent()) {
				Set<RoleRequest> roles = personRequest.getRole();
				String roleName = roles.stream().map(a -> a.getName()).findAny().get();

				Set<Role> findRole = roleRepository.findAll().stream().filter(checkRole -> checkRole.getName()
						.equals(roleName)).collect(Collectors.toSet());
				if(!CollectionUtils.isEmpty(findRole)) {
					Person person = Person.builder()
							.firstName(personRequest.getFirstName())
							.lastName(personRequest.getLastName())
							.age(personRequest.getAge())
							.email(personRequest.getEmail())
							.password(passwordEncoder.encode(personRequest.getPassword()))
							.mobile(personRequest.getMobile())
							.addresses(personRequest.getAddress())
							.department(findDepartment.get())
							.role(findRole)
							.build();
					Person save = personRepo.save(person);
					return save;
				}
				return ErrorConstants.ROLE_NOT_FOUND;
			}
			return ErrorConstants.DEPARTMENT_NOT_FOUND;
		}
		return ErrorConstants.EMAIL_ALREADY_EXISTS;
	}

	@Override
	public List<Person> getPersonList() {
		
		List<Person> personList = personRepo.findAll();
		if(CollectionUtils.isEmpty(personList)) {
			return null;
		}
		return personList;
	}

	@Override
	public Object getPersonDetailsById(Integer id) {
		
		Optional<Person> personData = personRepo.findById(id);
		if(personData.isPresent()) {
			
			return personData;
		}
		return ErrorConstants.PERSON_NOT_FOUND;
	}

	@Override
	public Object deletePersonDetails(Integer id) {
	
		Optional<Person> personData = personRepo.findById(id);
		if(personData.isPresent()) {
			
			personRepo.deleteById(id);
			return id;
		}
		return ErrorConstants.PERSON_NOT_FOUND;
	}

	@Override
	public Object updatePersonDetails(PersonRequest personRequest) {
		
		Optional<Person> findPerson = personRepo.findById(personRequest.getId());
		if(ObjectUtils.isEmpty(findPerson)) {
			Optional<Person> findByEmail = personRepo.findByEmail(personRequest.getEmail());
			if (ObjectUtils.isEmpty(findByEmail) || findPerson.get().getId()==personRequest.getId()) {
				Optional<Department> findDepartment = departmentRepo.findDepartmentByName(personRequest.getDepartment().getName());
				if(findDepartment.isPresent()) {
					Set<RoleRequest> roles = personRequest.getRole();
					String roleName = roles.stream().map(a -> a.getName()).findAny().get();

					Set<Role> findRole = roleRepository.findAll().stream().filter(check -> check.getName().equalsIgnoreCase(roleName))
							.collect(Collectors.toSet());
					if(!CollectionUtils.isEmpty(findRole)) {
						findPerson.get().setFirstName(personRequest.getFirstName());
						findPerson.get().setLastName(personRequest.getLastName());
						findPerson.get().setAge(personRequest.getAge());
						findPerson.get().setEmail(personRequest.getEmail());
						findPerson.get().setMobile(personRequest.getMobile());
						findPerson.get().setAddresses(personRequest.getAddress());
						findPerson.get().setDepartment(findDepartment.get());
						findPerson.get().setRole(findRole);
						return personRepo.save(findPerson.get());
					}
					return ErrorConstants.ROLE_NOT_FOUND;
				}
				return ErrorConstants.DEPARTMENT_NOT_FOUND;
			}
			return ErrorConstants.EMAIL_ALREADY_EXISTS;
		}
		return ErrorConstants.PERSON_NOT_FOUND;
	}
}
