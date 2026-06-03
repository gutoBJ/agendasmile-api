package com.agendasmile.api.service;

import com.agendasmile.api.dto.dentista.DentistaRequestDTO;
import com.agendasmile.api.dto.dentista.DentistaResponseDTO;
import com.agendasmile.api.dto.especialidade.EspecialidadeResponseDTO;
import com.agendasmile.api.entity.Dentista;
import com.agendasmile.api.entity.DentistaEspecialidade;
import com.agendasmile.api.entity.Especialidade;
import com.agendasmile.api.entity.Usuario;
import com.agendasmile.api.repository.DentistaEspecialidadeRepository;
import com.agendasmile.api.repository.DentistaRepository;
import com.agendasmile.api.repository.EspecialidadeRepository;
import com.agendasmile.api.repository.UsuarioRepository;
import com.agendasmile.api.utils.ValidacaoUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DentistaService {

    private final DentistaRepository dentistaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EspecialidadeRepository especialidadeRepository;
    private final DentistaEspecialidadeRepository dentistaEspecialidadeRepository;

    public DentistaService(
            DentistaRepository dentistaRepository,
            UsuarioRepository usuarioRepository,
            EspecialidadeRepository especialidadeRepository,
            DentistaEspecialidadeRepository dentistaEspecialidadeRepository) {
        this.dentistaRepository = dentistaRepository;
        this.usuarioRepository = usuarioRepository;
        this.especialidadeRepository = especialidadeRepository;
        this.dentistaEspecialidadeRepository = dentistaEspecialidadeRepository;
    }

    public DentistaResponseDTO cadastrar(DentistaRequestDTO dto) {
        ValidacaoUtil.validarNome(dto.getNome());
        validarCro(dto.getCro());

        // Verifica duplicidade
        if (dentistaRepository.existsByEmail(dto.getEmail()))
            throw new IllegalArgumentException("E-mail já cadastrado!");

        if (dentistaRepository.existsByCpf(dto.getCpf()))
            throw new IllegalArgumentException("CPF já cadastrado!");

        if (dentistaRepository.existsByCro(dto.getCro()))
            throw new IllegalArgumentException("CRO já cadastrado!");

        // Busca o usuário vinculado
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado!"));

        // Monta o dentista
        Dentista dentista = new Dentista();
        dentista.setNome(dto.getNome());
        dentista.setCpf(dto.getCpf());
        dentista.setEmail(dto.getEmail());
        dentista.setCro(dto.getCro());
        dentista.setUsuario(usuario);

        dentistaRepository.save(dentista);

        // Vincula as especialidades
        vincularEspecialidades(dentista, dto.getEspecialidades());

        return toResponse(dentista);
    }

    public DentistaResponseDTO buscarPorId(Long id) {
        Dentista dentista = dentistaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dentista não encontrado!"));

        return toResponse(dentista);
    }

    public List<DentistaResponseDTO> listarAtivos() {
        return dentistaRepository.findAllByAtivoTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<DentistaResponseDTO> listarTodos() {
        return dentistaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public DentistaResponseDTO atualizar(Long id, DentistaRequestDTO dto) {
        ValidacaoUtil.validarNome(dto.getNome());
        validarCro(dto.getCro());

        Dentista dentista = dentistaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dentista não encontrado!"));

        // Verifica duplicidade ignorando o próprio dentista
        if (dentistaRepository.existsByEmailAndIdNot(dto.getEmail(), id))
            throw new IllegalArgumentException("E-mail já cadastrado!");

        if (dentistaRepository.existsByCpfAndIdNot(dto.getCpf(), id))
            throw new IllegalArgumentException("CPF já cadastrado!");

        if (dentistaRepository.existsByCroAndIdNot(dto.getCro(), id))
            throw new IllegalArgumentException("CRO já cadastrado!");

        dentista.setNome(dto.getNome());
        dentista.setCpf(dto.getCpf());
        dentista.setEmail(dto.getEmail());
        dentista.setCro(dto.getCro());

        // Atualiza especialidades — remove as antigas e adiciona as novas
        dentistaEspecialidadeRepository.deleteAllByDentistaId(id);
        vincularEspecialidades(dentista, dto.getEspecialidades());

        dentistaRepository.save(dentista);

        return toResponse(dentista);
    }

    public void desativar(Long id) {
        Dentista dentista = dentistaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dentista não encontrado!"));

        if (!dentista.isAtivo())
            throw new IllegalArgumentException("Dentista já está desativado!");

        dentista.setAtivo(false);
        dentistaRepository.save(dentista);
    }

    // ─── Métodos privados ─────────────────────────────────────────

    private void vincularEspecialidades(Dentista dentista, List<Long> especialidadeIds) {
        for (Long idEspecialidade : especialidadeIds) {
            Especialidade especialidade = especialidadeRepository.findById(idEspecialidade)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Especialidade não encontrada: " + idEspecialidade));

            DentistaEspecialidade vinculo = new DentistaEspecialidade();
            vinculo.setDentista(dentista);
            vinculo.setEspecialidade(especialidade);

            dentistaEspecialidadeRepository.save(vinculo);
        }
    }

    private List<EspecialidadeResponseDTO> buscarEspecialidades(Long idDentista) {
        return dentistaEspecialidadeRepository.findAllByDentistaId(idDentista)
                .stream()
                .map(de -> new EspecialidadeResponseDTO(
                        de.getEspecialidade().getId(),
                        de.getEspecialidade().getNome()
                ))
                .toList();
    }

    // Validações importantes para o CRO:
    private void validarCro(String cro) {
        String croLimpo = cro.trim().toUpperCase();

        // Extrai só letras e só números
        String apenasLetras = croLimpo.replaceAll("[^A-Z]", "");
        String apenasNumeros = croLimpo.replaceAll("[^0-9]", "");

        // Verifica UF — exatamente 2 letras
        if (apenasLetras.length() < 2)
            throw new IllegalArgumentException("CRO deve conter a sigla do estado!");

        // Verifica número — entre 4 e 6 dígitos
        if (apenasNumeros.length() < 4 || apenasNumeros.length() > 6)
            throw new IllegalArgumentException("Número do CRO deve ter entre 4 e 6 dígitos!");
    }


    private DentistaResponseDTO toResponse(Dentista dentista) {
        return new DentistaResponseDTO(
                dentista.getId(),
                dentista.getNome(),
                dentista.getCpf(),
                dentista.getEmail(),
                dentista.getCro(),
                dentista.getDataCriacao(),
                dentista.isAtivo(),
                dentista.getUsuario().getId(), // ← só o ID!
                buscarEspecialidades(dentista.getId()) // ← lista de especialidades!
        );
    }
}