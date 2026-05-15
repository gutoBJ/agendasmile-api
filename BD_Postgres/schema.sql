-- =========================================
-- DATABASE: sistema_gestao_consultas
-- =========================================

CREATE DATABASE sistema_gestao_consultas;

-- =========================================
-- TABELA: usuarios
-- =========================================

CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    email VARCHAR(120) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ultimo_login TIMESTAMP,
    perfil VARCHAR(20) NOT NULL,
    ativo BOOLEAN DEFAULT TRUE
);

-- =========================================
-- TABELA: pacientes
-- =========================================

CREATE TABLE pacientes (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(120) UNIQUE NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    telefone VARCHAR(20)
);

-- =========================================
-- TABELA: dentistas
-- =========================================

CREATE TABLE dentistas (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    email VARCHAR(120) UNIQUE NOT NULL,
    cro VARCHAR(20) NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ativo BOOLEAN DEFAULT TRUE
);

-- =========================================
-- TABELA: especialidades
-- =========================================

CREATE TABLE especialidades (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

-- =========================================
-- TABELA: dentista_especialidade
-- =========================================

CREATE TABLE dentista_especialidade (
    id SERIAL PRIMARY KEY,

    id_dentista INTEGER NOT NULL,
    id_especialidade INTEGER NOT NULL,

    CONSTRAINT fk_dentista
        FOREIGN KEY (id_dentista)
        REFERENCES dentistas(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_especialidade
        FOREIGN KEY (id_especialidade)
        REFERENCES especialidades(id)
        ON DELETE CASCADE
);

-- =========================================
-- TABELA: consultas
-- =========================================

CREATE TABLE consultas (
    id SERIAL PRIMARY KEY,

    id_paciente INTEGER NOT NULL,
    id_dentista INTEGER NOT NULL,
    id_usuario INTEGER NOT NULL,

    descricao TEXT NOT NULL,
    motivo_cancelamento TEXT,

    data_inicio TIMESTAMP NOT NULL,
    data_fim TIMESTAMP NOT NULL,

    data_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    status VARCHAR(20) NOT NULL,

    CONSTRAINT fk_paciente
        FOREIGN KEY (id_paciente)
        REFERENCES pacientes(id),

    CONSTRAINT fk_dentista_consulta
        FOREIGN KEY (id_dentista)
        REFERENCES dentistas(id),

    CONSTRAINT fk_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuarios(id),

    CONSTRAINT chk_data_fim
        CHECK (data_fim > data_inicio),

    CONSTRAINT chk_status
        CHECK (
            status IN (
                'AGENDADA',
                'CANCELADA',
                'FINALIZADA'
            )
        ),

    CONSTRAINT chk_motivo_cancelamento
        CHECK (
            (
                status = 'CANCELADA'
                AND motivo_cancelamento IS NOT NULL
            )
            OR
            (
                status <> 'CANCELADA'
            )
        )
);