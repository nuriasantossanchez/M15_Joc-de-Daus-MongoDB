package jocDeDausMongoDB.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_URL = "/login";

    @Value("${spring.security.userModel.name}")
    private String name;

    @Value("${spring.security.userModel.password}")
    private String secretKey;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
            .antMatchers(LOGIN_URL).permitAll() //permitimos el acceso a /login a cualquiera
            .anyRequest().authenticated() //cualquier otra peticion requiere autenticacion
            .and()
            // Las peticiones /login pasaran previamente por este filtro
            .addFilterBefore(new LoginFilter(LOGIN_URL, authenticationManager()),
                    UsernamePasswordAuthenticationFilter.class)

            // Las demás peticiones pasarán por este filtro para validar el token
            .addFilterBefore(new JwtFilter(),
                    UsernamePasswordAuthenticationFilter.class);
    }

   @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Creamos una cuenta de usuario por default
       auth.inMemoryAuthentication()
               .withUser("root")
               .password("{noop}r@@t")
               .roles("ADMIN");
       
       auth.inMemoryAuthentication()
			   .withUser(name)
			   .password("{noop}"+secretKey)
			   .roles("USER");
    }
}