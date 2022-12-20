package com.gsv.codeflix.admin.catalog.domain.category;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CategoryTest {

    @Test
    public void testNewCategory() {
        // Given
        final var name = "MOVIE_XYZ";
        final var desc = "Description of the movie";
        final var isActive = true;

        // When
        final var actual = Category.newCategory(name, desc, isActive);

        // Then
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        Assertions.assertEquals(name, actual.getName());
        Assertions.assertEquals(desc, actual.getDescription());
        Assertions.assertEquals(isActive, actual.getIsActive());
        Assertions.assertNotNull(actual.getCreatedAt());
        Assertions.assertNotNull(actual.getUpdatedAt());
        Assertions.assertNull(actual.getDeletedAt());
    }
}