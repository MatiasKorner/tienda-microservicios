-- 1. Conexión a la base de datos de notificaciones
\c notificaciones

-- 2. Eliminación en orden jerárquico inverso
DROP TABLE IF EXISTS notificaciones CASCADE;
DROP TABLE IF EXISTS plantillas CASCADE;
DROP TABLE IF EXISTS canales CASCADE;
DROP TABLE IF EXISTS usuarios_proyeccion CASCADE;


-- 3. Creación de tablas
-- Canales de comunicación disponibles
CREATE TABLE usuarios_proyeccion (
    email VARCHAR(100) PRIMARY KEY,
    username VARCHAR(50) NOT NULL
);

CREATE TABLE canales (
    id_canal SERIAL PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL UNIQUE, -- Ej: 'EMAIL', 'PUSH', 'SMS'
    activo BOOLEAN DEFAULT TRUE
);

-- Plantillas predefinidas para los mensajes (confirmación de compra, oferta, etc.)
CREATE TABLE plantillas (
    id_plantilla SERIAL PRIMARY KEY,
    codigo_evento VARCHAR(50) NOT NULL UNIQUE, -- Ej: 'ORDEN_CONFIRMADA'
    asunto_base VARCHAR(100),
    cuerpo_base TEXT NOT NULL,
    id_canal INT REFERENCES canales(id_canal)
);

-- Registro individual de notificaciones enviadas
CREATE TABLE notificaciones (
    id_notificacion BIGSERIAL PRIMARY KEY,
    id_usuario INT NOT NULL, -- FK lógica al microservicio de Usuarios
    id_plantilla INT REFERENCES plantillas(id_plantilla),
    leido BOOLEAN DEFAULT FALSE,
    fecha_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Índices para optimizar la bandeja de entrada del usuario y auditoría
CREATE INDEX idx_notif_usuario_leido ON notificaciones(id_usuario, leido);
CREATE INDEX idx_notif_fecha ON notificaciones(fecha_envio);

-- 4. Poblado de datos de prueba
-- Canales
INSERT INTO canales (nombre, activo) VALUES 
('EMAIL', TRUE),
('PUSH', TRUE),
('SMS', FALSE); -- Canal desactivado temporalmente

-- Plantillas (Casos: Notificación transaccional y comercial)
INSERT INTO plantillas (codigo_evento, asunto_base, cuerpo_base, id_canal) VALUES 
('BIENVENIDA', '¡Bienvenido a la Tienda!', 'Hola, gracias por unirte...', 1),
('COMPRA_EXITOSA', 'Confirmación de tu pedido', 'Tu pago ha sido procesado...', 1),
('ALERTA_STOCK', '¡Ya disponible!', 'El juego que esperabas volvió...', 2);

-- Notificaciones (Casos: Leídas, no leídas, diferentes usuarios)
INSERT INTO notificaciones (id_usuario, id_plantilla, leido) VALUES 
(101, 1, TRUE),  -- Bienvenida ya leída
(101, 2, FALSE), -- Compra reciente sin leer
(102, 3, FALSE), -- Alerta push pendiente
(103, 1, TRUE);  -- Bienvenida leída por otro usuario

INSERT INTO usuarios_proyeccion VALUES ('j.perez@duocuc.cl', 'Juan Perez');