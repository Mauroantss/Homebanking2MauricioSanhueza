package com.mindhub.HomeBanking2.Configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAuthorization {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests() // Autoriza peticiones

                // Permisos sin autenticación
                .antMatchers(HttpMethod.POST,"/api/clients","/api/login/").permitAll()
                .antMatchers("/web/index.html", "/web/js/**", "/web/pages/login.html",
                        "/web/pages/register.html","/api/loans" ,"/web/css/**", "/web/images/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/loans").permitAll() // permiso público para obtener préstamos

                // Permisos para usuarios autenticados
                .antMatchers(HttpMethod.POST,"/api/clients/current/**", "/api/loans",
                        "/api/clients/current/transaction", "/api/clients/current/cards").authenticated()

                // Permisos específicos para ADMIN
                .antMatchers("/h2-console/**", "/api/clients", "/web/pages/manager.html").hasAuthority("ADMIN")

                // Permisos específicos para CLIENT
                .antMatchers("/api/clients/current/cards", "/api/client/current/loans",
                        "/api/loans").hasAuthority("CLIENT")

                // Requiere autenticación para cualquier otra petición no especificada
                .anyRequest().authenticated();
//hasAuthority(client)

        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout");


        // turn off checking for CSRF tokens

        http.csrf().disable();

        //disabling frameOptions so h2-console can be accessed

        http.headers().frameOptions().disable();

        // if user is not authenticated, just send an authentication failure response

        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication

        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response

        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response

        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
        return http.build();

    }


    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }
    }
}