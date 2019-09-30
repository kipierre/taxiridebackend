package biz.advance_it_group.taxiride_backend.authentification.events;

import biz.advance_it_group.taxiride_backend.authentification.entities.EmailVerificationToken;
import biz.advance_it_group.taxiride_backend.authentification.entities.Users;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.util.UriComponentsBuilder;

public class OnRegenerateEmailVerificationEvent extends ApplicationEvent {

	private UriComponentsBuilder redirectUrl;
	private Users user;
	private EmailVerificationToken token;

	public OnRegenerateEmailVerificationEvent(
            Users user, UriComponentsBuilder redirectUrl, EmailVerificationToken token) {
		super(user);
		this.user = user;
		this.redirectUrl = redirectUrl;
		this.token = token;
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

	public EmailVerificationToken getToken() {
		return token;
	}

	public void setToken(EmailVerificationToken token) {
		this.token = token;
	}
}
