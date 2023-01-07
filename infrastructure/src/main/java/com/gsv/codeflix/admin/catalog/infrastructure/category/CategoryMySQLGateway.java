package com.gsv.codeflix.admin.catalog.infrastructure.category;

import com.gsv.codeflix.admin.catalog.domain.category.Category;
import com.gsv.codeflix.admin.catalog.domain.category.CategoryGateway;
import com.gsv.codeflix.admin.catalog.domain.category.CategoryID;
import com.gsv.codeflix.admin.catalog.domain.category.SearchQuery;
import com.gsv.codeflix.admin.catalog.domain.pagination.Pagination;
import com.gsv.codeflix.admin.catalog.infrastructure.category.persistence.CategoryRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CategoryMySQLGateway implements CategoryGateway {

    private final CategoryRepository repository;

    public CategoryMySQLGateway(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category create(Category category) {
        return null;
    }

    @Override
    public Category update(Category category) {
        return null;
    }

    @Override
    public void deleteById(CategoryID category) {

    }

    @Override
    public Optional<Category> findById(CategoryID id) {
        return Optional.empty();
    }

    @Override
    public Pagination<Category> findAll(SearchQuery query) {
        return null;
    }
}
