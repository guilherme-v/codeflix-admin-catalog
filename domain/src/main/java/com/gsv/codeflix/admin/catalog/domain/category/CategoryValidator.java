package com.gsv.codeflix.admin.catalog.domain.category;

import com.gsv.codeflix.admin.catalog.domain.validation.Error;
import com.gsv.codeflix.admin.catalog.domain.validation.ValidationHandler;
import com.gsv.codeflix.admin.catalog.domain.validation.Validator;

public class CategoryValidator extends Validator {

    public static final int NAME_MIN_LENGTH = 3;
    public static final int NAME_MAX_LENGTH = 255;
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
            this.handler.append(new Error("'name' should not be null"));
            return;
        }
        if (category.getName().trim().isEmpty()) {
            this.handler.append(new Error("'name' should not be empty"));
        }
        if (category.getName().length() < NAME_MIN_LENGTH) {
            this.handler.append(new Error("'name' should have at least 3 characters"));
        }
        if (category.getName().length() > NAME_MAX_LENGTH) {
            this.handler.append(new Error("'name' should not have more then 255 characters"));
        }
    }
}
