package com.agendasmile.api.service;

import com.agendasmile.api.dto.auth.LoginRequestDTO;
import com.agendasmile.api.dto.auth.LoginResponseDTO;
import com.agendasmile.api.entity.Usuario;
import com.agendasmile.api.exception.RecursoNaoEncontradoException;
import com.agendasmile.api.repository.UsuarioRepository;
import com.agendasmile.api.security.JwtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UsuarioRepository usuarioRepository,
            BCryptPasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {

        // Busca o usuário pelo email
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado!"));

        // Verifica se o usuário está ativo
        if (!usuario.isAtivo())
            throw new IllegalArgumentException("Usuário desativado!");

        // Verifica a senha com BCrypt
        if (!passwordEncoder.matches(dto.getSenha(), usuario.getSenha()))
            throw new IllegalArgumentException("E-mail ou senha inválidos!");

        // Atualiza o último login
        usuario.setUltimoLogin(LocalDateTime.now());
        usuarioRepository.save(usuario);

        // Gera o token JWT
        String token = jwtService.gerarToken(usuario);

        return new LoginResponseDTO(
                token,
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPerfil()
        );
    }
}