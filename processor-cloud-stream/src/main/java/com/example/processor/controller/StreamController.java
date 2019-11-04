package com.example.processor.controller;

import java.util.concurrent.TimeUnit;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.binder.BinderHeaders;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;

import com.example.processor.configuration.ProcessorConfiguration;
import com.example.processor.model.User;



@EnableBinding(ProcessorConfiguration.class)
public class StreamController {
	
	@Autowired
	ProcessorConfiguration processChannel;
	
	private static final String X_RETRIES_HEADER = "x-retries";
	
	@StreamListener(Processor.INPUT)
	@SendTo(Processor.OUTPUT)
	public Message<User> reRoute(Message<User> failed) throws InterruptedException {
        Integer retries = failed.getHeaders().get(X_RETRIES_HEADER, Integer.class);
        System.out.println("retry header :"+retries);
        if (retries == null) {
            System.out.println("First retry for " + failed);
            System.out.println("headers:"+failed .getHeaders());
            TimeUnit.SECONDS.sleep(5);
            return MessageBuilder.fromMessage(failed)
                    .setHeader(X_RETRIES_HEADER, new Integer(1))
                    .setHeader(BinderHeaders.PARTITION_OVERRIDE,
                            failed.getHeaders().get(KafkaHeaders.RECEIVED_PARTITION_ID))
                    .build();
        }
        else if (retries.intValue() < 3) {
            System.out.println("Another retry for " + failed);
            System.out.println("headers:"+failed .getHeaders());
            TimeUnit.SECONDS.sleep(5);
            return MessageBuilder.fromMessage(failed)
                    .setHeader(X_RETRIES_HEADER, new Integer(retries.intValue() + 1))
                    .setHeader(BinderHeaders.PARTITION_OVERRIDE,
                            failed.getHeaders().get(KafkaHeaders.RECEIVED_PARTITION_ID))
                    .build();
        }
        else {
            System.out.println("Retries exhausted for " + failed);
            System.out.println("headers:"+failed .getHeaders());
            processChannel.failedMessage().send(MessageBuilder.fromMessage(failed)
                    .setHeader(BinderHeaders.PARTITION_OVERRIDE,
                            failed.getHeaders().get(KafkaHeaders.RECEIVED_PARTITION_ID))
                    .build());
        }
        return null;
    }

}
