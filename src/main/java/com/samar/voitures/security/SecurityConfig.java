package com.samar.voitures.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	 
	 @Override
	 protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	 PasswordEncoder passwordEncoder = passwordEncoder ();
	 auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder.encode("123")).roles("ADMIN");
	  auth.inMemoryAuthentication().withUser("samar").password(passwordEncoder.encode("123")).roles("AGENT","USER");
	  auth.inMemoryAuthentication().withUser("user1").password(passwordEncoder.encode("123")).roles("USER");
	  }

	 protected void configure(HttpSecurity http) throws Exception {
		 http.authorizeRequests().antMatchers(
				 "/showCreateVoiture",
				 "showCreateMarque",
				 "/saveVoiture",
				 "/saveMarque"
				 
				 ).hasAnyRole("ADMIN","AGENT");
		 
		 
		 http.authorizeRequests().antMatchers(
				 "/ListeVoitures",
				 "/ListeMarques"
				 ).hasAnyRole("ADMIN","AGENT","USER");

		 http.authorizeRequests().antMatchers(
				 "/supprimerVoiture",
				 "/modifierVoiture",
				 "/updateVoiture",
				 
				 "/supprimerMarque",
				 "/modifierMarque",
				 "/updateMarque"
				 
				 )
		 .hasAnyRole("ADMIN");

		 http.authorizeRequests().anyRequest().authenticated();
		 http.formLogin();
		 http.exceptionHandling().accessDeniedPage("/accessDenied");

		 }
	 
	 @Bean
	 public PasswordEncoder passwordEncoder () {
	 return new BCryptPasswordEncoder();
	 }

//
//	 @Override
//	 protected void configure(HttpSecurity http) throws Exception {
//	 http.authorizeRequests().anyRequest().authenticated();
//	http.formLogin();
//	 } 

}
