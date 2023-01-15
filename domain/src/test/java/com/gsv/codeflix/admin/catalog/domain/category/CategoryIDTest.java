package com.gsv.codeflix.admin.catalog.domain.category;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CategoryIDTest {

    @Test
    public void testFromString() {
        var id = "123";
        var categoryID = CategoryID.from(id);
        assertEquals(categoryID.getValue(), id);
    }

    @Test
    public void testFromUUID() {
        var uuid = UUID.randomUUID();
        var categoryID = CategoryID.from(uuid);
        assertEquals(categoryID.getValue(), uuid.toString());
    }

    @Test
    public void testFromUnique() {
        var categoryID1 = CategoryID.unique();
        assertNotNull(categoryID1);
    }

    @Test
    public void testEquals() {
        var uuid = UUID.randomUUID();
        assertEquals(CategoryID.from("123"), CategoryID.from("123"));
        assertEquals(CategoryID.from(uuid), CategoryID.from(uuid));
        assertNotEquals(CategoryID.unique(), CategoryID.unique());
    }

    @Test
    public void testHashCode() {
        final String value = "abc123";
        final CategoryID id1 = CategoryID.from(value);
        final CategoryID id2 = CategoryID.from(value);
        assertEquals(id1.hashCode(), id2.hashCode());
    }
}