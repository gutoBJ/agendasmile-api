package com.agendasmile.api.service;

import com.agendasmile.api.dto.consulta.ConsultaCancelamentoDTO;
import com.agendasmile.api.dto.consulta.ConsultaRequestDTO;
import com.agendasmile.api.dto.consulta.ConsultaResponseDTO;
import com.agendasmile.api.entity.Consulta;
import com.agendasmile.api.entity.Dentista;
import com.agendasmile.api.entity.Paciente;
import com.agendasmile.api.entity.Usuario;
import com.agendasmile.api.enums.StatusConsulta;
import com.agendasmile.api.repository.ConsultaRepository;
import com.agendasmile.api.repository.DentistaRepository;
import com.agendasmile.api.repository.PacienteRepository;
import com.agendasmile.api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final DentistaRepository dentistaRepository;
    private final PacienteRepository pacienteRepository;
    private final UsuarioRepository usuarioRepository;

    public ConsultaService(
            ConsultaRepository consultaRepository,
            DentistaRepository dentistaRepository,
            PacienteRepository pacienteRepository,
            UsuarioRepository usuarioRepository) {
        this.consultaRepository = consultaRepository;
        this.dentistaRepository = dentistaRepository;
        this.pacienteRepository = pacienteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public ConsultaResponseDTO agendar(ConsultaRequestDTO dto, Long idUsuario) {

        // Busca as entidades
        Dentista dentista = dentistaRepository.findById(dto.getIdDentista())
                .orElseThrow(() -> new IllegalArgumentException("Dentista não encontrado!"));

        Paciente paciente = pacienteRepository.findById(dto.getIdPaciente())
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado!"));

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado!"));

        // Regra 2 — não permite agendamento em datas passadas
        if (dto.getDataInicio().isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("Não é possível agendar em datas passadas!");

        // Regra 8 — horário fim deve ser após horário início
        if (!dto.getDataFim().isAfter(dto.getDataInicio()))
            throw new IllegalArgumentException("Horário de fim deve ser após o horário de início!");

        // Regra 1 — não permite conflito de horário para o mesmo dentista
        if (consultaRepository.existsByDentistaIdAndDataInicioLessThanAndDataFimGreaterThan(
                dto.getIdDentista(),
                dto.getDataFim(),
                dto.getDataInicio()))
            throw new IllegalArgumentException("Dentista já possui consulta nesse horário!");

        // Monta a consulta
        Consulta consulta = new Consulta();
        consulta.setDentista(dentista);
        consulta.setPaciente(paciente);
        consulta.setUsuario(usuario);
        consulta.setDescricao(dto.getDescricao());
        consulta.setDataInicio(dto.getDataInicio());
        consulta.setDataFim(dto.getDataFim());

        consultaRepository.save(consulta);

        return toResponse(consulta);
    }

    public ConsultaResponseDTO buscarPorId(Long id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada!"));

        return toResponse(consulta);
    }

    // Regra 7 — ADMIN vê todas as consultas
    public List<ConsultaResponseDTO> listarTodas() {
        return consultaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // Regra 6 — dentista vê só suas consultas
    public List<ConsultaResponseDTO> listarPorDentista(Long idDentista) {
        return consultaRepository.findAllByDentistaId(idDentista)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ConsultaResponseDTO> listarPorPaciente(Long idPaciente) {
        return consultaRepository.findAllByPacienteId(idPaciente)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // Regra 3 — cancelamento exige motivo
    public ConsultaResponseDTO cancelar(ConsultaCancelamentoDTO dto) {
        Consulta consulta = consultaRepository.findById(dto.getIdConsulta())
                .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada!"));

        // Verifica se já está cancelada
        if (consulta.getStatus() == StatusConsulta.CANCELADA)
            throw new IllegalArgumentException("Consulta já está cancelada!");

        // Verifica se já foi realizada
        if (consulta.getStatus() == StatusConsulta.REALIZADA)
            throw new IllegalArgumentException("Consulta já realizada não pode ser cancelada!");

        consulta.setStatus(StatusConsulta.CANCELADA);
        consulta.setMotivoCancelamento(dto.getMotivoCancelamento());

        consultaRepository.save(consulta);

        return toResponse(consulta);
    }

    public ConsultaResponseDTO finalizar(Long id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada!"));

        if (consulta.getStatus() == StatusConsulta.CANCELADA)
            throw new IllegalArgumentException("Consulta cancelada não pode ser realizada!");

        if (consulta.getStatus() == StatusConsulta.REALIZADA)
            throw new IllegalArgumentException("Consulta já foi realizada!");

        consulta.setStatus(StatusConsulta.REALIZADA);
        consultaRepository.save(consulta);

        return toResponse(consulta);
    }


    // ─── Métodos privados ─────────────────────────────────────────

    private ConsultaResponseDTO toResponse(Consulta consulta) {
        return new ConsultaResponseDTO(
                consulta.getId(),
                consulta.getPaciente().getId(),
                consulta.getDentista().getId(),
                consulta.getUsuario().getId(),
                consulta.getDescricao(),
                consulta.getMotivoCancelamento(),
                consulta.getDataInicio(),
                consulta.getDataFim(),
                consulta.getDataRegistro(),
                consulta.getStatus()
        );
    }
}
