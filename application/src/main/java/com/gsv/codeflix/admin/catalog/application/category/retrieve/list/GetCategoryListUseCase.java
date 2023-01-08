package com.gsv.codeflix.admin.catalog.application.category.retrieve.list;

import com.gsv.codeflix.admin.catalog.application.UseCase;
import com.gsv.codeflix.admin.catalog.domain.category.SearchQuery;
import com.gsv.codeflix.admin.catalog.domain.pagination.Pagination;

public abstract class GetCategoryListUseCase extends
        UseCase<SearchQuery, Pagination<GetCategoryListOutput>> {
}
