package com.gsv.codeflix.admin.catalog.infrastructure.category;

import com.gsv.codeflix.admin.catalog.infrastructure.MySQLGatewayTest;
import com.gsv.codeflix.admin.catalog.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


@MySQLGatewayTest
class CategoryMySQLGatewayTest {

    @Autowired
    private CategoryMySQLGateway categoryMySQLGateway;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testInjectDependencies() {
        Assertions.assertNotNull(categoryMySQLGateway);
        Assertions.assertNotNull(categoryRepository);
    }
}