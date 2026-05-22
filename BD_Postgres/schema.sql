-- =====================================================
-- AgendaSmile — Sistema de Gestão de Consultas
-- DDL — Data Definition Language
-- Banco: PostgreSQL
-- =====================================================

-- =====================================================
-- TABELA: usuarios
-- Usuários do sistema (ADMIN e DENTISTA)
-- =====================================================
CREATE TABLE usuarios (
    id           BIGSERIAL    PRIMARY KEY,
    nome         VARCHAR(100) NOT NULL,
    cpf          VARCHAR(14)  UNIQUE NOT NULL,
    email        VARCHAR(150) UNIQUE NOT NULL,
    senha        VARCHAR(255) NOT NULL,
    data_criacao TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    ultimo_login TIMESTAMP,
    perfil       VARCHAR(20)  NOT NULL
                 CONSTRAINT chk_usuarios_perfil
                 CHECK (perfil IN ('ADMIN', 'DENTISTA')),
    ativo        BOOLEAN      DEFAULT TRUE
);

-- =====================================================
-- TABELA: pacientes
-- Pacientes cadastrados — sem login no sistema
-- =====================================================
CREATE TABLE pacientes (
    id           BIGSERIAL    PRIMARY KEY,
    nome         VARCHAR(100) NOT NULL,
    email        VARCHAR(150) UNIQUE NOT NULL,
    cpf          VARCHAR(14)  UNIQUE NOT NULL,
    data_criacao TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    telefone     VARCHAR(20)
);

-- =====================================================
-- TABELA: dentistas
-- Profissionais vinculados a um usuario do sistema
-- =====================================================
CREATE TABLE dentistas (
    id           BIGSERIAL    PRIMARY KEY,
    nome         VARCHAR(100) NOT NULL,
    cpf          VARCHAR(14)  UNIQUE NOT NULL,
    email        VARCHAR(150) UNIQUE NOT NULL,
    cro          VARCHAR(20)  NOT NULL,
    data_criacao TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    ativo        BOOLEAN      DEFAULT TRUE,
    id_usuario   BIGINT       UNIQUE
                 REFERENCES usuarios(id)
                 ON DELETE SET NULL
);

-- =====================================================
-- TABELA: especialidades
-- =====================================================
CREATE TABLE especialidades (
    id   BIGSERIAL    PRIMARY KEY,
    nome VARCHAR(100) UNIQUE NOT NULL
);

-- =====================================================
-- TABELA: dentista_especialidade
-- Tabela associativa N:N
-- =====================================================
CREATE TABLE dentista_especialidade (
    id               BIGSERIAL PRIMARY KEY,
    id_dentista      BIGINT    NOT NULL
                     REFERENCES dentistas(id)
                     ON DELETE CASCADE,
    id_especialidade BIGINT    NOT NULL
                     REFERENCES especialidades(id)
                     ON DELETE CASCADE,
    CONSTRAINT uq_dentista_especialidade
        UNIQUE (id_dentista, id_especialidade)
);

-- =====================================================
-- TABELA: consultas
-- =====================================================
CREATE TABLE consultas (
    id                  BIGSERIAL    PRIMARY KEY,
    id_paciente         BIGINT       NOT NULL
                        REFERENCES pacientes(id),
    id_dentista         BIGINT       NOT NULL
                        REFERENCES dentistas(id),
    id_usuario          BIGINT       NOT NULL
                        REFERENCES usuarios(id),
    descricao           TEXT         NOT NULL,
    motivo_cancelamento TEXT,
    data_inicio         TIMESTAMP    NOT NULL,
    data_fim            TIMESTAMP    NOT NULL,
    data_registro       TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    status              VARCHAR(20)  NOT NULL DEFAULT 'AGENDADA'
                        CONSTRAINT chk_consultas_status
                        CHECK (status IN ('AGENDADA','CANCELADA','FINALIZADA')),
    CONSTRAINT chk_consultas_data_fim
        CHECK (data_fim > data_inicio)
);

-- =====================================================
-- ÍNDICES para performance
-- =====================================================
CREATE INDEX idx_consultas_dentista    ON consultas(id_dentista);
CREATE INDEX idx_consultas_paciente    ON consultas(id_paciente);
CREATE INDEX idx_consultas_usuario     ON consultas(id_usuario);
CREATE INDEX idx_consultas_status      ON consultas(status);
CREATE INDEX idx_consultas_data_inicio ON consultas(data_inicio);
CREATE INDEX idx_dent_esp_dentista     ON dentista_especialidade(id_dentista);
CREATE INDEX idx_dent_esp_especialidade ON dentista_especialidade(id_especialidade);