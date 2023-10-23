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

@Configuration
// Le indica a Spring que debe crear un objeto de este tipo cuando se esta iniciando la aplicacion,
// para que cuando se configure el módulo de spring utilice ese objeto ya creado.
public class WebAuthentication extends GlobalAuthenticationConfigurerAdapter {
    @Autowired
    ClientRepository clientRepository;


    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(inputName -> {

            Client client = clientRepository.findByEmail(inputName);

            if (client != null) {
                if (client.getAdmin()) {
                    return new User(client.getEmail(), client.getPassword(),
                            AuthorityUtils.createAuthorityList("ADMIN"));
                } else {
                    return new User(client.getEmail(), client.getPassword(),
                            AuthorityUtils.createAuthorityList("CLIENT"));
                }
            } else {
                throw new UsernameNotFoundException("Unknown user: " + inputName);
            }


        });

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
