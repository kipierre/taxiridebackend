package biz.advance_it_group.taxiride_backend.authentification.configurations;

import biz.advance_it_group.taxiride_backend.authentification.securities.JwtAuthenticationEntryPoint;
import biz.advance_it_group.taxiride_backend.authentification.securities.JwtAuthenticationFilter;
import biz.advance_it_group.taxiride_backend.authentification.securities.oauth2.CustomOAuth2UserService;
import biz.advance_it_group.taxiride_backend.authentification.securities.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import biz.advance_it_group.taxiride_backend.authentification.securities.oauth2.OAuth2AuthenticationFailureHandler;
import biz.advance_it_group.taxiride_backend.authentification.securities.oauth2.OAuth2AuthenticationSuccessHandler;
import biz.advance_it_group.taxiride_backend.authentification.services.impl.CustomUserDetailsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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


@Configuration
@EnableWebSecurity(debug = true)
@EnableJpaRepositories(basePackages = "biz.advanceitgroup.taxirideserver")
@EnableGlobalMethodSecurity(
		securedEnabled = true,
		jsr250Enabled = true,
		prePostEnabled = true)

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService userDetailsService;

	private static final Logger logger = Logger.getLogger(WebSecurityConfig.class);

	@Autowired
	private JwtAuthenticationEntryPoint jwtEntryPoint;

	@Autowired
	private CustomOAuth2UserService customOAuth2UserService;

	@Autowired
	private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

	@Autowired
	private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

	@Autowired
	private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;


	/*
      Spring OAuth2 utilise par défaut HttpSessionOAuth2AuthorizationRequestRepository pour enregistrer
      la demande d'autorisation. Mais, comme notre services est sans état, nous ne pouvons pas
      le sauvegarder pendant la session. Nous enregistrerons la demande dans un cookie encodé en Base64.
    */
	@Bean
	public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
		return new HttpCookieOAuth2AuthorizationRequestRepository();
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**",
				"/swagger-ui.html", "/webjars/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				.cors()
				.and()
				.csrf().disable()
				.exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.csrf()
				.disable()
				.formLogin()
				.disable()
				.httpBasic()
				.disable()
				.exceptionHandling()
				.authenticationEntryPoint(new JwtAuthenticationEntryPoint())
				.and()
				.authorizeRequests()
				.antMatchers("/",
						"/error",
						"/favicon.ico",
						"/**/*.png",
						"/**/*.gif",
						"/**/*.svg",
						"/**/*.jpg",
						"/**/*.html",
						"/**/*.css",
						"/**/*.js")
				.permitAll()
				.antMatchers("/**/api/auth/**").permitAll()
				.antMatchers("/**/api/user/**").permitAll()
				.antMatchers("/**/api/sms/**").permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.oauth2Login()
				.authorizationEndpoint()
				.baseUri("/oauth2/authorize")
				.authorizationRequestRepository(cookieAuthorizationRequestRepository())
				.and()
				.redirectionEndpoint()
				.baseUri("/oauth2/callback/*")
				.and()
				.userInfoEndpoint()
				.userService(customOAuth2UserService)
				.and()
				.successHandler(oAuth2AuthenticationSuccessHandler)
				.failureHandler(oAuth2AuthenticationFailureHandler);

		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}

