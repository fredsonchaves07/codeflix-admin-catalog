package com.fredsonchaves.infraestructure.genre.api.controllers;

import com.fredsonchaves.application.genre.create.CreateGenreCommand;
import com.fredsonchaves.application.genre.create.CreateGenreOutput;
import com.fredsonchaves.application.genre.create.CreateGenreUseCase;
import com.fredsonchaves.application.genre.retrieve.get.GenreOutput;
import com.fredsonchaves.application.genre.retrieve.get.GetGenreByIdUseCase;
import com.fredsonchaves.domain.genre.GenreID;
import com.fredsonchaves.domain.pagination.Pagination;
import com.fredsonchaves.infraestructure.genre.api.GenreAPI;
import com.fredsonchaves.infraestructure.genre.models.CreateGenreResponse;
import com.fredsonchaves.infraestructure.genre.models.GenreListResponse;
import com.fredsonchaves.infraestructure.genre.models.GenreResponse;
import com.fredsonchaves.infraestructure.genre.models.UpdateGenreResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class GenreController implements GenreAPI {

    private final CreateGenreUseCase createGenreUseCase;

    private final GetGenreByIdUseCase getGenreByIdUseCase;

//    private final UpdateGenreUseCase updateGenreUseCase;
//
//    private final DeleteGenreUseCase deleteGenreUseCase;
//
//    private final GenreListUseCase genreListUseCase;

    public GenreController(
            final CreateGenreUseCase createGenreUseCase,
            final GetGenreByIdUseCase getGenreByIdUseCase
//            final UpdateGenreUseCase updateGenreUseCase,
//            final DeleteGenreUseCase deleteGenreUseCase,
//            final GenreListUseCase genreListUseCase
    ) {
        this.createGenreUseCase = createGenreUseCase;
        this.getGenreByIdUseCase = getGenreByIdUseCase;
//        this.updateGenreUseCase = updateGenreUseCase;
//        this.deleteGenreUseCase = deleteGenreUseCase;
//        this.genreListUseCase = genreListUseCase;
    }

    @Override
    public ResponseEntity<?> createGenre(CreateGenreResponse input) {
        final CreateGenreCommand genreInput = CreateGenreCommand.with(
                input.name(),
                input.isActive(),
                input.categories()
        );
        CreateGenreOutput createGenreOutput = createGenreUseCase.execute(genreInput);
        return ResponseEntity.created(URI.create("/genres/" + createGenreOutput.id())).body(createGenreOutput);
    }

    @Override
    public Pagination<GenreListResponse> listGenre(String search, int page, int perPage, String sort, String direction) {
        return null;
    }

    @Override
    public GenreResponse getById(String id) {
        GenreOutput genreOutput = getGenreByIdUseCase.execute(GenreID.from(id));
        return GenreResponse.from(genreOutput);
    }

    @Override
    public ResponseEntity<?> updateById(String id, UpdateGenreResponse updateGenreResponse) {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
