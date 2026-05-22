-- =====================================================
-- AgendaSmile — DML — Dados de Exemplo
-- IMPORTANTE: senha "admin123" encodada em BCrypt
-- =====================================================

-- =====================================================
-- USUARIOS
-- =====================================================
INSERT INTO usuarios (nome, cpf, email, senha, perfil, ativo)
VALUES
    ('Maria Recepcionista', '000.000.000-00',
     'admin@agendaSmile.com',
     '$2a$10$N.zmdr9zkzoGtM72y3dGS.QibSANBe0FMzXkwuaOhyuwj7tEBhpWq',
     'ADMIN', TRUE),

    ('Dr. Carlos Silva', '111.111.111-11',
     'carlos@agendaSmile.com',
     '$2a$10$N.zmdr9zkzoGtM72y3dGS.QibSANBe0FMzXkwuaOhyuwj7tEBhpWq',
     'DENTISTA', TRUE),

    ('Dra. Ana Beatriz', '222.222.222-22',
     'ana@agendaSmile.com',
     '$2a$10$N.zmdr9zkzoGtM72y3dGS.QibSANBe0FMzXkwuaOhyuwj7tEBhpWq',
     'DENTISTA', TRUE),

    ('Dr. Roberto Mendes', '333.333.333-33',
     'roberto@agendaSmile.com',
     '$2a$10$N.zmdr9zkzoGtM72y3dGS.QibSANBe0FMzXkwuaOhyuwj7tEBhpWq',
     'DENTISTA', TRUE);

-- =====================================================
-- PACIENTES
-- =====================================================
INSERT INTO pacientes (nome, email, cpf, telefone)
VALUES
    ('João da Silva',    'joao.silva@email.com',    '444.444.444-44', '(11) 99999-0001'),
    ('Maria Oliveira',   'maria.oliveira@email.com', '555.555.555-55', '(11) 99999-0002'),
    ('Pedro Henrique',   'pedro.h@email.com',        '666.666.666-66', '(21) 98888-0003'),
    ('Fernanda Costa',   'fernanda.c@email.com',     '777.777.777-77', '(31) 97777-0004'),
    ('Lucas Almeida',    'lucas.a@email.com',        '888.888.888-88', '(41) 96666-0005');

-- =====================================================
-- DENTISTAS (vinculados aos usuarios DENTISTA)
-- id_usuario referencia os IDs inseridos acima:
-- Carlos  → id 2
-- Ana     → id 3
-- Roberto → id 4
-- =====================================================
INSERT INTO dentistas (nome, cpf, email, cro, ativo, id_usuario)
VALUES
    ('Dr. Carlos Silva',   '111.111.111-11',
     'carlos@agendaSmile.com',  'CRO-SP 12345', TRUE, 2),

    ('Dra. Ana Beatriz',   '222.222.222-22',
     'ana@agendaSmile.com',     'CRO-RJ 67890', TRUE, 3),

    ('Dr. Roberto Mendes', '333.333.333-33',
     'roberto@agendaSmile.com', 'CRO-MG 11223', TRUE, 4);

-- =====================================================
-- ESPECIALIDADES
-- =====================================================
INSERT INTO especialidades (nome)
VALUES
    ('Clínica Geral'),
    ('Ortodontia'),
    ('Endodontia'),
    ('Periodontia'),
    ('Implantodontia'),
    ('Odontopediatria'),
    ('Cirurgia Oral');

-- =====================================================
-- DENTISTA_ESPECIALIDADE
-- Dr. Carlos  (id 1) → Clínica Geral, Endodontia, Implantodontia
-- Dra. Ana    (id 2) → Clínica Geral, Ortodontia, Odontopediatria
-- Dr. Roberto (id 3) → Clínica Geral, Periodontia, Cirurgia Oral
-- =====================================================
INSERT INTO dentista_especialidade (id_dentista, id_especialidade)
VALUES
    (1, 1), (1, 3), (1, 5),
    (2, 1), (2, 2), (2, 6),
    (3, 1), (3, 4), (3, 7);

-- =====================================================
-- CONSULTAS
-- id_usuario indica QUEM agendou:
-- → Admin (id 1) ou o próprio Dentista (id 2, 3, 4)
-- =====================================================
INSERT INTO consultas
    (id_paciente, id_dentista, id_usuario,
     descricao, data_inicio, data_fim, status)
VALUES
    -- Admin agendou para Dr. Carlos
    (1, 1, 1,
     'Consulta de rotina e limpeza',
     '2025-06-20 08:00:00', '2025-06-20 09:00:00',
     'AGENDADA'),

    -- Admin agendou para Dra. Ana
    (2, 2, 1,
     'Avaliação para aparelho ortodôntico',
     '2025-06-20 09:30:00', '2025-06-20 10:30:00',
     'AGENDADA'),

    -- Dr. Carlos agendou ele mesmo
    (3, 1, 2,
     'Tratamento de canal',
     '2025-06-18 14:00:00', '2025-06-18 16:00:00',
     'FINALIZADA'),

    -- Admin agendou para Dr. Roberto (cancelada)
    (4, 3, 1,
     'Extração do siso',
     '2025-06-15 08:00:00', '2025-06-15 09:30:00',
     'CANCELADA'),

    -- Dra. Ana agendou ela mesma
    (5, 2, 3,
     'Consulta de avaliação geral',
     '2025-06-22 15:00:00', '2025-06-22 16:00:00',
     'AGENDADA'),

    -- Dr. Roberto agendou ele mesmo
    (1, 3, 4,
     'Limpeza e profilaxia',
     '2025-06-23 10:00:00', '2025-06-23 11:00:00',
     'AGENDADA');

-- Motivo da consulta cancelada
UPDATE consultas
SET motivo_cancelamento = 'Paciente não compareceu e não avisou com antecedência'
WHERE status = 'CANCELADA';
