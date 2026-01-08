package br.com.everson.gestaopedidos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // <--- ADICIONE ESTA ANOTAÇÃO
public class SecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    private final SecurityFilter securityFilter;

    public SecurityConfig(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(request -> {
                    var config = new org.springframework.web.cors.CorsConfiguration();
                    config.setAllowedOrigins(java.util.List.of("http://localhost:5173", "http://127.0.0.1:5173")); // Porta padrão do Vite/Vue
                    config.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(java.util.List.of("*"));
                    config.setAllowCredentials(true);
                    return config;
                }))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {
                    // 1. Rotas Públicas
                    req.requestMatchers(HttpMethod.POST, "/login").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/usuarios").permitAll();

                    // Libera o Swagger para todos:
                    req.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll();

                    // 2. Restrições por Perfil (Hierarquia)
                    // Somente ADMIN pode deletar produtos
                    req.requestMatchers(HttpMethod.DELETE, "/produtos/**").hasRole("ADMIN");

                    // Somente ADMIN pode criar novos produtos (exemplo)
                    req.requestMatchers(HttpMethod.POST, "/produtos").hasRole("ADMIN");

                    // 3. Qualquer outra rota exige apenas que esteja autenticado
                    req.anyRequest().authenticated();
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

/*    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Verifique se o login está EXATAMENTE assim e ANTES do anyRequest
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                        .anyRequest().authenticated()
                )
                // Esta ordem é crucial: primeiro o nosso filtro, depois o do Spring
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }*/
/*    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Desabilitamos para facilitar os testes via Postman/cURL
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // IMPORTANTE: Agora é Stateless
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                        .anyRequest().authenticated()
                )
                // Remova o httpBasic(withDefaults()) se quiser parar de usar Basic Auth
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class); // Faremos o filtro no próximo passo

        return http.build();
    }*/
   /* public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desabilitamos para facilitar os testes via Postman/cURL
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/usuarios").permitAll()
                        .anyRequest().authenticated() // Qualquer requisição exige login
                )
                .httpBasic(withDefaults()); // Ativa a autenticação básica (User/Password)

        return http.build();
    }*/

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