package com.gsv.codeflix.admin.catalog.domain.validation;

import java.util.List;

public interface ValidationHandler {

    ValidationHandler append(Error e);

    ValidationHandler append(ValidationHandler handler);

    ValidationHandler validate(Validation validation);

    default boolean hasErrors() {
        return getErrors() != null && getErrors().size() != 0;
    }

    List<Error> getErrors();

    public interface Validation {
        void validate();
    }
}
