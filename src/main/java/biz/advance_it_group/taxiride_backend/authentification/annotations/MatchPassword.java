package biz.advance_it_group.taxiride_backend.authentification.annotations;

import biz.advance_it_group.taxiride_backend.authentification.services.helpers.MatchPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MatchPasswordValidator.class)
@Documented
public @interface MatchPassword {

    String message() default "The new passwords must match";
    Class<?>[] groups() default {};
    boolean allowNull() default false;
    Class<? extends Payload>[] payload() default {};
}