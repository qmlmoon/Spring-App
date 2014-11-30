package com.mingliang.security.ldap;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/greeting", "/*news", "/getdir", "/getfile/*").permitAll()
			.anyRequest()
			.fullyAuthenticated()
			.and().formLogin()
			.and().httpBasic().disable().csrf().disable();
	}

	@Configuration
	protected static class AuthenticationConfiguration extends
		GlobalAuthenticationConfigurerAdapter {

		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception {
			auth.ldapAuthentication().userDnPatterns("uid={0},ou=people")
				.groupSearchBase("ou=groups").contextSource()
				.ldif("classpath:test-server.ldif");
		}
	}
}