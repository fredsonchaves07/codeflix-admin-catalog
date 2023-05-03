package com.fredsonchaves.domain.category;

import com.fredsonchaves.domain.validation.Error;
import com.fredsonchaves.domain.validation.ValidationHandler;
import com.fredsonchaves.domain.validation.Validator;

public class CategoryValidator extends Validator {

    private Category category;

    public CategoryValidator(final Category category, final ValidationHandler handler) {
        super(handler);
        this.category = category;
    }

    @Override
    public void validate() {
        if (category.getName() == null)
            validationHandler().append(new Error("'name' should not be null"));
    }
}
