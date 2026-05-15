-- =========================================
-- Script de inserção de dados para a aplicação de agendamento de consultas odontológicas.
-- Este script insere dados iniciais, especificamente para testes, nas tabelas de usuários, pacientes, dentistas, especialidades, dentista_especialidade e consultas.
-- Certifique-se de que as tabelas foram criadas corretamente antes de executar este script.
-- ========================================



-- =========================================
-- INSERTS: usuarios
-- =========================================

INSERT INTO usuarios (
    nome,
    cpf,
    email,
    senha,
    perfil,
    ativo
) VALUES
(
    'Administrador Geral',
    '111.111.111-11',
    'admin@agendasmile.com',
    '$2a$10$adminhashbcrypt',
    'ADMIN',
    TRUE
),
(
    'Carlos Mendes',
    '222.222.222-22',
    'carlos@agendasmile.com',
    '$2a$10$dentistahashbcrypt',
    'DENTISTA',
    TRUE
);

-- =========================================
-- INSERTS: pacientes
-- =========================================

INSERT INTO pacientes (
    nome,
    email,
    cpf,
    telefone
) VALUES
(
    'João Silva',
    'joao@email.com',
    '333.333.333-33',
    '(41) 99999-1111'
),
(
    'Maria Oliveira',
    'maria@email.com',
    '444.444.444-44',
    '(41) 99999-2222'
),
(
    'Fernanda Costa',
    'fernanda@email.com',
    '555.555.555-55',
    '(41) 99999-3333'
);

-- =========================================
-- INSERTS: dentistas
-- =========================================

INSERT INTO dentistas (
    nome,
    cpf,
    email,
    cro,
    ativo
) VALUES
(
    'Dr. Rafael Souza',
    '666.666.666-66',
    'rafael@clinicasmile.com',
    'CRO-PR-12345',
    TRUE
),
(
    'Dra. Juliana Lima',
    '777.777.777-77',
    'juliana@clinicasmile.com',
    'CRO-PR-54321',
    TRUE
);

-- =========================================
-- INSERTS: especialidades
-- =========================================

INSERT INTO especialidades (
    nome
) VALUES
('Ortodontia'),
('Implantodontia'),
('Endodontia'),
('Odontopediatria');

-- =========================================
-- INSERTS: dentista_especialidade
-- =========================================

INSERT INTO dentista_especialidade (
    id_dentista,
    id_especialidade
) VALUES
(1, 1),
(1, 2),
(2, 3),
(2, 4);

-- =========================================
-- INSERTS: consultas
-- =========================================

INSERT INTO consultas (
    id_paciente,
    id_dentista,
    id_usuario,
    descricao,
    motivo_cancelamento,
    data_inicio,
    data_fim,
    status
) VALUES
(
    1,
    1,
    1,
    'Avaliação inicial e limpeza dental.',
    NULL,
    '2026-06-10 09:00:00',
    '2026-06-10 10:00:00',
    'AGENDADA'
),
(
    2,
    2,
    1,
    'Tratamento de canal.',
    NULL,
    '2026-06-11 14:00:00',
    '2026-06-11 15:30:00',
    'FINALIZADA'
),
(
    3,
    1,
    1,
    'Consulta de manutenção ortodôntica.',
    'Paciente informou indisponibilidade.',
    '2026-06-12 11:00:00',
    '2026-06-12 11:30:00',
    'CANCELADA'
);