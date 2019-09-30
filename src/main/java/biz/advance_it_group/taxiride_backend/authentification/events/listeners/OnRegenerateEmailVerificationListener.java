package biz.advance_it_group.taxiride_backend.authentification.events.listeners;

import biz.advanceitgroup.taxirideserver.authentification.entities.EmailVerificationToken;
import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.authentification.events.OnRegenerateEmailVerificationEvent;
import biz.advanceitgroup.taxirideserver.authentification.exceptions.MailSendException;
import biz.advanceitgroup.taxirideserver.authentification.services.impl.MailService;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;

@Component
public class OnRegenerateEmailVerificationListener implements ApplicationListener<OnRegenerateEmailVerificationEvent> {

	@Autowired
	private MailService mailService;

	/**
	 * As soon as a registration event is complete, invoke the email verification
	 */
	@Override
	@Async
	public void onApplicationEvent(OnRegenerateEmailVerificationEvent onRegenerateEmailVerificationEvent) {
		resendEmailVerification(onRegenerateEmailVerificationEvent);
	}

	/**
	 * Send email verification to the user and persist the token in the database.
	 */
	private void resendEmailVerification(OnRegenerateEmailVerificationEvent event) {
		Users user = event.getUser();
		EmailVerificationToken emailVerificationToken = event.getToken();
		String recipientAddress = user.getEmail();

		String emailConfirmationUrl =
				event.getRedirectUrl().queryParam("token", emailVerificationToken.getToken()).toUriString();
		try {
			mailService.sendEmailVerification(emailConfirmationUrl, recipientAddress);
		} catch (IOException | TemplateException | MessagingException e) {

			throw new MailSendException(recipientAddress, "Email Verification");
		}
	}

}
