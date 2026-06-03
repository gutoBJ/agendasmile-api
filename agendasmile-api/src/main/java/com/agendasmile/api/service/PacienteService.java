package com.agendasmile.api.service;

import com.agendasmile.api.dto.paciente.PacienteRequestDTO;
import com.agendasmile.api.dto.paciente.PacienteResponseDTO;
import com.agendasmile.api.entity.Paciente;
import com.agendasmile.api.repository.PacienteRepository;
import com.agendasmile.api.utils.ValidacaoUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public PacienteResponseDTO cadastrar(PacienteRequestDTO dto) {
        ValidacaoUtil.validarNome(dto.getNome());
        ValidacaoUtil.validarTelefone(dto.getTelefone());

        // Verifica duplicidade
        if (pacienteRepository.existsByEmail(dto.getEmail()))
            throw new IllegalArgumentException("E-mail já cadastrado!");

        if (pacienteRepository.existsByCpf(dto.getCpf()))
            throw new IllegalArgumentException("CPF já cadastrado!");

        Paciente paciente = new Paciente();
        paciente.setNome(dto.getNome());
        paciente.setCpf(dto.getCpf());
        paciente.setEmail(dto.getEmail());
        paciente.setTelefone(dto.getTelefone());

        pacienteRepository.save(paciente);

        return toResponse(paciente);
    }

    public PacienteResponseDTO buscarPorId(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado!"));

        return toResponse(paciente);
    }

    public List<PacienteResponseDTO> listarAtivos() {
        return pacienteRepository.findAllByAtivoTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<PacienteResponseDTO> listarTodos() {
        return pacienteRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public PacienteResponseDTO atualizar(Long id, PacienteRequestDTO dto) {
        ValidacaoUtil.validarNome(dto.getNome());
        ValidacaoUtil.validarTelefone(dto.getTelefone());

        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado!"));

        // Verifica duplicidade ignorando o próprio paciente
        if (pacienteRepository.existsByEmailAndIdNot(dto.getEmail(), id))
            throw new IllegalArgumentException("E-mail já cadastrado!");

        if (pacienteRepository.existsByCpfAndIdNot(dto.getCpf(), id))
            throw new IllegalArgumentException("CPF já cadastrado!");

        paciente.setNome(dto.getNome());
        paciente.setCpf(dto.getCpf());
        paciente.setEmail(dto.getEmail());
        paciente.setTelefone(dto.getTelefone());

        pacienteRepository.save(paciente);

        return toResponse(paciente);
    }

    public void desativar(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado!"));

        if (!paciente.isAtivo())
            throw new IllegalArgumentException("Paciente já está desativado!");

        paciente.setAtivo(false);
        pacienteRepository.save(paciente);
    }

    // ─── Métodos privados ─────────────────────────────────────────

    private PacienteResponseDTO toResponse(Paciente paciente) {
        return new PacienteResponseDTO(
                paciente.getId(),
                paciente.getNome(),
                paciente.getCpf(),
                paciente.getEmail(),
                paciente.getDataCriacao(),
                paciente.getTelefone(),
                paciente.isAtivo()
        );
    }
}