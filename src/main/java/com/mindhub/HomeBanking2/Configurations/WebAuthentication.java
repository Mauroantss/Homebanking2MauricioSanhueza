package com.mindhub.HomeBanking2.Configurations;

import com.mindhub.HomeBanking2.models.Client;
import com.mindhub.HomeBanking2.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // Indica que esta será la configuracion que tiene que usar la app antes de iniciar.
public class WebAuthentication extends GlobalAuthenticationConfigurerAdapter {
    @Autowired // Permite la inyección de ClientRepository en esta clase.
    ClientRepository clientRepository;

    @Override // Indica que este método sobrescribe el método de una clase padre.

    public void init(AuthenticationManagerBuilder auth) throws Exception {
        // Configuración del servicio de detalles de usuario para autenticación.

        auth.userDetailsService(inputName -> {
            // Busca un cliente por email y configura los roles según el tipo de usuario.
            Client client = clientRepository.findByEmail(inputName);
            if (client != null) {
                if (client.getAdmin()) {
                    // Si es administrador, asigna el rol ADMIN.
                    return new User(client.getEmail(), client.getPassword(),
                            AuthorityUtils.createAuthorityList("ADMIN"));
                } else {
                    // Si no es administrador, asigna el rol CLIENT.
                    return new User(client.getEmail(), client.getPassword(),
                            AuthorityUtils.createAuthorityList("CLIENT"));
                }
            } else {
                // Si el usuario no se encuentra, lanza una excepción.
                throw new UsernameNotFoundException("Unknown user: " + inputName);
            }
        });
    }

    @Bean // Declara que un método produce un bean que será gestionado por el contexto de Spring.
    public PasswordEncoder passwordEncoder() {
        // Provee un codificador de contraseña para la seguridad de la aplicación.
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}

