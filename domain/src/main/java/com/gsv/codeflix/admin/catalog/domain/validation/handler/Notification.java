package com.gsv.codeflix.admin.catalog.domain.validation.handler;

import com.gsv.codeflix.admin.catalog.domain.exceptions.DomainException;
import com.gsv.codeflix.admin.catalog.domain.validation.Error;
import com.gsv.codeflix.admin.catalog.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class Notification implements ValidationHandler {

    final List<Error> errors;

    private Notification(List<Error> errors) {
        this.errors = errors;
    }


    public static Notification create() {
        return new Notification(new ArrayList<>());
    }

    public static Notification create(Error e) {
        return new Notification(new ArrayList<>()).append(e);
    }

    public static Notification create(final Throwable t) {
        return Notification.create(new Error(t.getMessage()));
    }

    @Override
    public Notification append(final Error anError) {
        this.errors.add(anError);
        return this;
    }

    @Override
    public Notification append(final ValidationHandler anHandler) {
        this.errors.addAll(anHandler.getErrors());
        return this;
    }

    @Override
    public Notification validate(final Validation aValidation) {
        try {
            aValidation.validate();
        } catch (final DomainException ex) {
            this.errors.addAll(ex.getErrors());
        } catch (final Throwable t) {
            this.errors.add(new Error(t.getMessage()));
        }
        return null;
    }

    @Override
    public List<Error> getErrors() {
        return this.errors;
    }
}
