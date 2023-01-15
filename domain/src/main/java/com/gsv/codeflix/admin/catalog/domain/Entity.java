package com.gsv.codeflix.admin.catalog.domain;

import com.gsv.codeflix.admin.catalog.domain.validation.ValidationHandler;

import java.util.Objects;

/**
 * Eric Evan's classification:
 * In Domain Driver Design, Entities are objects that have a distinct identity that runs
 * through time and different representations. You also hear these called `reference objects`.
 *
 * <p>
 * As such some examples may help. Entities are usually big things like Customer, Ship, Rental Agreement.
 * Values are usually little things like Date, Money, Database Query.
 * <p>
 * One clear division between entities and values is that values override the equality method
 * (and thus hash) while entities usually don't. This is because you usually don't want to have more
 * than one object representing the same conceptual entity within your processing context,
 * however you don't care about multiple "5.0" objects.
 */
public abstract class Entity<ID extends Identifier> {
    protected final ID id;

    protected Entity(final ID id) {
        Objects.requireNonNull(id, "'id' should not be null");
        this.id = id;
    }

    public ID getId() {
        return id;
    }

    public abstract void validate(ValidationHandler handler);
}
