package com.fredsonchaves.infraestructure.category.presenters;

import com.fredsonchaves.application.category.retrieve.get.CategoryOutput;
import com.fredsonchaves.infraestructure.category.CategoryApiOutput;

public interface CategoryApiPresenter {

    static CategoryApiOutput present(final CategoryOutput output) {
        return new CategoryApiOutput(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive()
        );
    }
}
