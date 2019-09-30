package biz.advance_it_group.taxiride_backend.authentification.events.listeners;

import biz.advance_it_group.taxiride_backend.authentification.entities.Users;
import biz.advance_it_group.taxiride_backend.authentification.events.OnUserRegistrationCompleteEvent;
import biz.advance_it_group.taxiride_backend.authentification.exceptions.MailSendException;
import biz.advance_it_group.taxiride_backend.authentification.services.impl.EmailVerificationTokenServiceImpl;
import biz.advance_it_group.taxiride_backend.authentification.services.impl.MailService;
import biz.advance_it_group.taxiride_backend.authentification.services.impl.PhoneVerificationTokenServiceImpl;
import biz.advance_it_group.taxiride_backend.notifications.fcm.dto.SmsRequest;
import biz.advance_it_group.taxiride_backend.notifications.services.SmsService;
import freemarker.template.TemplateException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;

@Component
public class OnUserRegistrationCompleteListener implements ApplicationListener<OnUserRegistrationCompleteEvent> {

	@Autowired
	private EmailVerificationTokenServiceImpl emailVerificationTokenServiceImpl;

	@Autowired
	private PhoneVerificationTokenServiceImpl phoneVerificationTokenServiceImpl;

	@Autowired
	private MailService mailService;

	@Autowired
	private SmsService smsService;

	private static final Logger logger = Logger.getLogger(OnUserRegistrationCompleteListener.class);

	/**
	 * A la fin du processus d'enregistrement, demarrer l'envoi de mail et/ou de sms dans un autre Thread de façon asynchrone
	 */
	@Override
	@Async
	public void onApplicationEvent(OnUserRegistrationCompleteEvent onUserRegistrationCompleteEvent) {
		sendPhoneVerification(onUserRegistrationCompleteEvent); // Envoi du code de vérification du téléphone
		sendEmailVerification(onUserRegistrationCompleteEvent); // Envoi du lien de vérification du mail
	}

	/**
	 * Envoi du mail de vérification de l'adresse mail
	 */
	private void sendEmailVerification(OnUserRegistrationCompleteEvent event) {
		Users user = event.getUser();
		String token = emailVerificationTokenServiceImpl.generateNewToken();
		emailVerificationTokenServiceImpl.createVerificationToken(user, token);

		String recipientAddress = user.getEmail();
		String emailConfirmationUrl = event.getRedirectUrl().queryParam("token", token).toUriString();

		try {
			mailService.sendEmailVerification(emailConfirmationUrl, recipientAddress);
		} catch (IOException | TemplateException | MessagingException e) {
			logger.error(e);
			throw new MailSendException(recipientAddress, "Email Verification");
		}
	}

	/**
	 * Envoi du code de vérification du téléphone par sms
	 */
	private void sendPhoneVerification(OnUserRegistrationCompleteEvent event) {
		Users user = event.getUser();
		String token = phoneVerificationTokenServiceImpl.generateNewToken();
		phoneVerificationTokenServiceImpl.createVerificationToken(user, token);
		try {
			smsService.sendSms(new SmsRequest(user.getPhoneNumber(), "Your TaxiRide verification code is " + token));
		} catch (Exception e) {
			logger.error(e);
			// Rejeter une exception d'envoi de sms ici
		}
	}

}
