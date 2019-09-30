package biz.advance_it_group.taxiride_backend.authentification.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class RoleRegistrationException extends RuntimeException {
    private String role;
    private String message;

    public RoleRegistrationException(String role, String message) {
        super(String.format("Failed to register Users[%d] : '%s'", role, message));
        this.role = role;
        this.message = message;
    }

    public RoleRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
