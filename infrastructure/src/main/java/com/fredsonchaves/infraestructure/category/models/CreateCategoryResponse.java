package com.fredsonchaves.infraestructure.category.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateCategoryResponse(
        @JsonProperty("name") String name,
        @JsonProperty("description")String description,
        @JsonProperty("is_active") boolean active
) {
}
