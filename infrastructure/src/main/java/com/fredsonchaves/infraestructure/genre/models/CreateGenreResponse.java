package com.fredsonchaves.infraestructure.genre.models;

import java.time.Instant;
import java.util.List;

public record CreateGenreResponse(
        String id,
        String name,
        List<String> categories,
        boolean isActive,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {
}
