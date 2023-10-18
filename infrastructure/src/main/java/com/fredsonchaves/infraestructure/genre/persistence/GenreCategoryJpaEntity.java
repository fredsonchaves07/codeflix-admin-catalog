package com.fredsonchaves.infraestructure.genre.persistence;

import com.fredsonchaves.domain.category.CategoryID;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "genres_categories")
public class GenreCategoryJpaEntity {

    @EmbeddedId
    private GenreCategoryID id;

    @ManyToOne
    @MapsId("genreId")
    private GenreJpaEntity genre;

    @Deprecated
    public GenreCategoryJpaEntity() {

    }

    private GenreCategoryJpaEntity(final CategoryID categoryID, final GenreJpaEntity genre) {
        this.id = GenreCategoryID.from(genre.getId(), categoryID.getValue());
        this.genre = genre;
    }

    public static GenreCategoryJpaEntity from(final CategoryID categoryID, final GenreJpaEntity genre) {
        return new GenreCategoryJpaEntity(categoryID, genre);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreCategoryJpaEntity that = (GenreCategoryJpaEntity) o;
        return id.equals(that.id) && genre.equals(that.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, genre);
    }

    public GenreCategoryID getId() {
        return id;
    }

    public void setId(GenreCategoryID id) {
        this.id = id;
    }

    public GenreJpaEntity getGenre() {
        return genre;
    }

    public void setGenre(GenreJpaEntity genre) {
        this.genre = genre;
    }
}
