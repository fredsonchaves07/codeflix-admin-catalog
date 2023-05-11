package com.fredsonchaves.application.category.retrieve.get;

import com.fredsonchaves.application.UseCase;
import com.fredsonchaves.domain.category.CategoryID;

public interface GetCategoryByIdUseCase extends UseCase<CategoryID, CategoryOutput> {
}
