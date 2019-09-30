package biz.advance_it_group.taxiride_backend.authentification.events.listeners;

import biz.advance_it_group.taxiride_backend.authentification.entities.Users;
import biz.advance_it_group.taxiride_backend.authentification.events.OnUserAccountChangeEvent;
import biz.advance_it_group.taxiride_backend.authentification.exceptions.MailSendException;
import biz.advance_it_group.taxiride_backend.authentification.services.impl.MailService;
import freemarker.template.TemplateException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;

@Component
public class OnUserAccountChangeListener implements ApplicationListener<OnUserAccountChangeEvent> {

	@Autowired
	private MailService mailService;

	private static final Logger logger = Logger.getLogger(OnUserAccountChangeListener.class);

	/**
	 * As soon as a registration event is complete, invoke the email verification
	 * asynchronously in an another thread pool
	 */
	@Override
	@Async
	public void onApplicationEvent(OnUserAccountChangeEvent onUserAccountChangeEvent) {
		sendAccountChangeEmail(onUserAccountChangeEvent);
	}

	/**
	 * Send email verification to the user and persist the token in the database.
	 */
	private void sendAccountChangeEmail(OnUserAccountChangeEvent event) {
		Users user = event.getUser();
		String action = event.getAction();
		String actionStatus = event.getActionStatus();
		String recipientAddress = user.getEmail();

		try {
			mailService.sendAccountChangeEmail(action, actionStatus, recipientAddress);
		} catch (IOException | TemplateException | MessagingException e) {
			logger.error(e);
			throw new MailSendException(recipientAddress, "Account Change Mail");
		}
	}
}
