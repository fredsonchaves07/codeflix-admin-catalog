package com.fredsonchaves.infraestructure.configuration.usecases;

import com.fredsonchaves.application.category.create.CreateCategoryUseCase;
import com.fredsonchaves.application.category.create.DefaultCreateCategoryUseCase;
import com.fredsonchaves.application.category.delete.DefaultDeleteCategoryUseCase;
import com.fredsonchaves.application.category.delete.DeleteCategoryUseCase;
import com.fredsonchaves.application.category.retrieve.get.DefaultGetCategoryByIdUseCase;
import com.fredsonchaves.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.fredsonchaves.application.category.retrieve.list.DefaultListCategoriesUseCase;
import com.fredsonchaves.application.category.retrieve.list.ListCategoriesUseCase;
import com.fredsonchaves.application.category.update.DefaultUpdateCategoryUseCase;
import com.fredsonchaves.application.category.update.UpdateCategoryUseCase;
import com.fredsonchaves.domain.category.CategoryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryUseCaseConfig {

    private final CategoryGateway gateway;

    public CategoryUseCaseConfig(CategoryGateway gateway) {
        this.gateway = gateway;
    }

    @Bean
    public CreateCategoryUseCase createCategoryUseCase() {
        return new DefaultCreateCategoryUseCase(gateway);
    }

    @Bean
    public UpdateCategoryUseCase updateCategoryUseCase() {
        return new DefaultUpdateCategoryUseCase(gateway);
    }

    @Bean
    public GetCategoryByIdUseCase getCategoryByIdUseCase() {
        return new DefaultGetCategoryByIdUseCase(gateway);
    }

    @Bean
    public ListCategoriesUseCase listCategoriesUseCase() {
        return new DefaultListCategoriesUseCase(gateway);
    }

    @Bean
    public DeleteCategoryUseCase deleteCategoryUseCase() {
        return new DefaultDeleteCategoryUseCase(gateway);
    }
}
