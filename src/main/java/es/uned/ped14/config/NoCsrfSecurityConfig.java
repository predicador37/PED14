package es.uned.ped14.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
/**
 * 
 * Clase que desactiva la protección contra cross scripting (CSRF) de spring security. 
 *
 */
@Configuration
public class NoCsrfSecurityConfig extends SecurityConfig {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.csrf().disable();
    }

}
