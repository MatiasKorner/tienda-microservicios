-- 1. Conexión a la base de datos de promociones
\c promociones

-- 2. Eliminación en orden jerárquico inverso
DROP TABLE IF EXISTS aplicacion_promos CASCADE;
DROP TABLE IF EXISTS cupones CASCADE;
DROP TABLE IF EXISTS campana CASCADE;
DROP TABLE IF EXISTS videojuegos_proyeccion CASCADE;

-- 3. Creación de tablas
-- Tabla de campaña generales (ej: Steam Summer Sale, Black Friday)
CREATE TABLE videojuegos_proyeccion (
    id VARCHAR(20) PRIMARY KEY,
    precio NUMERIC(10, 2) NOT NULL
);

CREATE TABLE campana (
    id_campaña SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descuento_porcentaje INT NOT NULL CHECK (descuento_porcentaje BETWEEN 1 AND 99),
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL CHECK (fecha_fin > fecha_inicio)
);

-- Tabla de cupones de un solo uso o específicos por código
CREATE TABLE cupones (
    id_cupon SERIAL PRIMARY KEY,
    codigo_alfa VARCHAR(15) NOT NULL UNIQUE, -- Clave alterna
    monto_fijo DECIMAL(10, 2),
    es_activo BOOLEAN DEFAULT TRUE,
    usos_maximos INT DEFAULT 1
);

-- Tabla para vincular promociones con videojuegos específicos
CREATE TABLE aplicacion_promos (
    id_aplicacion SERIAL PRIMARY KEY,
    id_juego INT NOT NULL, -- FK lógica al microservicio de Catálogo
    id_campaña INT REFERENCES campana(id_campaña) ON DELETE CASCADE,
    prioridad INT DEFAULT 1,
    CONSTRAINT uq_juego_campaña UNIQUE (id_juego, id_campaña)
);

-- Índices para búsqueda rápida de cupones y ofertas vigentes
CREATE INDEX idx_cupon_codigo ON cupones(codigo_alfa);
CREATE INDEX idx_campaña_fechas ON campana(fecha_inicio, fecha_fin);

-- 4. Poblado de datos de prueba
-- campaña
INSERT INTO campana (nombre, descuento_porcentaje, fecha_inicio, fecha_fin) VALUES 
('Summer Sale 2026', 50, '2026-06-01', '2026-06-30'),
('Lanzamiento Indie', 15, '2026-04-20', '2026-05-01'),
('Oferta Flash Hoy', 90, '2026-04-27', '2026-04-28');

-- Cupones (Casos: Cupón activo, cupón agotado, código promocional)
INSERT INTO cupones (codigo_alfa, monto_fijo, es_activo, usos_maximos) VALUES 
('BIENVENIDA20', 20.00, TRUE, 1000),
('INFLUENCER_TOP', 5.00, TRUE, 50),
('EXPIRED_99', 99.99, FALSE, 0);

-- Aplicación de promociones (Casos de borde: Un juego en múltiples campaña)
INSERT INTO aplicacion_promos (id_juego, id_campaña, prioridad) VALUES 
(1, 1, 2), -- Juego 1 en Summer Sale
(1, 3, 1), -- Juego 1 también en Oferta Flash (prioridad alta)
(2, 2, 1); -- Juego 2 en Lanzamiento Indie