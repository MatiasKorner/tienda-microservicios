-- 1. Conexión a la base de datos de reseñas
\c resenas

-- 2. Eliminación en orden jerárquico inverso
DROP TABLE IF EXISTS votos_utilidad CASCADE;
DROP TABLE IF EXISTS opiniones CASCADE;
DROP TABLE IF EXISTS moderacion_estados CASCADE;
DROP TABLE IF EXISTS usuarios_proyeccion CASCADE;
DROP TABLE IF EXISTS videojuegos_proyeccion CASCADE;

-- 3. Creación de tablas
-- Tabla maestra para estados de moderación (Aprobada, Pendiente, Rechazada)
CREATE TABLE usuarios_proyeccion (
    email VARCHAR(100) PRIMARY KEY,
    username VARCHAR(50) NOT NULL
);

CREATE TABLE videojuegos_proyeccion (
    id VARCHAR(20) PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
);

CREATE TABLE moderacion_estados (
    id_estado INT PRIMARY KEY,
    descripcion VARCHAR(20) NOT NULL UNIQUE
);

-- Tabla principal de opiniones
CREATE TABLE opiniones (
    id_opinion SERIAL PRIMARY KEY,
    id_juego INT NOT NULL, -- FK lógica al microservicio de Catálogo
    id_usuario INT NOT NULL, -- FK lógica al microservicio de Usuarios
    calificacion INT NOT NULL CHECK (calificacion BETWEEN 1 AND 5),
    comentario TEXT,
    id_estado INT DEFAULT 1 REFERENCES moderacion_estados(id_estado),
    CONSTRAINT uq_usuario_juego UNIQUE (id_usuario, id_juego) -- Clave alterna: una reseña por juego/usuario
);

-- Tabla para marcar si una reseña fue útil para otros usuarios
CREATE TABLE votos_utilidad (
    id_voto BIGSERIAL PRIMARY KEY,
    id_opinion INT REFERENCES opiniones(id_opinion) ON DELETE CASCADE,
    id_usuario_votante INT NOT NULL,
    es_util BOOLEAN NOT NULL,
    fecha_voto DATE DEFAULT CURRENT_DATE
);

-- Índices para optimizar la carga de reseñas por juego y filtrado por calificación
CREATE INDEX idx_resena_juego ON opiniones(id_juego);
CREATE INDEX idx_resena_calificacion ON opiniones(calificacion);

-- 4. Poblado de datos de prueba
-- Estados de moderación
INSERT INTO moderacion_estados (id_estado, descripcion) VALUES 
(1, 'PENDIENTE'),
(2, 'APROBADA'),
(3, 'RECHAZADA');

-- Opiniones (Casos: Calificación máxima, mínima, sin comentario, pendiente)
INSERT INTO opiniones (id_juego, id_usuario, calificacion, comentario, id_estado) VALUES 
(1, 101, 5, 'Una obra maestra, el mejor mundo abierto.', 2),
(1, 102, 4, 'Muy bueno, pero difícil.', 2),
(2, 101, 1, 'No me funcionó el código de descarga.', 3), -- Rechazada por política de soporte
(3, 103, 3, NULL, 1); -- Solo calificación, sin texto, pendiente de moderar

-- Votos de utilidad (Casos: Feedback positivo y negativo sobre las reseñas)
INSERT INTO votos_utilidad (id_opinion, id_usuario_votante, es_util) VALUES 
(1, 105, TRUE),
(1, 106, TRUE),
(2, 101, FALSE);

INSERT INTO usuarios_proyeccion VALUES ('j.perez@duocuc.cl', 'Juan Perez');
INSERT INTO videojuegos_proyeccion VALUES ('ER-2022', 'Elden Ring');