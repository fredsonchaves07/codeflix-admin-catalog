package com.fredsonchaves.application.genre.retrieve.get;

import com.fredsonchaves.application.UseCase;
import com.fredsonchaves.domain.exceptions.NotFoundException;
import com.fredsonchaves.domain.genre.Genre;
import com.fredsonchaves.domain.genre.GenreGateway;
import com.fredsonchaves.domain.genre.GenreID;

import java.util.function.Supplier;

public class GetGenreByIdUseCase implements UseCase<GenreID, GenreOutput> {

    public final GenreGateway gateway;

    public GetGenreByIdUseCase(final GenreGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public GenreOutput execute(GenreID genreID) {
        return gateway.findById(genreID).map(GenreOutput::from).orElseThrow(notFound(genreID));
    }

    private Supplier<NotFoundException> notFound(final GenreID genreID) {
        return () -> NotFoundException.with(Genre.class, genreID);
    }
}
