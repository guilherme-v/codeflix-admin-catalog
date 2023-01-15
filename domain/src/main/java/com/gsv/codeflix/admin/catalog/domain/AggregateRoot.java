package com.gsv.codeflix.admin.catalog.domain;

/**
 * An aggregate is a collection of one or more related entities (and possibly value objects).
 * Each aggregate has a single root entity, referred to as the aggregate root.
 * The aggregate root is responsible for controlling access to all of the members
 * of its aggregate.
 * */
public abstract class AggregateRoot<ID extends Identifier> extends Entity<ID> {
    protected AggregateRoot(final ID id) {
        super(id);
    }
}
