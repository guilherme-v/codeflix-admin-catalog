package com.gsv.codeflix.admin.catalog.application;

import com.gsv.codeflix.admin.catalog.domain.category.Category;

public abstract class UseCase<IN, OUT> {
    public abstract OUT execute(IN param);
}