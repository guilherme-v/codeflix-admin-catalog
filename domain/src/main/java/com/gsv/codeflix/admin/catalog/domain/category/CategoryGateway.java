package com.gsv.codeflix.admin.catalog.domain.category;

import com.gsv.codeflix.admin.catalog.domain.pagination.Pagination;

import java.util.Optional;

public interface CategoryGateway {

    Category create(Category category);
    Category update(Category category);
    Category deleteById(Category category);

    Optional<Category> findById(CategoryID id);
    Pagination<Category> findAll(CategorySearchQuery query);
}
