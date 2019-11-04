package com.example.processor.configuration;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;

public interface ProcessorConfiguration extends Processor{

    @Output("failedMessage")
    MessageChannel failedMessage();

}
