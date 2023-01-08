package com.gsv.codeflix.admin.catalog.infrastructure.configuration;

import com.gsv.codeflix.admin.catalog.application.category.create.CreateCategoryUseCase;
import com.gsv.codeflix.admin.catalog.application.category.create.DefaultCreateCategoryUseCase;
import com.gsv.codeflix.admin.catalog.application.category.delete.DefaultDeleteCategoryUseCase;
import com.gsv.codeflix.admin.catalog.application.category.delete.DeleteCategoryUseCase;
import com.gsv.codeflix.admin.catalog.application.category.retrieve.get.DefaultGetCategoryByIdUseCase;
import com.gsv.codeflix.admin.catalog.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.gsv.codeflix.admin.catalog.application.category.retrieve.list.DefaultGetCategoryListUseCase;
import com.gsv.codeflix.admin.catalog.application.category.retrieve.list.GetCategoryListUseCase;
import com.gsv.codeflix.admin.catalog.application.category.update.DefaultUpdateCategoryUseCase;
import com.gsv.codeflix.admin.catalog.application.category.update.UpdateCategoryUseCase;
import com.gsv.codeflix.admin.catalog.domain.category.CategoryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    private final CategoryGateway categoryGateway;

    public UseCaseConfig(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Bean
    public CreateCategoryUseCase createCategoryUseCase() {
        return new DefaultCreateCategoryUseCase(categoryGateway);
    }

    @Bean
    public UpdateCategoryUseCase updateCategoryUseCase() {
        return new DefaultUpdateCategoryUseCase(categoryGateway);
    }

    @Bean
    public GetCategoryByIdUseCase getCategoryByIdUseCase() {
        return new DefaultGetCategoryByIdUseCase(categoryGateway);
    }

    @Bean
    public GetCategoryListUseCase listCategoriesUseCase() {
        return new DefaultGetCategoryListUseCase(categoryGateway);
    }

    @Bean
    public DeleteCategoryUseCase deleteCategoryUseCase() {
        return new DefaultDeleteCategoryUseCase(categoryGateway);
    }
}
