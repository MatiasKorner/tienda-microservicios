-- 1. Conexión a la base de datos de opinion
\c opinion

-- 2. Eliminación en orden jerárquico inverso
DROP TABLE IF EXISTS votos_utilidad CASCADE;
DROP TABLE IF EXISTS opiniones CASCADE;
DROP TABLE IF EXISTS moderacion_estados CASCADE;
DROP TABLE IF EXISTS usuarios_proyeccion CASCADE;
DROP TABLE IF EXISTS videojuegos_proyeccion CASCADE;

-- 3. Creación de tablas
CREATE TABLE usuarios_proyeccion (
    email VARCHAR(100) PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    apellido VARCHAR(150) NOT NULL,
    rol VARCHAR(50) NOT NULL
);

CREATE TABLE videojuegos_proyeccion (
    ean VARCHAR(13) PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    precio NUMERIC(10, 2) NOT NULL
);

-- Tabla maestra para estados de moderación (Aprobada, Pendiente, Rechazada)
CREATE TABLE moderacion_estados (
    id_estado INT PRIMARY KEY,
    descripcion VARCHAR(20) NOT NULL UNIQUE
);

-- Tabla principal de opiniones
CREATE TABLE opiniones (
    id_opinion SERIAL PRIMARY KEY,
    ean VARCHAR(13) REFERENCES videojuegos_proyeccion(ean), -- FK lógica al microservicio de Catálogo
    usuario_email VARCHAR(100) NOT NULL REFERENCES usuarios_proyeccion(email), -- FK lógica al microservicio de Usuarios
    calificacion INT NOT NULL CHECK (calificacion BETWEEN 1 AND 5),
    comentario TEXT,
    id_estado INT DEFAULT 1 REFERENCES moderacion_estados(id_estado),
    CONSTRAINT uq_usuario_juego UNIQUE (usuario_email, ean) -- Clave alterna: una reseña por juego/usuario
);

-- Tabla para marcar si una reseña fue útil para otros usuarios
CREATE TABLE votos_utilidad (
    id_voto BIGSERIAL PRIMARY KEY,
    id_opinion INT REFERENCES opiniones(id_opinion) ON DELETE CASCADE,
    email_votante VARCHAR(100) NOT NULL REFERENCES usuarios_proyeccion(email),
    es_util BOOLEAN NOT NULL,
    fecha_voto DATE DEFAULT CURRENT_DATE,
    CONSTRAINT uq_voto_usuario UNIQUE (id_opinion, email_votante)
);

-- Índices para optimizar la carga de reseñas por juego y filtrado por calificación
CREATE INDEX idx_opinion_juego ON opiniones(ean);
CREATE INDEX idx_opinion_calificacion ON opiniones(calificacion);

-- 4. Poblado de datos de prueba
INSERT INTO usuarios_proyeccion VALUES ('j.perez@duocuc.cl', 'Juan', 'Perez', 'Cliente');
INSERT INTO usuarios_proyeccion VALUES ('marta.p@factogames.cl', 'Marta', 'Polanco', 'Soporte');
INSERT INTO videojuegos_proyeccion VALUES ('3391892017250', 'Elden Ring', 54990.00);
INSERT INTO videojuegos_proyeccion VALUES ('0045496478759', 'The Legend of Zelda', 59990.00);

-- Estados de moderación
INSERT INTO moderacion_estados (id_estado, descripcion) VALUES 
(1, 'PENDIENTE'),
(2, 'APROBADA'),
(3, 'RECHAZADA');

-- Opiniones (Casos: Calificación máxima, mínima, sin comentario, pendiente)
INSERT INTO opiniones (ean, usuario_email, calificacion, comentario, id_estado) VALUES 
('3391892017250', 'j.perez@duocuc.cl', 5, 'Una obra maestra, el mejor mundo abierto.', 2),
('3391892017250', 'marta.p@factogames.cl', 4, 'Muy bueno, pero difícil.', 2),
('0045496478759', 'j.perez@duocuc.cl', 1, 'No me funcionó el código de descarga.', 3); -- Rechazada por política de soporte

-- Votos de utilidad (Casos: Feedback positivo y negativo sobre las reseñas)
INSERT INTO votos_utilidad (id_opinion, email_votante, es_util) VALUES 
(1, 'marta.p@factogames.cl', TRUE),
(2, 'j.perez@duocuc.cl', FALSE);
