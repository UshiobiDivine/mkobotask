package com.mkobo.mkobotask.config;

import com.mkobo.mkobotask.repository.UserAccountRepository;
import com.mkobo.mkobotask.security.JwtAuthenticationEntryPoint;
import com.mkobo.mkobotask.security.JwtAuthenticationFilter;
import com.mkobo.mkobotask.service.impl.CustomUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		securedEnabled = true,
		jsr250Enabled = true,
		prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private CustomUserDetailsServiceImpl customUserDetailsService;
	private JwtAuthenticationEntryPoint unauthorizedHandler;
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	private UserAccountRepository userRepository;

	@Autowired
	public SecurityConfig(CustomUserDetailsServiceImpl customUserDetailsService,
						  JwtAuthenticationEntryPoint unauthorizedHandler, JwtAuthenticationFilter jwtAuthenticationFilter, UserAccountRepository userRepository) {
		this.customUserDetailsService = customUserDetailsService;
		this.unauthorizedHandler = unauthorizedHandler;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.userRepository = userRepository;
	}


	public void configure(WebSecurity web) throws Exception {
		web.ignoring().mvcMatchers(OPTIONS, "/**")
				.mvcMatchers(POST, "/**")
				.mvcMatchers(GET, "/**")
				.mvcMatchers(DELETE, "/**")
				.mvcMatchers(PUT)
				.mvcMatchers(PATCH, "/**");
//		 ignore swagger
		web.ignoring().mvcMatchers("/swagger-ui.html/**", "/configuration/**", "/swagger-resources/**", "/v2/api-docs");
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				.cors()
				.and()
				.csrf().disable()
				.exceptionHandling()
				.authenticationEntryPoint(unauthorizedHandler)
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				.antMatchers("/api/signup").permitAll()
				.antMatchers("/login").permitAll()
				.anyRequest()
				.authenticated();

		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

	}


//	.antMatchers(HttpMethod.GET, "/api/**").permitAll()
//				.antMatchers(HttpMethod.POST, "/api/**").permitAll()
//				.antMatchers(HttpMethod.DELETE, "/api/**").permitAll()
//				.antMatchers(HttpMethod.PUT, "/api/**").permitAll()
//				.antMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
//				.antMatchers(HttpMethod.GET, "/api/users/checkUsernameAvailability", "/api/users/checkEmailAvailability").permitAll()



	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(customUserDetailsService)
				.passwordEncoder(passwordEncoder());
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
