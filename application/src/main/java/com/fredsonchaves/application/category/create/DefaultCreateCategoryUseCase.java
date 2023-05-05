package com.fredsonchaves.application.category.create;

import com.fredsonchaves.domain.category.Category;
import com.fredsonchaves.domain.category.CategoryGateway;
import com.fredsonchaves.domain.validation.handler.Notification;
import com.fredsonchaves.domain.validation.handler.ThrowsValidationHandler;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;

public class DefaultCreateCategoryUseCase implements CreateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<Notification, CreateCategoryOutput> execute(final CreateCategoryInput createCategoryInput) {
        final Notification notification = Notification.create();
        final Category category = Category.newCategory(
                createCategoryInput.name(), createCategoryInput.description(), createCategoryInput.isActive()
        );
        category.validate(notification);
        return notification.hasError() ? API.Left(notification) : create(category);
    }

    private Either<Notification, CreateCategoryOutput> create(final Category category) {
        return API.Try(() -> categoryGateway.create(category))
                .toEither()
                .bimap(Notification::create, CreateCategoryOutput::from);
    }
}
