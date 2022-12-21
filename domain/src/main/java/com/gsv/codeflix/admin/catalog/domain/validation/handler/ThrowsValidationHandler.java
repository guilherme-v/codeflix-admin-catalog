package com.gsv.codeflix.admin.catalog.domain.validation.handler;

import com.gsv.codeflix.admin.catalog.domain.exceptions.DomainException;
import com.gsv.codeflix.admin.catalog.domain.validation.Error;
import com.gsv.codeflix.admin.catalog.domain.validation.ValidationHandler;

import java.util.List;

public class ThrowsValidationHandler implements ValidationHandler {
    @Override
    public ValidationHandler append(final Error e) {
        throw DomainException.with(e);
    }

    @Override
    public ValidationHandler append(final ValidationHandler handler) {
        throw DomainException.with(handler.getErrors());
    }

    @Override
    public ValidationHandler validate(final Validation validation) {
        try {
            validation.validate();
        } catch (final Exception e) {
            throw DomainException.with(new Error(e.getMessage()));
        }

        return this;
    }

    @Override
    public List<Error> getErrors() {
        return List.of();
    }
}
