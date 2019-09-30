package biz.advance_it_group.taxiride_backend.authentification.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class UserLogoutException extends RuntimeException {

	private String user;
	private String message;

	public UserLogoutException(String user, String message) {
		super(String.format("Couldn't log out device [%s]: [%s])", user, message));
		this.user = user;
		this.message = message;
	}
}