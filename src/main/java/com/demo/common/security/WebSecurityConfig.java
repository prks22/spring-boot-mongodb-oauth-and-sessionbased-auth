package com.demo.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.session.ExpiringSession;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

@Configuration
@EnableWebSecurity
@EnableSpringHttpSession
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	/** The Constant TOKEN_REFRESH_ENTRY_POINT. */
	public static final String LOGIN_URL = "/users/login";
	public static final String SIGNUP = "/users/signup";
	public static final String ROOT = "**/users/**";

	/** The authentication provider. */
	@Autowired
	AuthenticationProvider authenticationProvider;

	@Bean
	SessionRepository<ExpiringSession> inmemorySessionRepository() {
		return new MapSessionRepository();
	}

	@Bean
	public HttpSessionStrategy httpSessionStrategy() {
		return new HeaderHttpSessionStrategy();
	}

	@Autowired
	public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);

	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
				.authorizeRequests()
				.antMatchers(LOGIN_URL, SIGNUP, ROOT, "/users/user/facebook", "/login", "/login/facebook").permitAll()
				.anyRequest().authenticated().and().requestCache()
				// .anyRequest().permitAll().and().requestCache()
				.requestCache(new NullRequestCache()).and().httpBasic();

		http.exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint());
		http.addFilterBefore(new AuthorizationValidatorFilter(), BasicAuthenticationFilter.class);
	}

}