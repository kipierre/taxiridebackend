package biz.advance_it_group.taxiride_backend.authentification.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class UserLoginException extends RuntimeException {
	public UserLoginException(String message) {
		super(message);
	}
	public UserLoginException(String message, Throwable cause) {
		super(message, cause);
	}
}