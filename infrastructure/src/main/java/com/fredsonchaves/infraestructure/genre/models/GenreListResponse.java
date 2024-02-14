package com.fredsonchaves.infraestructure.genre.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fredsonchaves.application.genre.retrieve.list.GenreListOutput;

import java.util.List;

public record GenreListResponse(
        @JsonProperty("name") String name,
        @JsonProperty("is_active") boolean isActive,
        @JsonProperty("categories_id") List<String> categories
) {
    public static GenreListResponse from(GenreListOutput genreListOutput) {
        return new GenreListResponse(genreListOutput.name(), genreListOutput.isActive(), genreListOutput.categories());
    }
}
