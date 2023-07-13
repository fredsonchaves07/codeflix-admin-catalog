package com.fredsonchaves.infraestructure.category.presenters;

import com.fredsonchaves.application.category.retrieve.get.CategoryOutput;
import com.fredsonchaves.application.category.retrieve.list.CategoryListOutput;
import com.fredsonchaves.infraestructure.category.CategoryApiOutput;
import com.fredsonchaves.infraestructure.category.models.CategoryListResponse;

public interface CategoryApiPresenter {

    static CategoryApiOutput present(final CategoryOutput output) {
        return new CategoryApiOutput(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive()
        );
    }

    static CategoryListResponse present(final CategoryListOutput output) {
        return new CategoryListResponse(
                output.categoryID().getValue(),
                output.name(),
                output.description(),
                output.isActive()
        );
    }
}
