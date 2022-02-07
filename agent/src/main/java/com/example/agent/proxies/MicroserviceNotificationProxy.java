package com.example.agent.proxies;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.agent.beans.NotificationBean;
@FeignClient(name = "notification-service",url="localhost:8080")

public interface MicroserviceNotificationProxy {

	
	
	@PostMapping("/notification/send")
    public void sendSms( @RequestBody NotificationBean smsRequest);
}
