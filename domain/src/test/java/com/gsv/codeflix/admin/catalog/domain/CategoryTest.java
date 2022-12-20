package com.gsv.codeflix.admin.catalog.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CategoryTest {

    @Test
    public void TestNewCategory() {
        Assertions.assertNotNull(new Category());
    }
}