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
                .antMatchers(HttpMethod.POST,"/api/clients","/api/login/","/api/clients/current/**").permitAll()
                .antMatchers("/web/index.html","/web/js/**", "/web/pages/login.html", "/web/pages/register.html"
                        ,"/web/css/**","/web/images/**","/api/clients/current/**","/api/clients","/api/clients/current/transaction","/api/clients/current/cards").permitAll()

                .antMatchers(HttpMethod.POST,"/api/clients/current/cards","/web/pages/cards.html","/api/clients/current/transaction","/api/clients/current/**").authenticated()

                .antMatchers("/h2-console/**", "/rest/", "/web/pages/manager.html").hasAuthority("ADMIN")
                .antMatchers("/api/clients/current/cards").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST,"/api/logout/","/api/logout/",
                        "/web/pages/**",
                        "/api/clients/current/accounts",
                        "/api/clients/current/accounts/transaction").authenticated()
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