package com.agendasmile.api.service;

import com.agendasmile.api.dto.especialidade.EspecialidadeRequestDTO;
import com.agendasmile.api.dto.especialidade.EspecialidadeResponseDTO;
import com.agendasmile.api.entity.Especialidade;
import com.agendasmile.api.repository.DentistaEspecialidadeRepository;
import com.agendasmile.api.repository.EspecialidadeRepository;
import com.agendasmile.api.utils.ValidacaoUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspecialidadeService {

    private final EspecialidadeRepository especialidadeRepository;
    private final DentistaEspecialidadeRepository dentistaEspecialidadeRepository;

    public EspecialidadeService(EspecialidadeRepository especialidadeRepository,
                                DentistaEspecialidadeRepository dentistaEspecialidadeRepository) {
        this.especialidadeRepository = especialidadeRepository;
        this.dentistaEspecialidadeRepository = dentistaEspecialidadeRepository;
    }

    public EspecialidadeResponseDTO cadastrar(EspecialidadeRequestDTO dto) {
        ValidacaoUtil.validarNome(dto.getNome());

        // Verifica duplicidade
        if (especialidadeRepository.existsByNome(dto.getNome()))
            throw new IllegalArgumentException("Especialidade já cadastrada!");

        Especialidade especialidade = new Especialidade();
        especialidade.setNome(dto.getNome());

        especialidadeRepository.save(especialidade);

        return toResponse(especialidade);
    }

    public EspecialidadeResponseDTO buscarPorId(Long id) {
        Especialidade especialidade = especialidadeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Especialidade não encontrada!"));

        return toResponse(especialidade);
    }

    public List<EspecialidadeResponseDTO> listarTodas() {
        return especialidadeRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public EspecialidadeResponseDTO atualizar(Long id, EspecialidadeRequestDTO dto) {
        ValidacaoUtil.validarNome(dto.getNome());

        Especialidade especialidade = especialidadeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Especialidade não encontrada!"));

        // Verifica duplicidade ignorando a própria especialidade
        if (especialidadeRepository.existsByNomeAndIdNot(dto.getNome(), id))
            throw new IllegalArgumentException("Especialidade já cadastrada!");

        especialidade.setNome(dto.getNome());

        especialidadeRepository.save(especialidade);

        return toResponse(especialidade);
    }

    public void deletar(Long id) {
        Especialidade especialidade = especialidadeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Especialidade não encontrada!"));

        // Verifica se tem dentistas vinculados
        if (dentistaEspecialidadeRepository.existsByEspecialidadeId(id))
            throw new IllegalArgumentException(
                    "Especialidade não pode ser deletada pois está vinculada a dentistas!"
            );

        especialidadeRepository.delete(especialidade);
    }


    // ─── Métodos privados ─────────────────────────────────────────

    private EspecialidadeResponseDTO toResponse(Especialidade especialidade) {
        return new EspecialidadeResponseDTO(
                especialidade.getId(),
                especialidade.getNome()
        );
    }
}