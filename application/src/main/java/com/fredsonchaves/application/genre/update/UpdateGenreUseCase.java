package com.fredsonchaves.application.genre.update;

import com.fredsonchaves.application.UseCase;
import com.fredsonchaves.domain.category.Category;
import com.fredsonchaves.domain.category.CategoryGateway;
import com.fredsonchaves.domain.category.CategoryID;
import com.fredsonchaves.domain.exceptions.DomainException;
import com.fredsonchaves.domain.exceptions.NotificationException;
import com.fredsonchaves.domain.genre.Genre;
import com.fredsonchaves.domain.genre.GenreGateway;
import com.fredsonchaves.domain.genre.GenreID;
import com.fredsonchaves.domain.validation.Error;
import com.fredsonchaves.domain.validation.ValidationHandler;
import com.fredsonchaves.domain.validation.handler.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UpdateGenreUseCase implements UseCase<UpdateGenreCommand, UpdateGenreOutput> {

    private final CategoryGateway categoryGateway;

    private final GenreGateway genreGateway;

    public UpdateGenreUseCase(final CategoryGateway categoryGateway, final GenreGateway genreGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }

    @Override
    public UpdateGenreOutput execute(final UpdateGenreCommand updateGenreCommand) {
        GenreID id = GenreID.from(updateGenreCommand.id());
        String name = updateGenreCommand.name();
        boolean active = updateGenreCommand.isActive();
        List<CategoryID> categories = toCategoryId(updateGenreCommand.categories());
        Genre genre = genreGateway.findById(id).orElseThrow(() -> DomainException.with(new Error("Genre with id %s was not found".formatted(id))));
        Notification notification = Notification.create();
        notification.append(validateCategories(categories));
        notification.validate(() -> genre.update(name, active, categories));
        if (notification.hasError())
            throw new NotificationException("Could not update a genre", notification);
        return UpdateGenreOutput.from(genreGateway.update(genre));
    }

    private ValidationHandler validateCategories(List<CategoryID> categoryID) {
        final Notification notification = Notification.create();
        if (categoryID == null || categoryID.isEmpty()) {
            return notification;
        }
        final List<CategoryID> retrieveIds = categoryGateway.existsByIds(categoryID);
        if (categoryID.size() != retrieveIds.size()) {
            final var commandsIds = new ArrayList<>(categoryID);
            commandsIds.removeAll(retrieveIds);
            final String missingIds = commandsIds.stream().map(CategoryID::getValue).collect(Collectors.joining(", "));
            notification.append(new Error("Some categories could not be found: %s".formatted(missingIds)));
        }
        return notification;
    }

    private List<CategoryID> toCategoryId(List<String> categories) {
        return categories.stream().map(CategoryID::from).toList();
    }
}
