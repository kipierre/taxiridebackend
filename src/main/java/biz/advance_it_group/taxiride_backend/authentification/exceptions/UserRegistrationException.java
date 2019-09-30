package biz.advance_it_group.taxiride_backend.authentification.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class UserRegistrationException extends RuntimeException {
	private String user;
	private String message;

	public UserRegistrationException(String user, String message) {
		super(String.format("Failed to register Users[%d] : '%s'", user, message));
		this.user = user;
		this.message = message;
	}
	public UserRegistrationException(String message, Throwable cause) {
		super(message, cause);
	}
}