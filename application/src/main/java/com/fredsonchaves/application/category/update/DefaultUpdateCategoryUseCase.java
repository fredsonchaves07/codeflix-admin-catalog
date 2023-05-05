package com.fredsonchaves.application.category.update;

import com.fredsonchaves.application.category.create.CreateCategoryOutput;
import com.fredsonchaves.domain.category.Category;
import com.fredsonchaves.domain.category.CategoryGateway;
import com.fredsonchaves.domain.category.CategoryID;
import com.fredsonchaves.domain.exceptions.DomainException;
import com.fredsonchaves.domain.validation.Error;
import com.fredsonchaves.domain.validation.handler.Notification;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;

public class DefaultUpdateCategoryUseCase implements UpdateCategoryUseCase {

    private final CategoryGateway gateway;

    public DefaultUpdateCategoryUseCase(final CategoryGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    @Override
    public Either<Notification, UpdateCategoryOutput> execute(UpdateCategoryInput input) {
        final Notification notification = Notification.create();
        Category category = gateway.findById(CategoryID.from(input.id())).orElseThrow(
                () -> DomainException.with(new Error("Category with id %s was not found".formatted(input.id())))
        );
        category.update(input.name(), input.description(), input.isActive()).validate(notification);
        return notification.hasError() ? API.Left(notification) : update(category);
    }

    private Either<Notification, UpdateCategoryOutput> update(final Category category) {
        return API.Try(() -> gateway.update(category))
                .toEither()
                .bimap(Notification::create, UpdateCategoryOutput::from);
    }
}
