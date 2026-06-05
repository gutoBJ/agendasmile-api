package com.agendasmile.api.controller;

import com.agendasmile.api.dto.consulta.ConsultaCancelamentoDTO;
import com.agendasmile.api.dto.consulta.ConsultaRequestDTO;
import com.agendasmile.api.dto.consulta.ConsultaResponseDTO;
import com.agendasmile.api.entity.Usuario;
import com.agendasmile.api.service.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @PostMapping
    public ResponseEntity<ConsultaResponseDTO> agendar(
            @RequestBody @Valid ConsultaRequestDTO dto) {

        // Pega o usuário autenticado do SecurityContext
        Usuario usuario = (Usuario) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        ConsultaResponseDTO response = consultaService.agendar(dto, usuario.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Regra 7 — só ADMIN (protegido depois com JWT)
    @GetMapping
    public ResponseEntity<List<ConsultaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(consultaService.listarTodas());
    }

    // Regra 6 — dentista vê só as suas (protegido depois com JWT)
    @GetMapping("/dentista/{id}")
    public ResponseEntity<List<ConsultaResponseDTO>> listarPorDentista(
            @PathVariable Long id) {
        return ResponseEntity.ok(consultaService.listarPorDentista(id));
    }

    @GetMapping("/paciente/{id}")
    public ResponseEntity<List<ConsultaResponseDTO>> listarPorPaciente(
            @PathVariable Long id) {
        return ResponseEntity.ok(consultaService.listarPorPaciente(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(consultaService.buscarPorId(id));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<ConsultaResponseDTO> cancelar(
            @PathVariable Long id,
            @RequestBody @Valid ConsultaCancelamentoDTO dto) {
        dto.setIdConsulta(id);
        return ResponseEntity.ok(consultaService.cancelar(dto));
    }

    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<ConsultaResponseDTO> finalizar(@PathVariable Long id) {
        return ResponseEntity.ok(consultaService.finalizar(id));
    }
}
