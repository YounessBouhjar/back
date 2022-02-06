package com.example.agent.security;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.agent.exception.AgentNotFoundException;
import com.example.agent.model.Agent;
import com.example.agent.service.AgentService;
import com.google.common.collect.ImmutableList;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	AgentService agentService;
	
	
	UserPrincipalDetailsService service;
	
	@Autowired
	public AppSecurityConfig(UserPrincipalDetailsService service) {

		this.service = service;
	}
	@PostConstruct
	public void init() {
		
        
		List<Agent>  currentAgentList= new ArrayList<Agent>();
		try {
			currentAgentList = agentService.getAllAgent();
		} catch (AgentNotFoundException e) {
		
	     e.printStackTrace();}   
		}
	     
	        


	@Bean
	public DaoAuthenticationProvider autProvider()
	{
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(service);
		provider.setPasswordEncoder(new BCryptPasswordEncoder());
		
		return provider;
	}
	
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(ImmutableList.of("*"));
		
		configuration.setAllowedMethods(ImmutableList.of("HEAD",
				"GET", "POST", "PUT", "DELETE", "PATCH"));
		configuration.setAllowedHeaders(ImmutableList.of("accept",
				"accept-encoding",
				"authorization",
				"content-type",
				"dnt",
				"origin",
				"user-agent",
				"x-csrftoken",
				"x-requested-with"));
		// setAllowCredentials(true) is important, otherwise:
		// The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
		configuration.setAllowCredentials(true);
		// setAllowedHeaders is important! Without it, OPTIONS preflight request
		// will fail with 403 Invalid CORS request
		configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().defaultSuccessUrl("/agent/", true);

		http
			.cors()
			.and()
			.authorizeRequests()

			.antMatchers("/agent").permitAll() 		//agent par username

			.antMatchers(HttpMethod.GET,"/agent/agent/{email}").permitAll() 		//agent par username
			.antMatchers(HttpMethod.GET,"/agent/all").permitAll()	//afficher agent
			.antMatchers(HttpMethod.GET,"/agent/agentid/{id}").permitAll()	//afficher agent

			.antMatchers(HttpMethod.POST,"/agent/add").permitAll()	//creer agent
			.antMatchers(HttpMethod.PUT,"/agent/update").permitAll()	//modifier agent
			.antMatchers(HttpMethod.DELETE,"/agent/delete/{id}").permitAll()	//supprimer agent

			.antMatchers(HttpMethod.GET,"/agent/compte/all").permitAll() 		
			.antMatchers(HttpMethod.GET,"/agent/compte/find/{nomClient}").permitAll()		
			.antMatchers(HttpMethod.POST,"/agent/compte/find/{numCompte}").permitAll()	
			.antMatchers(HttpMethod.PUT,"/agent/compte/add").permitAll()	
			.antMatchers(HttpMethod.DELETE,"/agent/compte/update/{id}/{solde}").permitAll()	
			.and()
			.httpBasic()
			.and()
			.csrf().disable()
			;
			
		
		
		super.configure(http);
	}
	
	
	

}
