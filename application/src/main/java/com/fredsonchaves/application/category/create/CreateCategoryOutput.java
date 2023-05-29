package com.fredsonchaves.application.category.create;

import com.fredsonchaves.domain.category.Category;
import com.fredsonchaves.domain.category.CategoryID;

public record CreateCategoryOutput(CategoryID id) {

    public static CreateCategoryOutput from(final Category category) {
        return new CreateCategoryOutput(category.getId());
    }

    public static CreateCategoryOutput from(final CategoryID id) {
        return new CreateCategoryOutput(id);
    }

    public static CreateCategoryOutput from(final String id) {
        return new CreateCategoryOutput(CategoryID.from(id));
    }
}
