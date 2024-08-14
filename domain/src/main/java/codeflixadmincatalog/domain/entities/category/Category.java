package codeflixadmincatalog.domain.entities.category;

import codeflixadmincatalog.core.entities.Entity;

import java.time.LocalDateTime;

public final class Category extends Entity<CategoryID> {

    private String name;

    private String description;

    private boolean isActive;

    private Category(
            CategoryID categoryID,
            String name,
            String description,
            boolean isActive,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime deletedAt
    ) {
        super(categoryID);
        super.createdAt(createdAt);
        super.updatedAt(updatedAt);
        super.deletedAt(deletedAt);
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        new CategoryValidator(this).validate();
    }

    private Category(CategoryID categoryID, String name, String description, boolean isActive) {
        super(categoryID);
        this.name = name;
        this.description = description;
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

    public static Category create(
            CategoryID id,
            String name,
            String description,
            boolean isActive,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime deletedAt
    ) {
        return new Category(id, name, description, isActive, createdAt, updatedAt, deletedAt);
    }

    public static Category create(String name, String description, boolean isActive) {
        return new Category(name, description, isActive);
    }

    public String name() {
        return name;
    }

    public void name(String name) {
        this.name = name;
        this.updateCategory();
    }

    public String description() {
        return description;
    }

    public void description(String description) {
        this.description = description;
        this.updateCategory();
    }

    public boolean isActive() {
        return isActive;
    }

    public void deactivate() {
        this.isActive = false;
        this.updateCategory();
    }

    public void activate() {
        this.isActive = true;
        this.updateCategory();
    }

    private void updateCategory() {
        new CategoryValidator(this).validate();
        super.update();
    }
}
