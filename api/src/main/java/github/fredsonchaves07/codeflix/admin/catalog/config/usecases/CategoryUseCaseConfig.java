package github.fredsonchaves07.codeflix.admin.catalog.config.usecases;

import codeflixadmincatalog.domain.repositories.category.CategoryRepository;
import codeflixadmincatalog.domain.usecases.category.CreateCategoryUseCase;
import io.quarkus.arc.DefaultBean;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;


@ApplicationScoped
public class CategoryUseCaseConfig {

    @Inject
    private CategoryRepository repository;

    @Produces
    @DefaultBean
    public CreateCategoryUseCase createCategoryUseCase() {
        return new CreateCategoryUseCase(repository);
    }
}
