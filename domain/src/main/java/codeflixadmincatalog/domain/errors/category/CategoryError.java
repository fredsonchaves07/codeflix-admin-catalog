package codeflixadmincatalog.domain.errors.category;

import codeflixadmincatalog.core.errors.DomainError;

public class CategoryError extends DomainError {

    private CategoryError(String message) {
        super(message);
    }

    private CategoryError(Exception exception) {
        super(exception);
    }

    public static CategoryError trows(String message) {
        return new CategoryError(message);
    }

    public static CategoryError trows(Exception exception) {
        return new CategoryError(exception);
    }
}
