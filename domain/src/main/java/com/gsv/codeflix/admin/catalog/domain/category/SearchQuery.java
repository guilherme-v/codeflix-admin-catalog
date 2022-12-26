package com.gsv.codeflix.admin.catalog.domain.category;

public record SearchQuery(
        int page,
        int perPage,
        String terms,
        String sort,
        String direction
) {
}
