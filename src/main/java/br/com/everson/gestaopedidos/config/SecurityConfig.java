package br.com.everson.gestaopedidos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desabilitamos para facilitar os testes via Postman/cURL
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/usuarios").permitAll()
                        .anyRequest().authenticated() // Qualquer requisição exige login
                )
                .httpBasic(withDefaults()); // Ativa a autenticação básica (User/Password)

        return http.build();
    }

    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Liberamos o POST de usuários para que novos cadastros sejam possíveis
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/usuarios").permitAll()
                        // Todo o resto continua trancado
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults());

        return http.build();
    }
    */

    @Bean
    public PasswordEncoder passwordEncoder() {

        // Isso diz ao Spring: "Não use criptografia nenhuma, compare as Strings puras"
        //return org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();

        return new BCryptPasswordEncoder(); // Define que usaremos BCrypt para as senhas
    }
}