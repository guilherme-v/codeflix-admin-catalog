package com.gsv.codeflix.admin.catalog.domain.category;

import com.gsv.codeflix.admin.catalog.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class CategoryID extends Identifier {
    private final String value;

    private CategoryID(final String value) {
        this.value = value;
    }

    public static CategoryID from(final String value) {
        return new CategoryID(value);
    }

    public static CategoryID from(final UUID uuid) {
        return new CategoryID(uuid.toString().toLowerCase());
    }

    public static CategoryID unique() {
        return CategoryID.from(UUID.randomUUID());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CategoryID that = (CategoryID) o;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
