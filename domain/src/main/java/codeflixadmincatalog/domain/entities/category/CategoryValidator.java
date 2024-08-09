package codeflixadmincatalog.domain.entities.category;

import codeflixadmincatalog.core.entities.Validator;
import codeflixadmincatalog.core.errors.DomainError;
import codeflixadmincatalog.domain.errors.category.CategoryError;

public class CategoryValidator implements Validator {

    private static final int MIN_NAME_LENGTH = 3;

    private static final int MAX_NAME_LENGTH = 50;

    private final Category category;

    public CategoryValidator(Category category) {
        this.category = category;
    }

    @Override
    public void validate() throws CategoryError {
        checkNameConstraints();
    }

    private void checkNameConstraints() {
        final String name = category.name();
        if (name == null)
            throw CategoryError.trows("Category name cannot be null");
        if (name.isBlank())
            throw CategoryError.trows("Category name cannot be empty");
        if (name.trim().length() > MAX_NAME_LENGTH || name.trim().length() < MIN_NAME_LENGTH)
            throw CategoryError.trows("Category name must be between 3 to 50 characters");
    }
}
