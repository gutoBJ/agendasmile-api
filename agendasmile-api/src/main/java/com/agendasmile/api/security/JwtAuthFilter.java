package com.agendasmile.api.security;

import com.agendasmile.api.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    public JwtAuthFilter(JwtService jwtService, UsuarioRepository usuarioRepository) {
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Pega o token do header
        String token = extrairToken(request);

        // Se tiver token → valida e autentica
        if (token != null) {
            String email = jwtService.validarToken(token);
            String perfil = jwtService.extrairPerfil(token);

            // Busca o usuário no banco
            usuarioRepository.findByEmail(email).ifPresent(usuario -> {

                // Cria a autenticação com o perfil
                var autenticacao = new UsernamePasswordAuthenticationToken(
                        usuario,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + perfil))
                );

                // Registra no contexto do Spring Security
                SecurityContextHolder.getContext().setAuthentication(autenticacao);
            });
        }

        // Continua a requisição
        filterChain.doFilter(request, response);
    }

    private String extrairToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        // Token vem no formato: "Bearer eyJhbGci..."
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // remove "Bearer "
        }

        return null;
    }
}
