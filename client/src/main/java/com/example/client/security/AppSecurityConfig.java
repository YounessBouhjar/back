package com.example.client.security;



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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.client.model.Client;
import com.example.client.service.ClientService;
import com.google.common.collect.ImmutableList;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	ClientService clientService;
	
	
	UserPrincipalDetailsService service;
	
	@Autowired
	public AppSecurityConfig(UserPrincipalDetailsService service) {

		this.service = service;
	}
	
	@PostConstruct
	public void init() {
		
        
		List<Client>  currentClientList= new ArrayList<Client>();
		currentClientList = clientService.findAllClients();

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
		
		http
			.cors()
			.and()
			.authorizeRequests()

			
			.antMatchers(HttpMethod.GET,"/client/findgsm/{gsm}").permitAll() 		//client par gsm
			.antMatchers(HttpMethod.GET,"/client/all").permitAll()		//afficher clients
			.antMatchers(HttpMethod.POST,"/client/add").permitAll()	//creer clients
			.antMatchers(HttpMethod.GET,"/client/findcin/{cin}").permitAll()	//client par cin
			.antMatchers(HttpMethod.GET,"/client/beneficiaire/all").permitAll()	//benef client
			.antMatchers(HttpMethod.PUT,"/client/updateClient/{clientId}").permitAll()	//benef client
			.antMatchers(HttpMethod.GET,"/client/findid/{id}").permitAll()	//benef client
			.antMatchers(HttpMethod.DELETE,"/client/delete/{id}").permitAll()	//benef client
			.antMatchers(HttpMethod.PUT,"/client/updateClient").permitAll()	//benef client


			.and()
			.httpBasic()
			.and()
			.csrf().disable()
			;
		http.cors().disable()
		;
		
		
		super.configure(http);
	}
	
	
	

}
