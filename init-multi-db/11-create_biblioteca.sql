-- 1. Conexión a la base de datos de biblioteca
\c biblioteca

-- 2. Eliminación en orden jerárquico inverso
DROP TABLE IF EXISTS licencias_activas CASCADE;
DROP TABLE IF EXISTS juegos_poseidos CASCADE;
DROP TABLE IF EXISTS plataformas_acceso CASCADE;
DROP TABLE IF EXISTS usuarios_proyeccion CASCADE;
DROP TABLE IF EXISTS videojuegos_proyeccion CASCADE;

-- 3. Creación de tablas
-- Define las plataformas desde donde se accede al juego (Launcher propio, Steam, Epic, etc.)
CREATE TABLE usuarios_proyeccion (
    email VARCHAR(100) PRIMARY KEY,
    username VARCHAR(50) NOT NULL
);

CREATE TABLE videojuegos_proyeccion (
    id VARCHAR(20) PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
);

CREATE TABLE plataformas_acceso (
    id_plataforma SERIAL PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL UNIQUE,
    requiere_launcher BOOLEAN DEFAULT TRUE
);

-- Tabla principal que vincula al usuario con sus juegos comprados
CREATE TABLE juegos_poseidos (
    id_propiedad SERIAL PRIMARY KEY,
    id_usuario INT NOT NULL, -- FK lógica al microservicio de Usuarios
    id_juego INT NOT NULL,   -- FK lógica al microservicio de Catálogo
    fecha_adquisicion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    horas_jugadas DECIMAL(7, 1) DEFAULT 0.0 CHECK (horas_jugadas >= 0),
    CONSTRAINT uq_usuario_biblioteca UNIQUE (id_usuario, id_juego)
);

-- Tabla para gestionar las claves digitales o licencias de activación
CREATE TABLE licencias_activas (
    id_licencia SERIAL PRIMARY KEY,
    id_propiedad INT REFERENCES juegos_poseidos(id_propiedad) ON DELETE CASCADE,
    id_plataforma INT REFERENCES plataformas_acceso(id_plataforma),
    clave_activacion VARCHAR(50) NOT NULL UNIQUE,
    activada_en TIMESTAMP
);

-- Índices para búsqueda rápida de la biblioteca de un usuario y validación de claves
CREATE INDEX idx_biblioteca_usuario ON juegos_poseidos(id_usuario);
CREATE INDEX idx_licencia_clave ON licencias_activas(clave_activacion);

-- 4. Poblado de datos de prueba
-- Plataformas
INSERT INTO plataformas_acceso (nombre, requiere_launcher) VALUES 
('Tienda Interna', FALSE),
('Steam Sync', TRUE),
('Epic Games Store', TRUE);

-- Juegos poseídos (Casos: Juego recién comprado, juego con muchas horas, varios usuarios)
INSERT INTO juegos_poseidos (id_usuario, id_juego, horas_jugadas) VALUES 
(101, 501, 120.5), -- Usuario 101 tiene el juego 501 con mucho progreso
(101, 502, 0.0),   -- Usuario 101 compró el juego 502 pero no lo ha jugado
(102, 501, 15.2);  -- Usuario 102 también tiene el juego 501

-- Licencias (Casos: Clave vinculada a plataforma, claves únicas)
INSERT INTO licencias_activas (id_propiedad, id_plataforma, clave_activacion, activada_en) VALUES 
(1, 1, 'ABCD-1234-EFGH-5678', '2026-01-10 14:00:00'),
(2, 2, 'STEAM-999-KEY-888', NULL), -- Comprado pero aún no activado en Steam
(3, 3, 'EPIC-GAME-PROMO-001', '2026-04-25 09:30:00');

INSERT INTO usuarios_proyeccion VALUES ('j.perez@duocuc.cl', 'Juan Perez');
INSERT INTO videojuegos_proyeccion VALUES ('ER-2022', 'Elden Ring');