package com.fredsonchaves.infraestructure.genre.presenters;

import com.fredsonchaves.application.genre.retrieve.get.GenreOutput;
import com.fredsonchaves.application.genre.retrieve.list.GenreListOutput;
import com.fredsonchaves.domain.category.CategoryID;
import com.fredsonchaves.infraestructure.genre.models.GenreListResponse;
import com.fredsonchaves.infraestructure.genre.models.GenreResponse;

public interface GenreApiPresenter {

    static GenreResponse present(final GenreOutput output) {
        return new GenreResponse(
                output.id().getValue(),
                output.name(),
                output.categories().stream().map(CategoryID::getValue).toList(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }

    static GenreListResponse present(final GenreListOutput output) {
        return new GenreListResponse(
                output.name(),
                output.isActive(),
                output.categories()
        );
    }
}
