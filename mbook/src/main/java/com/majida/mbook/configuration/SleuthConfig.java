package com.majida.mbook.configuration;

import brave.sampler.Sampler;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SleuthConfig {

/*
Always_SAMPLE dit à Sleuth de prendre toutes les requetes et de les exporter vers zipkin afin que celuici puisse les analyser
 */
    public Sampler defaultSampler(){
        return Sampler.ALWAYS_SAMPLE;
    }
}