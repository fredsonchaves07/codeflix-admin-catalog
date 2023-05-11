package com.fredsonchaves.application.category.retrieve.get;

import com.fredsonchaves.application.category.update.UpdateCategoryOutput;
import com.fredsonchaves.domain.category.Category;
import com.fredsonchaves.domain.category.CategoryGateway;
import com.fredsonchaves.domain.category.CategoryID;
import com.fredsonchaves.domain.exceptions.DomainException;
import com.fredsonchaves.domain.validation.Error;
import com.fredsonchaves.domain.validation.handler.Notification;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Optional;
import java.util.function.Supplier;

public class DefaultGetCategoryByIdUseCase implements GetCategoryByIdUseCase {

    public final CategoryGateway gateway;

    public DefaultGetCategoryByIdUseCase(final CategoryGateway gateway) {
        this.gateway = gateway;
    }


    @Override
    public CategoryOutput execute(CategoryID categoryID) {
        return gateway.findById(categoryID).map(CategoryOutput::from).orElseThrow(notFound(categoryID));
    }

    private Supplier<DomainException> notFound(final CategoryID categoryID) {
        return () -> DomainException.with(
                new Error("Category with id %s was not found".formatted(categoryID.getValue()))
        );
    }
}
