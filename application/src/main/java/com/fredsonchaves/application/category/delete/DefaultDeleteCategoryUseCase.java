package com.fredsonchaves.application.category.delete;

import com.fredsonchaves.domain.category.CategoryGateway;
import com.fredsonchaves.domain.category.CategoryID;

import java.util.Objects;

public class DefaultDeleteCategoryUseCase implements DeleteCategoryUseCase {

    private final CategoryGateway gateway;

    public DefaultDeleteCategoryUseCase(final CategoryGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    @Override
    public void execute(final CategoryID id) {
        gateway.deleteById(id);
    }
}
