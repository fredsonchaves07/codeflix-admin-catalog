package com.fredsonchaves.application.genre.delete;

import com.fredsonchaves.application.UnitUseCase;
import com.fredsonchaves.domain.genre.GenreGateway;
import com.fredsonchaves.domain.genre.GenreID;

import java.util.Objects;

public class DeleteGenreUseCase implements UnitUseCase<GenreID> {

    private final GenreGateway gateway;

    public DeleteGenreUseCase(final GenreGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }


    @Override
    public void execute(GenreID genreID) {
        gateway.deleteById(genreID);
    }
}
