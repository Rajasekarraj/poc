package com.example.producer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.producer.model.User;



@RestController
@RequestMapping("/kafka")
@EnableBinding(Source.class)
public class ProducerController {
	@Autowired
	private Source sourceChannel;
	@PostMapping("/send")
	public String postData(@RequestBody User user) {
		sourceChannel.output().send(MessageBuilder.withPayload(user).build());
		return "Sent to topic successfully";
	}
}
