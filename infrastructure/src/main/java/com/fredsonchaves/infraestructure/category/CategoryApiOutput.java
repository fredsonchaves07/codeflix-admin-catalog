package com.fredsonchaves.infraestructure.category;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CategoryApiOutput(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("description")String description,
        @JsonProperty("is_active") boolean active
) {


}
