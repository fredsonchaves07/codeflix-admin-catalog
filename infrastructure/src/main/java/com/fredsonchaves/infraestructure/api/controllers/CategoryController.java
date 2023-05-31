package com.fredsonchaves.infraestructure.api.controllers;

import com.fredsonchaves.application.category.create.CreateCategoryInput;
import com.fredsonchaves.application.category.create.CreateCategoryOutput;
import com.fredsonchaves.application.category.create.CreateCategoryUseCase;
import com.fredsonchaves.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.fredsonchaves.domain.category.CategoryID;
import com.fredsonchaves.domain.pagination.Pagination;
import com.fredsonchaves.domain.validation.handler.Notification;
import com.fredsonchaves.infraestructure.api.CategoryAPI;
import com.fredsonchaves.infraestructure.category.CategoryApiOutput;
import com.fredsonchaves.infraestructure.category.models.CreateCategoryApiInput;
import com.fredsonchaves.infraestructure.category.presenters.CategoryApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public class CategoryController implements CategoryAPI {

    private final CreateCategoryUseCase createCategoryUseCase;

    private final GetCategoryByIdUseCase getCategoryByIdUseCase;

    public CategoryController(final CreateCategoryUseCase createCategoryUseCase, final GetCategoryByIdUseCase getCategoryByIdUseCase) {
        this.createCategoryUseCase = Objects.requireNonNull(createCategoryUseCase);
        this.getCategoryByIdUseCase = Objects.requireNonNull(getCategoryByIdUseCase);
    }

    @Override
    public ResponseEntity<?> createCategory(CreateCategoryApiInput input) {
        final CreateCategoryInput categoryInput = CreateCategoryInput.with(
                input.name(),
                input.description(),
                input.active()
        );
        final Function<Notification, ResponseEntity<?>> onError = ResponseEntity.unprocessableEntity()::body;
        final Function<CreateCategoryOutput, ResponseEntity<?>> onSuccess = output ->
                ResponseEntity.created(URI.create("/categories/" + output.id().getValue())).body(output);
        return createCategoryUseCase.execute(categoryInput)
                .fold(onError, onSuccess);
    }

    @Override
    public Pagination<?> listCategories(String search, int page, int perPage, String sort, String direction) {
        return null;
    }

    @Override
    public CategoryApiOutput getById(String id) {
        return CategoryApiPresenter.present(getCategoryByIdUseCase.execute(CategoryID.from(id)));
    }
}
