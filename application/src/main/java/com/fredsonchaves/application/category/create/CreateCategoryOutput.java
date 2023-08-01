package com.fredsonchaves.application.category.create;

import com.fredsonchaves.domain.category.Category;
import com.fredsonchaves.domain.category.CategoryID;

public record CreateCategoryOutput(String id) {

    public static CreateCategoryOutput from(final Category category) {
        return new CreateCategoryOutput(category.getId().getValue());
    }

    public static CreateCategoryOutput from(final String anId) {
        return new CreateCategoryOutput(anId);
    }

    @Override
    public String toString() {
        return id;
    }
}
