package com.person.security;

import com.person.entity.Person;
import com.person.exception.PersonNotFound;
import com.person.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PersonDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<Person> person = personRepository.findByEmail(username);
        if (person.isEmpty()) {
            throw new PersonNotFound("Person not found with email "+username);
        }
        return PersonDetailsImpl.build(person.get());
    }
}
