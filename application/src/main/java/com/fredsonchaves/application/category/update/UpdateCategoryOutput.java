package com.fredsonchaves.application.category.update;

import com.fredsonchaves.domain.category.Category;
import com.fredsonchaves.domain.category.CategoryID;

public record UpdateCategoryOutput(CategoryID id) {

    public static UpdateCategoryOutput from(final Category category) {
        return new UpdateCategoryOutput(category.getId());
    }
}
