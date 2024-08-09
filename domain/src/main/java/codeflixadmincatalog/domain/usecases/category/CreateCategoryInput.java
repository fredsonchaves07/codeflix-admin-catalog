package codeflixadmincatalog.domain.usecases.category;

import codeflixadmincatalog.core.valueobject.ValueObject;

public record CreateCategoryInput(
        String name,
        String description,
        boolean isActive
) implements ValueObject {

    public static CreateCategoryInput with(String name, String description, boolean isActive) {
        return new CreateCategoryInput(name, description, isActive);
    }
}
