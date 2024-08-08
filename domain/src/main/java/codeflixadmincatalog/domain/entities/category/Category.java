package codeflixadmincatalog.domain.entities.category;

import codeflixadmincatalog.core.entities.Entity;

public final class Category extends Entity<CategoryID> {

    private String name;

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
        this.isActive = isActive;
        new CategoryValidator(this).validate();
    }

    public static Category create(String name, String description, boolean isActive) {
        return new Category(name, description, isActive);
    }

    public String name() {
        return name;
    }

    public void name(String name) {
        this.name = name;
        super.update();
    }

    public String description() {
        return description;
    }

    public void description(String description) {
        this.description = description;
        super.update();
    }

    public boolean isActive() {
        return isActive;
    }

    public void deactivate() {
        this.isActive = false;
        super.update();
    }

    public void activate() {
        this.isActive = true;
        super.update();
    }
}
