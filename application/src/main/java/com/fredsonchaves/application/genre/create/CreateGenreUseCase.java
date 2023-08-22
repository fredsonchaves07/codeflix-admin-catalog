package com.fredsonchaves.application.genre.create;

import com.fredsonchaves.application.UseCase;
import com.fredsonchaves.domain.category.CategoryGateway;
import com.fredsonchaves.domain.category.CategoryID;
import com.fredsonchaves.domain.exceptions.NotificationException;
import com.fredsonchaves.domain.genre.Genre;
import com.fredsonchaves.domain.genre.GenreGateway;
import com.fredsonchaves.domain.validation.Error;
import com.fredsonchaves.domain.validation.ValidationHandler;
import com.fredsonchaves.domain.validation.handler.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CreateGenreUseCase implements UseCase<CreateGenreCommand, CreateGenreOutput> {

    private final GenreGateway genreGateway;

    private final CategoryGateway categoryGateway;

    public CreateGenreUseCase(GenreGateway genreGateway, CategoryGateway categoryGateway) {
        this.genreGateway = Objects.requireNonNull(genreGateway);
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public CreateGenreOutput execute(CreateGenreCommand command) {
        final String name = command.name();
        final boolean isActive = command.isActive();
        final List<CategoryID> categoryIDS = toCategoryId(command.categoriesId());
        final Notification notification = Notification.create();
        notification.append(validateCategories(categoryIDS));
        Genre genre = notification.validate(() -> Genre.newGenre(name, isActive));
        if (notification.hasError())
            throw new NotificationException("Could not create a genre", notification);
        return CreateGenreOutput.from(genreGateway.create(genre));
    }

    private ValidationHandler validateCategories(List<CategoryID> categoryIDS) {
        final Notification notification = Notification.create();
        if (categoryIDS == null || categoryIDS.isEmpty()) {
            return notification;
        }
        final List<CategoryID> retrieveIds = categoryGateway.existsByIds(categoryIDS);
        if (categoryIDS.size() != retrieveIds.size()) {
            final var commandsIds = new ArrayList<>(categoryIDS);
            commandsIds.removeAll(retrieveIds);
            final String missingIds = commandsIds.stream().map(CategoryID::getValue).collect(Collectors.joining(", "));
            notification.append(new Error("Some categories could not be found: %s".formatted(missingIds)));
        }
        return notification;
    }

    private List<CategoryID> toCategoryId(final List<String> categoriesId) {
        return categoriesId.stream().map(CategoryID::from).toList();
    }
}
