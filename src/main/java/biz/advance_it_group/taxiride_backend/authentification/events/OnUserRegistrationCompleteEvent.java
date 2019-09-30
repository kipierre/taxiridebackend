package biz.advance_it_group.taxiride_backend.authentification.events;

import biz.advance_it_group.taxiride_backend.authentification.entities.Users;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.util.UriComponentsBuilder;

public class OnUserRegistrationCompleteEvent extends ApplicationEvent {

	private UriComponentsBuilder redirectUrl;
	private Users user;

	public OnUserRegistrationCompleteEvent(
            Users user, UriComponentsBuilder redirectUrl) {
		super(user);
		this.user = user;
		this.redirectUrl = redirectUrl;
	}

	public UriComponentsBuilder getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(UriComponentsBuilder redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
}
