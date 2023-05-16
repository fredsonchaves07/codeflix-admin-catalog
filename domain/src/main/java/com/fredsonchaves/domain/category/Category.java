package com.fredsonchaves.domain.category;

import com.fredsonchaves.domain.AggregateRoot;
import com.fredsonchaves.domain.validation.ValidationHandler;

import java.time.Instant;

public class Category extends AggregateRoot<CategoryID>  implements Cloneable{

    private String name;

    private String description;

    private boolean active;

    private Instant createdAt;

    private Instant updatedAt;

    private Instant deletedAt;

    private Category(
            final CategoryID id,
            final String name,
            final String description,
            final boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        super(id);
        this.name = name;
        this.description = description;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Category newCategory(final String name, final String description, final boolean isActive) {
        CategoryID id = CategoryID.unique();
        Instant createdAt = Instant.now();
        Instant updatedAt = Instant.now();
        Instant deletedAt = isActive ? null : Instant.now();
        return new Category(id, name, description, isActive, createdAt, updatedAt, deletedAt);
    }

    public static Category newCategory(
            final CategoryID id,
            final String name,
            final String description,
            final boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        return new Category(id, name, description, active, createdAt, updatedAt, deletedAt);
    }

    public static Category newCategory(final Category category) {
        return newCategory(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.isActive(),
                category.getCreatedAt(),
                category.getUpdatedAt(),
                category.getDeletedAt()
        );
    }

    public CategoryID getId() {
        return id;
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new CategoryValidator(this, handler).validate();
    }

    public Category deactivate() {
        if (getDeletedAt() == null)
            deletedAt = Instant.now();
        active = false;
        updatedAt = Instant.now();
        return this;
    }

    public Category activate() {
        deletedAt = null;
        active = true;
        updatedAt = Instant.now();
        return this;
    }

    public Category update(final String name, final String description, final boolean isActive) {
        if (isActive)
            activate();
        else
            deactivate();
        this.name = name;
        this.description = description;
        updatedAt = Instant.now();
        return this;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    @Override
    public Category clone() {
        try {
           return (Category) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
