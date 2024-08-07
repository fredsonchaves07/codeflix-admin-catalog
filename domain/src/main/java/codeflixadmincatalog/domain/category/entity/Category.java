package codeflixadmincatalog.domain.category.entity;

import codeflixadmincatalog.core.entities.Entity;

public final class Category extends Entity<CategoryID> {

    private final String name;

    private String description;

    private boolean isActive;

    private Category(CategoryID categoryID, String name, String description, boolean isActive) {
        super(categoryID);
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        new CategoryValidator(this).validate();
    }

    private Category(CategoryID categoryID, String name, boolean isActive) {
        super(categoryID);
        this.name = name;
        this.isActive = isActive;
        new CategoryValidator(this).validate();
    }

    private Category(String name, String description, boolean isActive) {
        super(CategoryID.newId());
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        new CategoryValidator(this).validate();
    }

    private Category(String name, boolean isActive) {
        super(CategoryID.newId());
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        new CategoryValidator(this).validate();
    }

    public static Category create(String name, String description, boolean isActive) {
        return new Category(name, description, isActive);
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public boolean isActive() {
        return isActive;
    }
}
