package com.gsv.codeflix.admin.catalog.domain.pagination;

import java.util.List;

public record Pagination<T>(
        int currentPage,
        int nextPage,
        int totalPages,
        List<T> items
) {
}
