package biz.advance_it_group.taxiride_backend.notifications.repositories;

import biz.advance_it_group.taxiride_backend.notifications.fcm.dto.SmsRequest;

public interface SmsSender {

    void sendSms(SmsRequest smsRequest);

}
