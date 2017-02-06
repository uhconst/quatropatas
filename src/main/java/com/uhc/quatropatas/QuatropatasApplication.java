package com.uhc.quatropatas;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;



@SpringBootApplication
public class QuatropatasApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuatropatasApplication.class, args);
	}
	
	// Alterando as medidas e valores para o padrao brasileiro.
	@Bean
	public LocaleResolver localeResolver(){
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}
}
