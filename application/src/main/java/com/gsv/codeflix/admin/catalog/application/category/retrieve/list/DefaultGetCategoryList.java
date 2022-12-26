package com.gsv.codeflix.admin.catalog.application.category.retrieve.list;

import com.gsv.codeflix.admin.catalog.domain.category.CategoryGateway;
import com.gsv.codeflix.admin.catalog.domain.category.SearchQuery;
import com.gsv.codeflix.admin.catalog.domain.pagination.Pagination;

public class DefaultGetCategoryList extends GetCategoryList {
    private final CategoryGateway categoryGateway;

    public DefaultGetCategoryList(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public Pagination<GetCategoryListOutput> execute(SearchQuery param) {
        return this.categoryGateway.findAll(param)
                .map(GetCategoryListOutput::from);
    }
}
