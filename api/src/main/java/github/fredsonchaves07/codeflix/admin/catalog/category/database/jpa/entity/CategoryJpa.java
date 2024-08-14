package github.fredsonchaves07.codeflix.admin.catalog.category.database.jpa.entity;

import codeflixadmincatalog.domain.entities.category.Category;
import codeflixadmincatalog.domain.entities.category.CategoryID;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "category")
public class CategoryJpa extends PanacheEntityBase {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(length = 4000)
    private String description;

    @Column(nullable = false)
    private boolean isActive;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    private CategoryJpa() {

    }

    private CategoryJpa(Category category) {
        this.id = category.id().toString();
        this.name = category.name();
        this.description = category.description();
        this.isActive = category.isActive();
        this.createdAt = category.createdAt();
        this.updatedAt = category.updatedAt();
        this.deletedAt = category.deletedAt();
    }

    public static CategoryJpa from(Category category) {
        return new CategoryJpa(category);
    }

    public Category toAggregate() {
        return Category.create(CategoryID.newId(id), name, description, isActive, createdAt, updatedAt, deletedAt);
    }

    public CategoryJpa updateFrom(Category category) {
        this.name = category.name();
        this.description = category.description();
        this.updatedAt = category.updatedAt();
        this.deletedAt = category.deletedAt();
        return this;
    }
}