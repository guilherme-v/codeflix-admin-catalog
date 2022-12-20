package com.gsv.codeflix.admin.catalog.application;

import com.gsv.codeflix.admin.catalog.domain.Category;

public class UseCase {
    public Category execute() {
        return new Category();
    }
}