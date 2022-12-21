package com.gsv.codeflix.admin.catalog.domain.category;

import com.gsv.codeflix.admin.catalog.domain.exceptions.DomainException;
import com.gsv.codeflix.admin.catalog.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CategoryTest {

    @Test
    public void givenValidParams_whenCallNewCategory_thenReturnACategory() {
        final var name = "MOVIE_XYZ";
        final var desc = "Description of the movie";
        final var isActive = true;

        final var actual = Category.newCategory(name, desc, isActive);

        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        Assertions.assertEquals(name, actual.getName());
        Assertions.assertEquals(desc, actual.getDescription());
        Assertions.assertEquals(isActive, actual.getIsActive());
        Assertions.assertNotNull(actual.getCreatedAt());
        Assertions.assertNotNull(actual.getUpdatedAt());
        Assertions.assertNull(actual.getDeletedAt());
    }

    @Test
    public void givenNullName_whenCallNewCategory_thenThroughException() {
        final String name = null;
        final var desc = "Description of the movie";
        final var isActive = true;

        final var actual = Category.newCategory(name, desc, isActive);

        final var actualExp = Assertions.assertThrows(DomainException.class, () -> actual.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(1, actualExp.getErrors().size());
        Assertions.assertEquals("'name' should not be null", actualExp.getErrors().get(0).message());
    }

    @Test
    public void givenEmptyName_whenCallNewCategory_thenThroughException() {
        final String name = " ";
        final var desc = "Description of the movie";
        final var isActive = true;

        final var actual = Category.newCategory(name, desc, isActive);

        final var actualExp = Assertions.assertThrows(DomainException.class, () -> actual.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(1, actualExp.getErrors().size());
        Assertions.assertEquals("'name' should not be empty", actualExp.getErrors().get(0).message());
    }

    @Test
    public void givenNameWithLengthLessThen3_whenCallNewCategory_thenThroughException() {
        final String name = "12";
        final var desc = "Description of the movie";
        final var isActive = true;

        final var actual = Category.newCategory(name, desc, isActive);

        final var actualExp = Assertions.assertThrows(DomainException.class, () -> actual.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(1, actualExp.getErrors().size());
        Assertions.assertEquals("'name' should have at least 3 characters", actualExp.getErrors().get(0).message());
    }

    @Test
    public void givenNameWithLengthMoreThen255_whenCallNewCategory_thenThroughException() {
        final String name256 = "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";
        final var desc = "Description of the movie";
        final var isActive = true;

        final var actual = Category.newCategory(name256, desc, isActive);

        final var actualExp = Assertions.assertThrows(DomainException.class, () -> actual.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(1, actualExp.getErrors().size());
        Assertions.assertEquals("'name' should not have more then 255 characters", actualExp.getErrors().get(0).message());
    }
}