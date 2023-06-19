package com.fredsonchaves.infraestructure.api.controllers;

import com.fredsonchaves.application.category.create.CreateCategoryInput;
import com.fredsonchaves.application.category.create.CreateCategoryOutput;
import com.fredsonchaves.application.category.create.CreateCategoryUseCase;
import com.fredsonchaves.application.category.delete.DeleteCategoryUseCase;
import com.fredsonchaves.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.fredsonchaves.application.category.retrieve.list.ListCategoriesUseCase;
import com.fredsonchaves.application.category.update.UpdateCategoryInput;
import com.fredsonchaves.application.category.update.UpdateCategoryOutput;
import com.fredsonchaves.application.category.update.UpdateCategoryUseCase;
import com.fredsonchaves.domain.category.CategoryID;
import com.fredsonchaves.domain.category.CategorySearchQuery;
import com.fredsonchaves.domain.pagination.Pagination;
import com.fredsonchaves.domain.validation.handler.Notification;
import com.fredsonchaves.infraestructure.api.CategoryAPI;
import com.fredsonchaves.infraestructure.category.CategoryApiOutput;
import com.fredsonchaves.infraestructure.category.models.CreateCategoryApiInput;
import com.fredsonchaves.infraestructure.category.models.UpdateCategoryApiInput;
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

    private final UpdateCategoryUseCase updateCategoryUseCase;

    private final DeleteCategoryUseCase deleteCategoryUseCase;

    private final ListCategoriesUseCase listCategoriesUseCase;

    public CategoryController(
            final CreateCategoryUseCase createCategoryUseCase,
            final GetCategoryByIdUseCase getCategoryByIdUseCase,
            final UpdateCategoryUseCase updateCategoryUseCase,
            final DeleteCategoryUseCase deleteCategoryUseCase,
            final ListCategoriesUseCase listCategoriesUseCase
    ) {
        this.createCategoryUseCase = Objects.requireNonNull(createCategoryUseCase);
        this.getCategoryByIdUseCase = Objects.requireNonNull(getCategoryByIdUseCase);
        this.updateCategoryUseCase = Objects.requireNonNull(updateCategoryUseCase);
        this.deleteCategoryUseCase = Objects.requireNonNull(deleteCategoryUseCase);
        this.listCategoriesUseCase = Objects.requireNonNull(listCategoriesUseCase);
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
        return listCategoriesUseCase.execute(new CategorySearchQuery(page, perPage, search, sort, direction));
    }

    @Override
    public CategoryApiOutput getById(String id) {
        return CategoryApiPresenter.present(getCategoryByIdUseCase.execute(CategoryID.from(id)));
    }

    @Override
    public ResponseEntity<?> updateById(final String id, final UpdateCategoryApiInput input) {
        final UpdateCategoryInput categoryInput = UpdateCategoryInput.with(
                id,
                input.name(),
                input.description(),
                input.active()
        );
        final Function<Notification, ResponseEntity<?>> onError = ResponseEntity.unprocessableEntity()::body;
        final Function<UpdateCategoryOutput, ResponseEntity<?>> onSuccess = ResponseEntity::ok;
        return updateCategoryUseCase.execute(categoryInput).fold(onError, onSuccess);
    }

    @Override
    public void deleteById(String id) {
        deleteCategoryUseCase.execute(CategoryID.from(id));
    }
}
