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

@Configuration
@EnableWebSecurity // Habilito la seguridad web y me permite realizar configuraciones de seguridad en la aplicación.
class WebAuthorization {

    @Bean // Creo una instancia de tipo SecurityFilterChain que Spring se encarga de administrar.
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Uso HttpSecurity para configurar la seguridad en relación con las solicitudes HTTP.
        http.authorizeRequests()
                // Defino rutas públicas a las que se puede acceder sin autenticación.
                .antMatchers(
                        HttpMethod.POST,
                        "/api/clients" // Permito el registro de clientes.
                ).permitAll()
                .antMatchers(
                        "/web/index.html",
                        "/web/pages/login.html",
                        "/web/register.html",
                        "/web/css/**",
                        "/web/js/**",
                        "/web/images/**"
                ).permitAll()
                // Defino rutas exclusivas para el administrador.
                .antMatchers(
                        "/h2-console/**",
                        "/rest/**",
                        "/web/pages/manager.html",
                        "/web/pages/admin-loan.html"
                ).hasAuthority("ADMIN")
                // Rutas de solo lectura para administradores.
                .antMatchers(HttpMethod.GET, "/api/clients", "/api/loans").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/loans/create").hasAuthority("ADMIN")
                // Rutas que requieren autenticación.
                .antMatchers(
                        "/web/pages/**",
                        "/api/clients/current/**"
                ).authenticated()
                // Restrinjo el acceso a /api/loans solo a usuarios autenticados.
                .antMatchers(HttpMethod.GET, "/api/loans").authenticated()
                .antMatchers(HttpMethod.POST, "/api/loans", "/api/loans/payments").authenticated()
                // Deniego todas las demás solicitudes no configuradas.
                .anyRequest().denyAll();

        // Configuro el inicio de sesión basado en formulario.
        http.formLogin()
                .usernameParameter("email") // Utilizo el email como nombre de usuario.
                .passwordParameter("password")
                .loginPage("/api/login"); // Establezco la ruta para el inicio de sesión.

        // Configuro la ruta para cerrar sesión.
        http.logout().logoutUrl("/api/logout");

        // Desactivo la protección CSRF ya que no utilizo formularios generados desde el servidor.
        http.csrf().disable();

        // Deshabilito las restricciones de frameOptions para acceder a la consola H2.
        http.headers().frameOptions().disable();

        // Si el usuario no está autenticado, envío una respuesta de error de autenticación.
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // Si el inicio de sesión es exitoso, limpio los atributos de autenticación.
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // Si el inicio de sesión falla, envío una respuesta de error de autenticación.
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // Si el cierre de sesión es exitoso, envío una respuesta de éxito.
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

        // Construyo y retorno la configuración de seguridad.
        return http.build();
    }

    // Elimino los atributos de autenticación fallida o exitosa de la sesión.
    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}
