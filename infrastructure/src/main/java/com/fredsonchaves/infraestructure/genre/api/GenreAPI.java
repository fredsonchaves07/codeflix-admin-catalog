package com.fredsonchaves.infraestructure.genre.api;

import com.fredsonchaves.domain.pagination.Pagination;
import com.fredsonchaves.infraestructure.genre.models.CreateGenreResponse;
import com.fredsonchaves.infraestructure.genre.models.GenreResponse;
import com.fredsonchaves.infraestructure.genre.models.UpdateGenreResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "genres")
@Tag(name = "Genre")
public interface GenreAPI {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new genre")
    ResponseEntity<?> createGenre(
            @RequestBody CreateGenreResponse createGenreResponse
    );

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List all genres paginated")
    Pagination<?> listGenre(
            @RequestParam(name = "search", required = false, defaultValue = "") final String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") final int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "name") final String sort,
            @RequestParam(name = "dir", required = false, defaultValue = "asc") final String direction
    );

    @GetMapping("/{id}")
    @Operation(summary = "Get genre by id")
    GenreResponse getById(@PathVariable(name = "id") String id);

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Updated a genre")
    ResponseEntity<?> updateById(@PathVariable(name = "id") String id, @RequestBody UpdateGenreResponse updateGenreResponse);

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a genre")
    void delete(@PathVariable(name = "id") String id);
}
