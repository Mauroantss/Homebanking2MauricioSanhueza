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

// Estoy configurando una clase llamada WebAuthentication que extiende GlobalAuthenticationConfigurerAdapter.
@Configuration
public class WebAuthentication extends GlobalAuthenticationConfigurerAdapter {

    // Estoy inyectando la dependencia de ClientRepository en esta clase.
    @Autowired
    ClientRepository clientRepository;

    // Estoy anulando el método init de GlobalAuthenticationConfigurerAdapter para personalizar la configuración de autenticación.
    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {

        // Estoy configurando un userDetailsService que buscará clientes por su dirección de correo electrónico.
        auth.userDetailsService(inputEmail -> {

            // Estoy recuperando un objeto Client de la base de datos usando el ClientRepository.
            Client client = clientRepository.findByEmail(inputEmail);

            // Estoy comprobando si el cliente existe.
            if (client != null) {

                // Estoy verificando si el correo electrónico del cliente contiene "homebanking".
                if (client.getEmail().contains("homebanking")) {

                    // Estoy devolviendo un objeto User con roles ADMIN si el correo electrónico contiene "homebanking".
                    return new User(client.getEmail(), client.getPassword(),
                            AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));

                } else {

                    // Estoy devolviendo un objeto User con el rol CLIENT si el correo electrónico no contiene "homebanking".
                    return new User(client.getEmail(), client.getPassword(),
                            AuthorityUtils.createAuthorityList("CLIENT"));
                }
            } else {

                // Estoy lanzando una excepción si no se encuentra el cliente.
                throw new UsernameNotFoundException("Unknown client: " + inputEmail);
            }
        });
    }

    // Estoy configurando un bean para proporcionar un codificador de contraseñas.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}


