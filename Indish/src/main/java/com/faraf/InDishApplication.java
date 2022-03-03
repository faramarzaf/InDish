package com.faraf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
public class InDishApplication {

	public static void main(String[] args) {
		SpringApplication.run(InDishApplication.class, args);
	}


	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * An application context delegates the message resolution to a bean with the exact name messageSource.
	 * ReloadableResourceBundleMessageSource is the most common MessageSource implementation that
	 * resolves messages from resource bundles for different locales
	 */
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	/**
	 * To use custom name messages in a properties file like we need to define a
	 * LocalValidatorFactoryBean and register the messageSource
	 */
	@Bean
	public LocalValidatorFactoryBean validator() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());
		return bean;
	}

/*	@Bean
	public MyUserMapper myUserMapper(){
		return new MyUserMapperImpl();
	}*/
}
