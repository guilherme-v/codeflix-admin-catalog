package com.gsv.codeflix.admin.catalog.domain.category;

import com.gsv.codeflix.admin.catalog.domain.validation.Error;
import com.gsv.codeflix.admin.catalog.domain.validation.ValidationHandler;
import com.gsv.codeflix.admin.catalog.domain.validation.Validator;

public class CategoryValidator extends Validator {

    private final Category category;

    public CategoryValidator(Category category, ValidationHandler handler) {
        super(handler);
        this.category = category;
    }

    @Override
    public void validate() {
        checkNameConstraints();
    }

    private void checkNameConstraints() {
        if (category.getName() == null) {
            this.validationHandler().append(new Error("'name' should not be null"));
            return;
        }
        if (category.getName().trim().isEmpty()) {
            this.validationHandler().append(new Error("'name' should not be empty"));
        }
        if (category.getName().length() < 3) {
            this.validationHandler().append(new Error("'name' should have at least 3 characters"));
        }
        if (category.getName().length() > 255) {
            this.validationHandler().append(new Error("'name' should not have more then 255 characters"));
        }
    }
}
