package com.fredsonchaves.application.genre.retrieve.list;

import com.fredsonchaves.domain.category.CategoryID;
import com.fredsonchaves.domain.genre.Genre;

import java.time.Instant;
import java.util.List;

public record GenreListOutput(
        String name,
        boolean isActive,
        List<String> categories,
        Instant createdAt
) {

    public static GenreListOutput from(final Genre genre) {
        return new GenreListOutput(
                genre.getName(), genre.isActive(), genre.getCategories().stream().map(CategoryID::getValue).toList(),
                genre.getCreatedAt()
        );
    }
}
