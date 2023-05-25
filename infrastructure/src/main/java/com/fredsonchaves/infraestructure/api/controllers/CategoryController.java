package com.fredsonchaves.infraestructure.api.controllers;

import com.fredsonchaves.application.category.create.CreateCategoryUseCase;
import com.fredsonchaves.domain.pagination.Pagination;
import com.fredsonchaves.infraestructure.api.CategoryAPI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class CategoryController implements CategoryAPI {

    private final CreateCategoryUseCase useCase;

    public CategoryController(final CreateCategoryUseCase useCase) {
        this.useCase = Objects.requireNonNull(useCase);
    }

    @Override
    public ResponseEntity<?> createCategory() {
        return null;
    }

    @Override
    public Pagination<?> listCategories(String search, int page, int perPage, String sort, String direction) {
        return null;
    }
}
