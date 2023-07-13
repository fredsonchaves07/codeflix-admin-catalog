package com.fredsonchaves.infraestructure.category.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CategoryListResponse (
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("description")String description,
        @JsonProperty("is_active") boolean active
) {}
