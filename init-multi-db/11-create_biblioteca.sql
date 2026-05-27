-- 1. Conexión a la base de datos de biblioteca
\c biblioteca

-- 2. Eliminación en orden jerárquico inverso
DROP TABLE IF EXISTS licencias_activas CASCADE;
DROP TABLE IF EXISTS juegos_poseidos CASCADE;
DROP TABLE IF EXISTS plataformas_acceso CASCADE;
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

-- Define las plataformas desde donde se accede al juego (Launcher propio, Steam, Epic, etc.)
CREATE TABLE plataformas_acceso (
    id_plataforma SERIAL PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL UNIQUE,
    requiere_launcher BOOLEAN DEFAULT TRUE
);

-- Tabla principal que vincula al usuario con sus juegos comprados
CREATE TABLE juegos_poseidos (
    id_propiedad SERIAL PRIMARY KEY,
    usuario_email VARCHAR(100) NOT NULL REFERENCES usuarios_proyeccion(email), -- FK lógica al microservicio de Usuarios
    ean VARCHAR(13) REFERENCES videojuegos_proyeccion(ean), -- FK lógica al microservicio de Catálogo
    fecha_adquisicion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    horas_jugadas DECIMAL(7, 1) DEFAULT 0.0 CHECK (horas_jugadas >= 0),
    CONSTRAINT uq_usuario_biblioteca UNIQUE (usuario_email, ean)
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
CREATE INDEX idx_biblioteca_usuario ON juegos_poseidos(usuario_email);
CREATE INDEX idx_licencia_clave ON licencias_activas(clave_activacion);

-- 4. Poblado de datos de prueba
INSERT INTO usuarios_proyeccion VALUES ('j.perez@duocuc.cl', 'Juan', 'Perez', 'Cliente');
INSERT INTO usuarios_proyeccion VALUES ('marta.p@factogames.cl', 'Marta', 'Polanco', 'Soporte');
INSERT INTO videojuegos_proyeccion VALUES ('0045496478759', 'The Legend of Zelda', 59990.00);
INSERT INTO videojuegos_proyeccion VALUES ('3391892017250', 'Elden Ring', 54990.00);

-- Plataformas
INSERT INTO plataformas_acceso (nombre, requiere_launcher) VALUES 
('Tienda Interna', FALSE),
('Steam Sync', TRUE),
('Epic Games Store', TRUE);

-- Juegos poseídos (Casos: Juego recién comprado, juego con muchas horas, varios usuarios)
INSERT INTO juegos_poseidos (usuario_email, ean, horas_jugadas) VALUES 
('j.perez@duocuc.cl', '3391892017250', 120.5), -- Usuario juan tiene el juego 501 con mucho progreso
('j.perez@duocuc.cl', '0045496478759', 0.0),   -- Usuario 101 compró el juego 502 pero no lo ha jugado
('marta.p@factogames.cl', '3391892017250', 15.2);  -- Usuario marta también tiene el juego 501

-- Licencias (Casos: Clave vinculada a plataforma, claves únicas)
INSERT INTO licencias_activas (id_propiedad, id_plataforma, clave_activacion, activada_en) VALUES 
(1, 1, 'ABCD-1234-EFGH-5678', '2026-01-10 14:00:00'),
(2, 2, 'STEAM-999-KEY-888', NULL), -- Comprado pero aún no activado en Steam
(3, 3, 'EPIC-GAME-PROMO-001', '2026-04-25 09:30:00');
