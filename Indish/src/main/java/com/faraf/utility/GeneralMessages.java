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

    public String getMsgAllUsersDeleted() {
        return environment.getProperty("allUsersDeleted");
    }

    public String getMsgDuplicatedUsername() {
        return environment.getProperty("duplicatedUsername");
    }

    public String getMsgDuplicatedEmail() {
        return environment.getProperty("duplicatedEmail");
    }


    public String getMsgUserNotFoundWithId() {
        return environment.getProperty("userNotFoundWithId");
    }

    public String getMsgUserNotFoundWithUsername() {
        return environment.getProperty("userNotFoundWithUsername");
    }

    public String getMsgUserNotFoundWithEmail() {
        return environment.getProperty("userNotFoundWithEmail");
    }

    public String getMsgFoodNotFoundWithId() {
        return environment.getProperty("foodNotFoundWithId");
    }

    public String getMsgIngredientsNotFoundWithFoodName() {
        return environment.getProperty("ingredientsNotFoundWithFoodName");
    }

    public String getMsgIngredientsNotFoundWithFoodId() {
        return environment.getProperty("ingredientsNotFoundWithFoodId");
    }
}
