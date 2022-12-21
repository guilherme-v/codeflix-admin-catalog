package com.gsv.codeflix.admin.catalog.domain.exceptions;

import com.gsv.codeflix.admin.catalog.domain.validation.Error;

import java.util.List;

public class DomainException extends NoStacktraceException {

    private final List<Error> errors;

    public DomainException(final String message, final List<Error> errors) {
        super(message);
        this.errors = errors;
    }

    public static DomainException with(Error e) {
        return new DomainException(e.message(), List.of(e));
    }

    public static DomainException with(final List<Error> errors) {
        return new DomainException("",  errors);
    }

    public List<Error> getErrors() {
        return errors;
    }
}
