package biz.advance_it_group.taxiride_backend.notifications.fcm;

import biz.advance_it_group.taxiride_backend.notifications.fcm.dto.Push;
import biz.advance_it_group.taxiride_backend.notifications.filters.HeaderRequestInterceptor;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * <p>
 * <b> Push Notification Service </b>
 * </p>
 * <p>
 * Service send message to FCM
 * </p>
 */

@Service
public class PushNotificationService {

	//@Value("${my.fcm.key}")
	//private String fcmKey;
 private  String fcmKey = "AAAAVAR8m2U:APA91bEzQkXWwY8fyR6zmWcBH0x5m4sM5XqavHidYJ91zYSbJfolMg4XM7xBkBovQOovHzLkh_SSQq9gqhmgLna54NQQ2xW1xtohOxk-di7A0pny1itUYjz_SEr-KKmQ_T-keo1gKCiu";

	private static final String FCM_API = "https://fcm.googleapis.com/fcm/send";

	/**
	 * 
	 * @param push
	 * @return
	 */

	public FirebaseResponse sendNotification(Push push) {

		HttpEntity<Push> request = new HttpEntity<>(push);

		CompletableFuture<FirebaseResponse> pushNotification = this.send(request);
		CompletableFuture.allOf(pushNotification).join();

		FirebaseResponse firebaseResponse = null;

		try {
			firebaseResponse = pushNotification.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return firebaseResponse;
	}

	/**
	 * send push notifications to API FCM
	 * 
	 * Using CompletableFuture with @Async to provide Asynchronous call.
	 * 
	 * 
	 * @param entity
	 * @return
	 */
	@Async
	private CompletableFuture<FirebaseResponse> send(HttpEntity<Push> entity) {

		RestTemplate restTemplate = new RestTemplate();

		ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + fcmKey));
		interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
		restTemplate.setInterceptors(interceptors);

		FirebaseResponse firebaseResponse = restTemplate.postForObject(FCM_API, entity, FirebaseResponse.class);

		return CompletableFuture.completedFuture(firebaseResponse);
	}
}
