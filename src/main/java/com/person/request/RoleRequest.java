package com.person.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRequest {

    @Nullable
    Integer id;

    @NotNull
    String name;
}
