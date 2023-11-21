package com.fredsonchaves.application.genre.retrieve.get;

import com.fredsonchaves.domain.category.CategoryID;
import com.fredsonchaves.domain.genre.Genre;
import com.fredsonchaves.domain.genre.GenreID;

import java.time.Instant;
import java.util.List;

public record GenreOutput(GenreID id, String name, List<CategoryID> categories, boolean isActive, Instant createdAt,
                          Instant updatedAt,
                          Instant deletedAt) {

    public static GenreOutput from(final Genre genre) {
        return new GenreOutput(genre.getId(), genre.getName(), genre.getCategories(), genre.isActive(), genre.getCreatedAt(), genre.getUpdatedAt(), genre.getDeletedAt());
    }
}
