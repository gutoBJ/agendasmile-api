package com.agendasmile.api.controller;

import com.agendasmile.api.dto.dentista.DentistaRequestDTO;
import com.agendasmile.api.dto.dentista.DentistaResponseDTO;
import com.agendasmile.api.service.DentistaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dentistas")
public class DentistaController {

    private final DentistaService dentistaService;

    public DentistaController(DentistaService dentistaService) {
        this.dentistaService = dentistaService;
    }

    @PostMapping
    public ResponseEntity<DentistaResponseDTO> cadastrar(
            @RequestBody @Valid DentistaRequestDTO dto) {
        DentistaResponseDTO response = dentistaService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<DentistaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(dentistaService.listarTodos());
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<DentistaResponseDTO>> listarAtivos() {
        return ResponseEntity.ok(dentistaService.listarAtivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DentistaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(dentistaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DentistaResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid DentistaRequestDTO dto) {
        return ResponseEntity.ok(dentistaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        dentistaService.desativar(id);
        return ResponseEntity.noContent().build();
    }
}
