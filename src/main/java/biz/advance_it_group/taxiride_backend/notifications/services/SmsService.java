package biz.advance_it_group.taxiride_backend.notifications.services;

import biz.advance_it_group.taxiride_backend.notifications.configurations.TwilioSmsSender;
import biz.advance_it_group.taxiride_backend.notifications.fcm.dto.SmsRequest;
import biz.advance_it_group.taxiride_backend.notifications.repositories.SmsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@org.springframework.stereotype.Service
public class SmsService {

    private final SmsSender smsSender;

    @Autowired
    public SmsService(@Qualifier("twilio") TwilioSmsSender smsSender) {
        this.smsSender = smsSender;
    }

    public void sendSms(SmsRequest smsRequest) {
        smsSender.sendSms(smsRequest);
    }
}
