package biz.advance_it_group.taxiride_backend.authentification.annotations;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;


@Target({ElementType.PARAMETER, ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@AuthenticationPrincipal
public @interface CurrentUser {
}
