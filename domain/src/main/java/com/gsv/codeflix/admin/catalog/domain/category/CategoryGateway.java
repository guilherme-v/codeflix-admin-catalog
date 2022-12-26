package com.gsv.codeflix.admin.catalog.domain.category;

import com.gsv.codeflix.admin.catalog.domain.pagination.Pagination;

import java.util.Optional;

public interface CategoryGateway {

    Category create(Category category);
    Category update(Category category);
    void deleteById(CategoryID category);

    Optional<Category> findById(CategoryID id);
    Pagination<Category> findAll(SearchQuery query);
}
