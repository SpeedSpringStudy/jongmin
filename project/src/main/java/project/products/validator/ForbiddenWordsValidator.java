package project.products.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ForbiddenWordsValidator implements ConstraintValidator<ForbiddenWords, String> {

    private String[] forbiddenWords;

    @Override
    public void initialize(ForbiddenWords constraintAnnotation) {
        this.forbiddenWords = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name == null) { // null은 @NotBlank 등에서 걸러줘야 함
            return true;
        }

        for (String word : forbiddenWords) {
            if (name.contains(word)) {
                return false;
            }
        }

        return true;
    }
}
