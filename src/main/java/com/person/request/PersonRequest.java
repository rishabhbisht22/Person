package com.person.request;

import com.person.entity.Address;
import com.person.entity.Department;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Set;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonRequest {

	@Nullable
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

	@Nullable
	String password;

	@NotNull
	List<Address> address;

	@NotNull
	Department department;

	@NotNull
	Set<RoleRequest> role;
}
