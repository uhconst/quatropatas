package com.uhc.quatropatas.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.inMemoryAuthentication()
			.withUser("joao").password("joao").roles("PESQUISAR_RACA").and()
			.withUser("maria").password("maria").roles("CADASTRAR_RACA", "PESQUISAR_RACA").and()
			.withUser("admin").password("").roles("CADASTRAR_RACA", "PESQUISAR_RACA", 
					"CADASTRAR_ANIMAL", "PESQUISAR_ANIMAL");
	}
	
	/*
	 * Para ignorar o bloqueio ao layout na tela de login.
	 * Assim ele dará acesso ao layout antes da pessoa logar.
	 * Depois de feito o login, terá acesso ao resto.
	 */
	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring()
			.antMatchers("/layout/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.authorizeRequests()
				.antMatchers("/racas").hasRole("PESQUISAR_RACA")
				.antMatchers("/racas/**").hasRole("CADASTRAR_RACA")
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}
}
