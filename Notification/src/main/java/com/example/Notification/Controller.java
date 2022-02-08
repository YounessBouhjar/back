package com.example.Notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/notification")
@CrossOrigin(allowCredentials = "true",  originPatterns = "*")

public class Controller {
    @Autowired
    private final Service service;

    public Controller(Service service) {
        this.service = service;
    }

    @PostMapping("/send")
    public void sendSms(@Valid @RequestBody SmsRequest smsRequest) {
        service.sendSms(smsRequest);
    }

}
