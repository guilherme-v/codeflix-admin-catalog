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

    @Test
    public void givenAnActivatedCategory_whenCallDeactivate_thenReturnDeactivatedUpdatedCategory() {
        final var name = "MOVIE_XYZ";
        final var desc = "Description of the movie";
        final var isActive = true;
        final var category = Category.newCategory(name, desc, isActive);
        final var categoryOriginalUpdatedAt = category.getUpdatedAt();

        final Category actual = category.deactivate();

        Assertions.assertNotNull(actual);

        Assertions.assertFalse(actual.getIsActive());
        Assertions.assertNotEquals(actual.getUpdatedAt(), categoryOriginalUpdatedAt);
        Assertions.assertNotNull(actual.getDeletedAt());

        Assertions.assertEquals(category.getId(), actual.getId());
        Assertions.assertEquals(category.getName(), actual.getName());
        Assertions.assertEquals(category.getDescription(), actual.getDescription());
        Assertions.assertEquals(category.getCreatedAt(), actual.getCreatedAt());

        Assertions.assertDoesNotThrow(() -> actual.validate(new ThrowsValidationHandler()));
    }

    @Test
    public void givenAnDeactivatedCategory_whenCallActivate_thenReturnActivatedUpdatedCategory() {
        final var name = "MOVIE_XYZ";
        final var desc = "Description of the movie";
        final var isActive = false;
        final var category = Category.newCategory(name, desc, isActive);
        final var categoryOriginalUpdatedAt = category.getUpdatedAt();

        final Category actual = category.activate();

        Assertions.assertNotNull(actual);

        Assertions.assertTrue(actual.getIsActive());
        Assertions.assertNotEquals(actual.getUpdatedAt(), categoryOriginalUpdatedAt);
        Assertions.assertNull(actual.getDeletedAt());

        Assertions.assertEquals(category.getId(), actual.getId());
        Assertions.assertEquals(category.getName(), actual.getName());
        Assertions.assertEquals(category.getDescription(), actual.getDescription());
        Assertions.assertEquals(category.getCreatedAt(), actual.getCreatedAt());

        Assertions.assertDoesNotThrow(() -> actual.validate(new ThrowsValidationHandler()));
    }

    @Test
    public void givenACategory_whenCallUpdateWithValidParams_thenReturnUpdatedCategory() {
        final var name = "MOVIE_XYZ";
        final var desc = "Description of the movie";
        final var isActive = false;
        final var category = Category.newCategory(name, desc, isActive);
        final var categoryOriginalUpdatedAt = category.getUpdatedAt();

        final var expectedName = "MOVIE_2";
        final var expectedDesc = "Description of the movie2";
        final var expectedIsActive = true;

        final Category actual = category.update(expectedName, expectedDesc, expectedIsActive);

        Assertions.assertNotNull(actual);

        Assertions.assertEquals(category.getId(), actual.getId());
        Assertions.assertEquals(category.getCreatedAt(), actual.getCreatedAt());

        Assertions.assertEquals(actual.getName(), expectedName);
        Assertions.assertEquals(actual.getDescription(), expectedDesc);
        Assertions.assertTrue(actual.getIsActive());
        Assertions.assertNotEquals(actual.getUpdatedAt(), categoryOriginalUpdatedAt);

        Assertions.assertDoesNotThrow(() -> actual.validate(new ThrowsValidationHandler()));
    }

    @Test
    public void givenACategory_whenCallUpdateWithInvalidParams_thenItShouldThrows() {
        final var name = "MOVIE_XYZ";
        final var desc = "Description of the movie";
        final var isActive = false;
        final var category = Category.newCategory(name, desc, isActive);

        Assertions.assertThrows(DomainException.class, () -> category.update(" ", desc, isActive));
        Assertions.assertDoesNotThrow(() -> category.update(name, " ", isActive));
    }
}