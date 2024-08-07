package codeflixadmincatalog.core.entities;

import java.time.LocalDateTime;

public abstract class Entity<ID extends Identifier> {

    private final ID id;

    private final LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    protected Entity(ID id) {
        this.id = id;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public ID id() {
        return id;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public LocalDateTime updatedAt() {
        return updatedAt;
    }

    public LocalDateTime deletedAt() {
        return deletedAt;
    }

    public void update() {
        this.updatedAt = LocalDateTime.now();
    }
}
