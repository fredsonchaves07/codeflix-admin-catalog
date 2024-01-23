package com.fredsonchaves.infraestructure.genre.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fredsonchaves.application.genre.retrieve.get.GenreOutput;
import com.fredsonchaves.domain.category.CategoryID;

import java.time.Instant;
import java.util.List;

public record GenreResponse(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("categories_id") List<String> categories,
        @JsonProperty("is_active") boolean isActive,
        @JsonProperty("created_at") Instant createdAt,
        @JsonProperty("updated_at") Instant updatedAt,
        @JsonProperty("deleted_at") Instant deletedAt
) {

    public static GenreResponse from(GenreOutput output) {
        return new GenreResponse(
                output.id().getValue(),
                output.name(),
                toCategoriesListId(output.categories()),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }

    private static List<String> toCategoriesListId(List<CategoryID> categoryIDS) {
        return categoryIDS.stream().map(CategoryID::toString).toList();
    }
}
