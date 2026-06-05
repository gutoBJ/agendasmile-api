package com.agendasmile.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                // Desativa CSRF — API REST não precisa!
                .csrf(AbstractHttpConfigurer::disable)

                // Sem sessão — API REST é stateless!
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Regras de acesso
                .authorizeHttpRequests(auth -> auth

                        // ✅ Login — público!
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()

                        .requestMatchers(HttpMethod.GET, "/usuarios", "/usuarios/**").permitAll()

                        // ✅ Regra 5 — só ADMIN gerencia usuários
                        .requestMatchers("/usuarios/**").hasRole("ADMIN")

                        // ✅ Regra 5 — só ADMIN gerencia especialidades
                        .requestMatchers("/especialidades/**").hasRole("ADMIN")

                        // ✅ Pacientes — ADMIN e DENTISTA
                        .requestMatchers("/pacientes/**").hasAnyRole("ADMIN", "DENTISTA")

                        // ✅ Dentistas — ADMIN e DENTISTA
                        .requestMatchers("/dentistas/**").hasAnyRole("ADMIN", "DENTISTA")

                        // ✅ Consultas — ADMIN e DENTISTA
                        .requestMatchers("/consultas/**").hasAnyRole("ADMIN", "DENTISTA")

                        // Qualquer outra rota → autenticado
                        .anyRequest().authenticated()
                )

                // Adiciona o filtro JWT antes do filtro padrão
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
