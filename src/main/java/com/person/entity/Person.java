package com.person.entity;



import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "person")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	
	@NotNull
	String firstName;
	
	@NotNull
	String lastName;
	
	@NotNull
	String mobile;
	
	@NotNull
	String email;
	
	@NotNull
	Integer age;

	@NotNull
	String password;

	@OneToMany(targetEntity = Address.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "person_id")
	List<Address> addresses;

	@ManyToOne
	Department department;

	@ManyToMany
	@JoinTable(name = "person_role",
		joinColumns = @JoinColumn(
			name = "person_id", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(
			name = "role_id", referencedColumnName = "id"))
	Set<Role> role;
}
