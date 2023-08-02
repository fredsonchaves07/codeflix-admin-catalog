package com.fredsonchaves.domain.genre;

import com.fredsonchaves.domain.AggregateRoot;
import com.fredsonchaves.domain.category.CategoryID;
import com.fredsonchaves.domain.exceptions.NotificationException;
import com.fredsonchaves.domain.validation.ValidationHandler;
import com.fredsonchaves.domain.validation.handler.Notification;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Genre extends AggregateRoot<GenreID> {

    private String name;

    private boolean isActive;

    private List<CategoryID> categories;

    private Instant createdAt;

    private Instant updatedAt;

    private Instant deletedAt;

    private Genre(final GenreID id, final String name, final boolean isActive, final List<CategoryID> categories,final Instant createdAt, final Instant updatedAt, final Instant deletedAt) {
        super(id);
        this.name = name;
        this.isActive = isActive;
        this.categories = categories;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        final Notification notification = Notification.create();
        validate(notification);
        if (notification.hasError()) {
            throw new NotificationException("", notification);
        }
    }

    public static Genre newGenre(final String name, final boolean isActive) {
        final GenreID id = GenreID.unique();
        final Instant now = Instant.now();
        final Instant deletedAt = isActive ? null : now;
        return new Genre(id, name, isActive, new ArrayList<>(), now, now, deletedAt);
    }

    public static Genre newGenre(final GenreID id, final String name, final boolean isActive, final List<CategoryID> categories,final Instant createdAt, final Instant updatedAt, final Instant deletedAt) {
        return new Genre(id, name, isActive, categories, createdAt, updatedAt, deletedAt);
    }

    public static Genre newGenre(final Genre genre) {
        return new Genre(genre.id, genre.getName(), genre.isActive(), genre.getCategories(), genre.getCreatedAt(), genre.getUpdatedAt(), genre.getDeletedAt());
    }

    @Override
    public void validate(ValidationHandler handler) {
        new GenreValidator(this, handler).validate();
    }

    public void activate() {
        isActive = true;
        deletedAt = null;
        updatedAt = Instant.now();
    }

    public void deactivate() {
        isActive = false;
        deletedAt = Instant.now();
        updatedAt = Instant.now();
    }

    public Genre update(String name, boolean isActive, List<CategoryID> categories) {
        if (isActive)
            activate();
        else
            deactivate();
        this.name = name;
        this.categories = categories;
        updatedAt = Instant.now();
        final Notification notification = Notification.create();
        validate(notification);
        if (notification.hasError()) {
            throw new NotificationException("", notification);
        }
        return this;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return isActive;
    }

    public List<CategoryID> getCategories() {
        return Collections.unmodifiableList(categories);
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
}
