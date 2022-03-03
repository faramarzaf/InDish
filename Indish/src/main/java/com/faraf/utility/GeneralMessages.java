package com.faraf.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:/messages.properties")
public class GeneralMessages {

    @Autowired
    private Environment environment;

    @Bean
    public String getMsgAllUsersDeleted() {
        return environment.getProperty("allUsersDeleted");
    }

    @Bean
    public String getMsgDuplicatedUsername() {
        return environment.getProperty("duplicatedUsername");
    }

    @Bean
    public String getMsgDuplicatedEmail() {
        return environment.getProperty("duplicatedEmail");
    }


    @Bean
    public String getMsgUserNotFoundWithId() {
        return environment.getProperty("userNotFoundWithId");
    }

    @Bean
    public String getMsgUserNotFoundWithUsername() {
        return environment.getProperty("userNotFoundWithUsername");
    }

    @Bean
    public String getMsgUserNotFoundWithEmail() {
        return environment.getProperty("userNotFoundWithEmail");
    }


}
