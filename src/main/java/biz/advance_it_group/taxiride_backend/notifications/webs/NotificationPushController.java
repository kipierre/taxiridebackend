package biz.advance_it_group.taxiride_backend.notifications.webs;

import biz.advance_it_group.taxiride_backend.authentification.entities.Users;
import biz.advance_it_group.taxiride_backend.authentification.repositories.UserRepository;
import biz.advance_it_group.taxiride_backend.notifications.fcm.PushNotificationService;
import biz.advance_it_group.taxiride_backend.notifications.fcm.dto.Notification;
import biz.advance_it_group.taxiride_backend.notifications.fcm.dto.Push;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(value = "Push notification Rest API", description = "Définition des API permettant la gestion des notifications push.")
@RestController
@RequestMapping("/api/push-notification")
public class NotificationPushController {


	@Autowired
	private PushNotificationService pushNotification;


	@Autowired
	private UserRepository userRepository;


	@RequestMapping(value = "/usernotification", method = RequestMethod.POST)
	public ResponseEntity<Users> saveProject(@RequestBody Users user) {

		Users userSaved = userRepository.save(user);
              //userRepository.saveAndFlush(user);
		return new ResponseEntity<Users>(userSaved, HttpStatus.CREATED);

	}

	@ApiOperation(value = "send dthe notifications in all user")
	@RequestMapping(value = "/pushAll", method = RequestMethod.GET)
	public ResponseEntity<?> pushAll() {

		List<String> tokens = new ArrayList<>();
		List<Users> users = userRepository.findAll();

		users.forEach(p -> tokens.add(p.getTokenFCM().getToken()));

		Notification notification = new Notification("default", "My App", "Test");
		Push push = new Push("high", notification, tokens);
		pushNotification.sendNotification(push);

		return new ResponseEntity<Users>(HttpStatus.OK);
	}

	@ApiOperation(value = "send simple push notifications" )
	@RequestMapping(value = "/push", method = RequestMethod.GET)
	public ResponseEntity<?> push() {

		userRepository.findFirstByOrderByName().ifPresent(p -> {
			Notification notification = new Notification("default", "My app", "Teste");
			Push push = new Push(p.getTokenFCM().getToken(), "high", notification);
			pushNotification.sendNotification(push);
		});
		
		return new ResponseEntity<Users>(HttpStatus.OK);
	}
}
