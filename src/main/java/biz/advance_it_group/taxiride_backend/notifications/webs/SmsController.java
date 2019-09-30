package biz.advance_it_group.taxiride_backend.notifications.webs;

import biz.advance_it_group.taxiride_backend.notifications.fcm.dto.SmsRequest;
import biz.advance_it_group.taxiride_backend.notifications.services.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(value = "SMS Rest API", description = "Définition des API permettant la gestion des SMS.")
@RestController
@RequestMapping("/api/sms")
public class SmsController {

    private final SmsService service;

    @Autowired
    public SmsController(SmsService service) {
        this.service = service;
    }

    @ApiOperation(value = "send sms the message  ")
    @PostMapping
    public void sendSms(@Valid @RequestBody SmsRequest smsRequest) {
        service.sendSms(smsRequest);
    }
}
