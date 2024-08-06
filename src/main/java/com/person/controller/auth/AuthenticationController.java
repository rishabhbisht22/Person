package com.person.controller.auth;

import com.person.constants.ErrorConstants;
import com.person.entity.Person;
import com.person.entity.Role;
import com.person.repository.PersonRepository;
import com.person.request.LoginRequest;
import com.person.response.Response;
import com.person.responseDto.JwtResponse;
import com.person.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationController {

     private final PersonRepository personRepository;
     private final JwtUtils jwtUtils;
     private final AuthenticationManager authenticationManager;

     @PostMapping("signIn")
    public ResponseEntity<?> authenticatePerson(@RequestBody LoginRequest loginRequest) {
         Authentication authentication = authenticationManager.authenticate(
                 new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
         if(!authentication.isAuthenticated()) {
             return ResponseEntity.ok().body(new Response<>(false, ErrorConstants.PERSON_NOT_FOUND));
         }
         SecurityContextHolder.getContext().setAuthentication(authentication);
         String jwt = jwtUtils.generateToken(authentication);

         Optional<Person> findPerson = personRepository.findByEmail(loginRequest.getEmail());
         if(findPerson.isEmpty()) {
              return ResponseEntity.ok().body(new Response<>(false, ErrorConstants.PERSON_NOT_FOUND));
         }
         Set<Role> findRoles = findPerson.get().getRole();
         if(CollectionUtils.isEmpty(findRoles)) {
              return ResponseEntity.ok().body(new Response<>(false, ErrorConstants.ROLE_NOT_FOUND));
         }
         return ResponseEntity.ok().body(new JwtResponse(findPerson.get().getId(), findPerson.get().getFirstName(),
                 findPerson.get().getEmail(), findRoles, jwt));
     }
}
