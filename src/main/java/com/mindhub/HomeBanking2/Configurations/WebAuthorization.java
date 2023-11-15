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
        http.authorizeRequests()
                // Rutas Públicas (Acceso sin autenticación)
                .antMatchers(
                        HttpMethod.POST,
                        "/api/clients" // Registro de clientes
                ).permitAll()
                .antMatchers(
                        "/web/index.html",
                        "/web/pages/login.html",
                        "/web/pages/register.html",
                        "/web/css/**",
                        "/web/js/**",
                        "/web/images/**"
                ).permitAll()
                // Rutas de Administrador
                .antMatchers(
                        "/h2-console/**",
                        "/rest/**",
                        "/web/pages/manager.html",
                        "/web/pages/admin-loan.html"
                ).hasAuthority("ADMIN")
                // Rutas de Solo Lectura para Administradores(Obtener listado de clientes)
                .antMatchers(HttpMethod.GET, "/api/clients", "/api/loans").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/loans/create").hasAuthority("ADMIN")
                // Rutas Autenticadas (Requieren autenticación)
                .antMatchers(
                        "/web/pages/**",
                        "/api/clients/current/**"
                ).authenticated()
                // Restringir el acceso a /api/loans solo a CLIENT (si es necesario)
                .antMatchers(HttpMethod.GET, "/api/loans").authenticated()
                .antMatchers(HttpMethod.POST, "/api/loans", "/api/loans/payments").authenticated()

                // Ruta Denegada si no coincide con las rutas previamente definidas (Sin acceso)
                .anyRequest().denyAll();
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