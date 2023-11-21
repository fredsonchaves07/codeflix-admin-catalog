package com.fredsonchaves.infraestructure.genre.api.controllers;

import com.fredsonchaves.domain.pagination.Pagination;
import com.fredsonchaves.infraestructure.genre.api.GenreAPI;
import com.fredsonchaves.infraestructure.genre.models.CreateGenreResponse;
import com.fredsonchaves.infraestructure.genre.models.GenreListResponse;
import com.fredsonchaves.infraestructure.genre.models.GenreResponse;
import com.fredsonchaves.infraestructure.genre.models.UpdateGenreResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenreController implements GenreAPI {

    @Override
    public ResponseEntity<?> createGenre(CreateGenreResponse createGenreResponse) {
        return null;
    }

    @Override
    public Pagination<GenreListResponse> listGenre(String search, int page, int perPage, String sort, String direction) {
        return null;
    }

    @Override
    public GenreResponse getById(String id) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateById(String id, UpdateGenreResponse updateGenreResponse) {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
