package com.example.cfp.security;

import com.example.cfp.CfpProperties;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;

@Configuration
@EnableOAuth2Sso
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final CfpProperties cfpProperties;

	public SecurityConfig(CfpProperties cfpProperties) {
		this.cfpProperties = cfpProperties;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/", "/news", "/login**", "/css/**", "/img/**", "/webjars/**", "/bootstrap/**").permitAll()
				.anyRequest().authenticated()
				.and()
			.csrf()
				.ignoringAntMatchers("/admin/h2-console/*")
				.and()
			.logout()
				.logoutSuccessUrl("/")
				.permitAll()
				.and()
			.headers()
				.frameOptions().sameOrigin();
	}

	@Bean
	public AuthoritiesExtractor authoritiesExtractor() {
		return map -> {
			String username = (String) map.get("login");
			if (this.cfpProperties.getSecurity().getAdmins().contains(username)) {
				return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER,ROLE_ADMIN");
			}
			else {
				return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
			}
		};
	}

}
