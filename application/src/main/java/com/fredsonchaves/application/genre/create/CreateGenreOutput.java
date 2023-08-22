package com.fredsonchaves.application.genre.create;

import com.fredsonchaves.domain.genre.Genre;

public record CreateGenreOutput(String id) {

    public static CreateGenreOutput from(final Genre genre) {
        return new CreateGenreOutput(genre.getId().getValue());
    }

    public static CreateGenreOutput from(final String anId) {
        return new CreateGenreOutput(anId);
    }

    @Override
    public String toString() {
        return id;
    }
}
