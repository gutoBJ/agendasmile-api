package com.agendasmile.api.service;

import com.agendasmile.api.dto.usuario.UsuarioRequestDTO;
import com.agendasmile.api.dto.usuario.UsuarioResponseDTO;
import com.agendasmile.api.entity.Dentista;
import com.agendasmile.api.entity.Usuario;
import com.agendasmile.api.enums.PerfilUsuario;
import com.agendasmile.api.repository.DentistaRepository;
import com.agendasmile.api.repository.UsuarioRepository;
import com.agendasmile.api.utils.ValidacaoUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final DentistaRepository dentistaRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // Injeção via construtor — boa prática!
    public UsuarioService(UsuarioRepository usuarioRepository,
                          BCryptPasswordEncoder passwordEncoder,
                          DentistaRepository dentistaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.dentistaRepository = dentistaRepository;
    }

    // Regra 5 — só ADMIN chama esse metodo (controlado no Controller)
    public UsuarioResponseDTO registrar(UsuarioRequestDTO dto, PerfilUsuario perfil) {
        ValidacaoUtil.validarNome(dto.getNome());
        validarSenha(dto.getSenha());

        // Verifica duplicidade
        if (usuarioRepository.existsByEmail(dto.getEmail()))
            throw new IllegalArgumentException("E-mail já cadastrado!");

        if (usuarioRepository.existsByCpf(dto.getCpf()))
            throw new IllegalArgumentException("CPF já cadastrado!");

        // Valida força da senha
        validarSenha(dto.getSenha());

        // Monta a entidade
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setCpf(dto.getCpf());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha())); // ← BCrypt!
        usuario.setPerfil(perfil); // ← definido pela rota, não pelo cliente!

        usuarioRepository.save(usuario);

        return toResponse(usuario);
    }

    // Regra 5 — só ADMIN chama esse metodo (controlado no Controller)
    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado!"));

        return toResponse(usuario);
    }

    // Regra 5 — só ADMIN chama esse metodo (controlado no Controller)
    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // Regra 5 — só ADMIN chama esse metodo (controlado no Controller)
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {
        ValidacaoUtil.validarNome(dto.getNome());

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado!"));

        // Verifica duplicidade ignorando o próprio usuário
        if (usuarioRepository.existsByEmailAndIdNot(dto.getEmail(), id))
            throw new IllegalArgumentException("E-mail já cadastrado!");

        if (usuarioRepository.existsByCpfAndIdNot(dto.getCpf(), id))
            throw new IllegalArgumentException("CPF já cadastrado!");

        usuario.setNome(dto.getNome());
        usuario.setCpf(dto.getCpf());
        usuario.setEmail(dto.getEmail());

        // Só atualiza a senha se vier preenchida
        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            validarSenha(dto.getSenha());
            usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        usuarioRepository.save(usuario);
        return toResponse(usuario);
    }

    // Regra 5 — só ADMIN chama esse metodo (controlado no Controller)
    public void desativar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado!"));

        if (!usuario.isAtivo())
            throw new IllegalArgumentException("Usuário já está desativado!");

        // Se o usuário for dentista, desativa o dentista também!
        if (usuario.getPerfil() == PerfilUsuario.DENTISTA) {
            Dentista dentista = dentistaRepository.findByUsuarioId(id)
                    .orElseThrow(() -> new IllegalArgumentException("Dentista não encontrado!"));
            dentista.setAtivo(false);
            dentistaRepository.save(dentista);
        }

        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }


    // ─── Métodos privados ─────────────────────────────────────────

    private void validarSenha(String senha) {
        if (!senha.matches(".*[A-Z].*"))
            throw new IllegalArgumentException("Senha deve ter pelo menos uma letra maiúscula!");
        if (!senha.matches(".*[0-9].*"))
            throw new IllegalArgumentException("Senha deve ter pelo menos um número!");
    }


    private UsuarioResponseDTO toResponse(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getEmail(),
                usuario.getDataCriacao(),
                usuario.getUltimoLogin(),
                usuario.getPerfil(),
                usuario.isAtivo()
        );
    }
}
