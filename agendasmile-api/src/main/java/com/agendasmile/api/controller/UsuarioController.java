package com.agendasmile.api.controller;

import com.agendasmile.api.dto.usuario.UsuarioRequestDTO;
import com.agendasmile.api.dto.usuario.UsuarioResponseDTO;
import com.agendasmile.api.enums.PerfilUsuario;
import com.agendasmile.api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // indica que é um Controller REST
// retorna JSON automaticamente
@RequestMapping("/usuarios") // define a rota base do controller
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Regra 5 — só ADMIN (protegido depois com JWT)
    @PostMapping("/admin")
    public ResponseEntity<UsuarioResponseDTO> registrarAdmin(
            @RequestBody @Valid UsuarioRequestDTO dto) {
        // ResponseEntity -> controla o status HTTP da resposta
        UsuarioResponseDTO response = usuarioService.registrar(dto, PerfilUsuario.ADMIN);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Regra 5 — só ADMIN (protegido depois com JWT)
    @PostMapping("/dentista")
    public ResponseEntity<UsuarioResponseDTO> registrarDentista(
            @RequestBody @Valid UsuarioRequestDTO dto) {
        UsuarioResponseDTO response = usuarioService.registrar(dto, PerfilUsuario.DENTISTA);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Regra 5 — só ADMIN (protegido depois com JWT)
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    // Regra 5 — só ADMIN (protegido depois com JWT)
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    // Regra 5 — só ADMIN (protegido depois com JWT)
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(
            @PathVariable Long id, // lê o valor da URL (/usuarios/{id})
            @RequestBody @Valid UsuarioRequestDTO dto) {
        // @RequestBody -> lê o corpo da requisição (JSON)
        // @Valid -> ativa as validações do DTO
        return ResponseEntity.ok(usuarioService.atualizar(id, dto));
    }

    // Regra 5 — só ADMIN (protegido depois com JWT)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        usuarioService.desativar(id);
        return ResponseEntity.noContent().build();
    }
}