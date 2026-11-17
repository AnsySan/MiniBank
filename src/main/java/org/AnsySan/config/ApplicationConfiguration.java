package org.AnsySan.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan({"org.AnsySan"})
@PropertySource({"classpath:application.properties"})
public class ApplicationConfiguration {
    public ApplicationConfiguration() {
    }
}