package es.uned.ped14.config;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import es.uned.ped14.account.UserService;

/**
 * 
 * Configuración de spring security donde se definen las autorizaciones por URL
 * y rol a la aplicación.
 *
 */
@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserService userService() {
		return new UserService();
	}

	@Bean
	public TokenBasedRememberMeServices rememberMeServices() {
		return new TokenBasedRememberMeServices("remember-me-key",
				userService());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new StandardPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.eraseCredentials(true).userDetailsService(userService())
				.passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http

		.authorizeRequests()
				.antMatchers("/", "/favicon.ico", "/resources/**", "/signup",
						"/curriculum/results", "/grupo", "/curriculum/show/**",
						"/titulacion/like", "/conocimiento/like", "/curso/like")
				.permitAll().antMatchers("/titulacion/create/**")
				.hasRole("CREATE").antMatchers("/titulacion/edit/**")
				.hasRole("EDIT").antMatchers("/titulacion/delete/**")
				.hasRole("DELETE").antMatchers("/curso/create/**")
				.hasRole("CREATE").antMatchers("/curso/edit/**")
				.hasRole("EDIT").antMatchers("/curso/delete/**")
				.hasRole("DELETE").antMatchers("/experiencia/create/**")
				.hasRole("CREATE").antMatchers("/experiencia/edit/**")
				.hasRole("EDIT").antMatchers("/experiencia/delete/**")
				.hasRole("DELETE").antMatchers("/conocimiento/create/**")
				.hasRole("CREATE").antMatchers("/conocimiento/edit/**")
				.hasRole("EDIT").antMatchers("/conocimiento/delete/**")
				.hasRole("DELETE").antMatchers("/curriculum/edit/**")
				.hasRole("EDIT").antMatchers("/curriculum/delete/**")
				.hasRole("DELETE")

				.anyRequest().authenticated().and().formLogin()
				.loginPage("/signin").permitAll().failureUrl("/signin?error=1")
				.loginProcessingUrl("/authenticate").defaultSuccessUrl("/")
				.and().logout().logoutUrl("/logout").permitAll()
				.logoutSuccessUrl("/signin?logout").and().rememberMe()
				.rememberMeServices(rememberMeServices())
				.key("remember-me-key");
		// .and()
		// .csrf().disable();
	}
}