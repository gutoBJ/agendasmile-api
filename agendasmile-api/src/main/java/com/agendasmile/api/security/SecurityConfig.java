package com.agendasmile.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http

                // Habilita CORS
                .cors(Customizer.withDefaults())

                // Desativa CSRF
                .csrf(AbstractHttpConfigurer::disable)

                // API Stateless
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Regras de autorização
                .authorizeHttpRequests(auth -> auth

                        // Login público
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()

                        // Libera requisições OPTIONS (preflight do CORS)
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Usuários
                        .requestMatchers(HttpMethod.GET, "/usuarios", "/usuarios/**").permitAll()
                        .requestMatchers("/usuarios/**").hasRole("ADMIN")

                        // Especialidades
                        .requestMatchers("/especialidades/**").hasRole("ADMIN")

                        // Pacientes
                        .requestMatchers("/pacientes/**")
                        .hasAnyRole("ADMIN", "DENTISTA")

                        // Dentistas
                        .requestMatchers("/dentistas/**")
                        .hasAnyRole("ADMIN", "DENTISTA")

                        // Consultas
                        .requestMatchers("/consultas/**")
                        .hasAnyRole("ADMIN", "DENTISTA")

                        // Qualquer outra rota exige autenticação
                        .anyRequest().authenticated()
                )

                // Filtro JWT
                .addFilterBefore(
                        jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}