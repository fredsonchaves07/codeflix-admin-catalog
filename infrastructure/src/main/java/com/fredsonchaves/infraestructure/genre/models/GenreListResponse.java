package com.fredsonchaves.infraestructure.genre.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record GenreListResponse(
        @JsonProperty("name") String name,
        @JsonProperty("is_active") boolean isActive,
        @JsonProperty("categories_id") List<String> categories
) {
}
