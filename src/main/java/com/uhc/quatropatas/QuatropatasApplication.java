package com.uhc.quatropatas;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.number.NumberStyleFormatter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;



@SpringBootApplication
public class QuatropatasApplication extends WebMvcConfigurerAdapter{

	public static void main(String[] args) {
		SpringApplication.run(QuatropatasApplication.class, args);
	}
	
	// Alterando as medidas e valores para o padrao brasileiro.
	@Bean
	public LocaleResolver localeResolver(){
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}
	
	/*
	 * Overriding o addFormatters padrão do Spring Boot
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addFormatters(org.springframework.format.FormatterRegistry)
	 */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        
    	/*registry.addConverter(new LocalDateConverter("yyyy-MM-dd"));
        registry.addConverter(new LocalDateTimeConverter("yyyy-MM-dd'T'HH:mm:ss.SSS"));*/
        
		// API de Datas do Java 8
		DateTimeFormatterRegistrar dateTimeFormatter = new DateTimeFormatterRegistrar();
		dateTimeFormatter.setDateFormatter(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		dateTimeFormatter.setTimeFormatter(DateTimeFormatter.ofPattern("HH:mm"));
		dateTimeFormatter.registerFormatters(registry);
		
    	
		NumberStyleFormatter bigDecimalFormatter = new NumberStyleFormatter("#,##0.00");
		registry.addFormatterForFieldType(BigDecimal.class, bigDecimalFormatter);
		
		NumberStyleFormatter integerFormatter = new NumberStyleFormatter("#,##0");
		registry.addFormatterForFieldType(Integer.class, integerFormatter);
    }

}
