package com.uhc.quatropatas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.uhc.quatropatas.security.AppUserDetailsService;

@EnableWebSecurity
@Configuration
@ComponentScan(basePackageClasses = AppUserDetailsService.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//		auth.inMemoryAuthentication()
//			.withUser("joao").password("joao").roles("PESQUISAR_RACA").and()
//			.withUser("maria").password("maria").roles("CADASTRAR_RACA", "PESQUISAR_RACA").and()
//			.withUser("admin").password("").roles("CADASTRAR_RACA", "PESQUISAR_RACA", 
//					"CADASTRAR_ANIMAL", "PESQUISAR_ANIMAL");
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
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
		//http.csrf().disable();
		http
			.csrf().disable()
			.authorizeRequests()
				//.antMatchers("/racas").hasRole("PESQUISAR_RACA")
				//.antMatchers("/racas/**").hasRole("CADASTRAR_RACA")
				.antMatchers("/usuarios/**").hasRole("CADASTRAR_USUARIO")
				.antMatchers("/servicos/novo").hasRole("CADASTRAR_SERVICO")
				.antMatchers("/cidades/novo").hasRole("CADASTRAR_CIDADE")
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	
}
