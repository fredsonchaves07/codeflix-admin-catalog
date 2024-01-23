package com.fredsonchaves.infraestructure.configuration.usecases;

import com.fredsonchaves.application.genre.create.CreateGenreUseCase;
import com.fredsonchaves.application.genre.retrieve.get.GetGenreByIdUseCase;
import com.fredsonchaves.domain.category.CategoryGateway;
import com.fredsonchaves.domain.genre.GenreGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GenreUseCaseConfig {

    private final GenreGateway genreGateway;

    private final CategoryGateway categoryGateway;

    public GenreUseCaseConfig(GenreGateway genreGateway, CategoryGateway categoryGateway) {
        this.genreGateway = genreGateway;
        this.categoryGateway = categoryGateway;
    }

    @Bean
    public CreateGenreUseCase createGenreUseCase() {
        return new CreateGenreUseCase(genreGateway, categoryGateway);
    }

    //    @Bean
//    public UpdateGenreUseCase updateGenreUseCase() {
//        return new UpdateGenreUseCase(categoryGateway, genreGateway);
//    }
//
    @Bean
    public GetGenreByIdUseCase getGenreByIdUseCase() {
        return new GetGenreByIdUseCase(genreGateway);
    }
//
//    @Bean
//    public GenreListUseCase genreListUseCase() {
//        return new GenreListUseCase(genreGateway);
//    }
//
//    @Bean
//    public DeleteGenreUseCase deleteGenreUseCase() {
//        return new DeleteGenreUseCase(genreGateway);
//    }
}
