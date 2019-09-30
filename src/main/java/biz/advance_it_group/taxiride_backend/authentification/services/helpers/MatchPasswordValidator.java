package biz.advance_it_group.taxiride_backend.authentification.services.helpers;

import biz.advance_it_group.taxiride_backend.authentification.annotations.MatchPassword;
import biz.advance_it_group.taxiride_backend.authentification.dto.PasswordResetRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MatchPasswordValidator implements ConstraintValidator<MatchPassword, PasswordResetRequest> {

    private Boolean allowNull;

    @Override
    public void initialize(MatchPassword constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(PasswordResetRequest value, ConstraintValidatorContext context) {
        String password = value.getPassword();
        String confirmPassword = value.getConfirmPassword();
        if (allowNull) {
            return null == password && null == confirmPassword;
        }
        return password.equals(confirmPassword);
    }
}

