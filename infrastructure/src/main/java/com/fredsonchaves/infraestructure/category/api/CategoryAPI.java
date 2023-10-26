package com.fredsonchaves.infraestructure.category.api;

import com.fredsonchaves.domain.pagination.Pagination;
import com.fredsonchaves.infraestructure.category.CategoryApiOutput;
import com.fredsonchaves.infraestructure.category.models.CreateCategoryResponse;
import com.fredsonchaves.infraestructure.category.models.UpdateCategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(value = "/categories")
@Tag(name = "Categories")
public interface CategoryAPI {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "422", description = "Unprocessable error"),
            @ApiResponse(responseCode = "500", description = "An internal server error"),
    })
    ResponseEntity<?> createCategory(@RequestBody @Valid CreateCategoryResponse input);

    @GetMapping
    @Operation(summary = "List all categories paginated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "422", description = "Unprocessable error"),
            @ApiResponse(responseCode = "500", description = "An internal server error"),
    })
    Pagination<?> listCategories(
            @RequestParam(name = "search", required = false, defaultValue = "") final String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") final int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "name") final String sort,
            @RequestParam(name = "dir", required = false, defaultValue = "asc") final String direction
    );

    @GetMapping("/{id}")
    @Operation(summary = "Get a category by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NotFound"),
            @ApiResponse(responseCode = "500", description = "An internal server error"),
    })
    CategoryApiOutput getById(@PathVariable(value = "id") String id);

    @PutMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Update category by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated succesfully"),
            @ApiResponse(responseCode = "404", description = "NotFound"),
            @ApiResponse(responseCode = "500", description = "An internal server error"),
    })
    ResponseEntity<?> updateById(@PathVariable(value = "id") String id, @RequestBody UpdateCategoryResponse input);

    @DeleteMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Delete category by it's identifier")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Delete succesfuly"),
            @ApiResponse(responseCode = "404", description = "NotFound"),
            @ApiResponse(responseCode = "500", description = "An internal server error"),
    })
    void deleteById(@PathVariable(value = "id") String id);
}
