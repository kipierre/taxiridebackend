package biz.advance_it_group.taxiride_backend.commons.helpers;

import java.util.UUID;

public class Util {

	public static Boolean isTrue(Boolean value) {
		return true == value;
	}

	public static Boolean isFalse(Boolean value) {
		return false == value;
	}

	public static String generateRandomUuid() {
		return UUID.randomUUID().toString();
	}
}
