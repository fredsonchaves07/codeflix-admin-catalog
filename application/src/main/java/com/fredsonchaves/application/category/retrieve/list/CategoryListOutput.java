package com.fredsonchaves.application.category.retrieve.list;

import com.fredsonchaves.domain.category.Category;
import com.fredsonchaves.domain.category.CategoryID;

import java.time.Instant;

public record CategoryListOutput(
        CategoryID categoryID,
        String name,
        String description,
        boolean isActive,
        Instant createdAt,
        Instant deletedAt
) {

    public static CategoryListOutput from(final Category category) {
        return new CategoryListOutput(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.isActive(),
                category.getCreatedAt(),
                category.getDeletedAt()
        );
    }
}
