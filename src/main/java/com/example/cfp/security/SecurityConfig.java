package com.example.cfp.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/", "/news", "/login**", "/css/**", "/img/**", "/webjars/**", "/bootstrap/**").permitAll()
				.antMatchers("/submit").permitAll()
				.anyRequest().authenticated()
				.and()
			.csrf()
				.ignoringAntMatchers("/admin/h2-console/*")
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
			.logout()
				.logoutSuccessUrl("/")
				.permitAll()
				.and()
			.headers()
				.frameOptions().sameOrigin();
	}

}
