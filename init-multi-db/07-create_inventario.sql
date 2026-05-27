-- 1. Conexión a la base de datos de inventario
\c inventario

-- 2. Eliminación de tablas en orden jerárquico inverso
DROP TABLE IF EXISTS movimientos_stock CASCADE;
DROP TABLE IF EXISTS stock_productos CASCADE;
DROP TABLE IF EXISTS almacenes CASCADE;
DROP TABLE IF EXISTS videojuegos_proyeccion CASCADE;

-- 3. Crear las tablas y sus relaciones

CREATE TABLE videojuegos_proyeccion (
    ean VARCHAR(13) PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    precio NUMERIC(10, 2) NOT NULL
);

-- Tabla de ubicaciones físicas o lógicas
CREATE TABLE almacenes (
    id_almacen SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    tipo VARCHAR(20) NOT NULL CHECK (tipo IN ('FISICO', 'DIGITAL')),
    ubicacion_codigo CHAR(3) NOT NULL -- Ej: 'USA', 'ESP'
);

-- Tabla central de existencias por producto
CREATE TABLE stock_productos (
    id_stock SERIAL PRIMARY KEY,
    ean VARCHAR(13)  REFERENCES videojuegos_proyeccion(ean), -- FK lógica al microservicio de Catálogo
    id_almacen INT REFERENCES almacenes(id_almacen),
    cantidad INT NOT NULL DEFAULT 0 CHECK (cantidad >= 0),
    CONSTRAINT uq_juego_almacen UNIQUE (ean, id_almacen)
);

-- Tabla de historial para auditoría y trazabilidad
CREATE TABLE movimientos_stock (
    id_movimiento BIGSERIAL PRIMARY KEY,
    id_stock INT REFERENCES stock_productos(id_stock),
    tipo_movimiento VARCHAR(10) CHECK (tipo_movimiento IN ('ENTRADA', 'SALIDA')),
    cantidad_afectada INT NOT NULL,
    fecha_movimiento TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Índices para mejorar velocidad de consulta de disponibilidad
CREATE INDEX idx_stock_juego ON stock_productos(ean);
CREATE INDEX idx_movimiento_fecha ON movimientos_stock(fecha_movimiento);

-- 4. Poblar las tablas con datos de prueba coherentes
INSERT INTO videojuegos_proyeccion VALUES ('3391892017250', 'Elden Ring', 54990.00);
INSERT INTO videojuegos_proyeccion VALUES ('0045496478759', 'The Legend of Zelda', 59990.00);
INSERT INTO videojuegos_proyeccion VALUES('0014633382075', 'FC 24', 45000.00);   

-- Configuración de almacenes
INSERT INTO almacenes (nombre, tipo, ubicacion_codigo) VALUES 
('Servidor Global Keys', 'DIGITAL', 'GLB'),
('Bodega Central Santiago', 'FISICO', 'SCL'),
('Almacén Europa', 'FISICO', 'MAD');

-- Stock inicial (Casos: Stock abundante, Stock crítico, Stock agotado)
INSERT INTO stock_productos (ean, id_almacen, cantidad) VALUES 
('3391892017250', 1, 500), -- Elden Ring (Digital): Stock de sobra
('0045496478759', 2, 2),   -- Zelda (Físico): Stock crítico
('0014633382075', 3, 0);   -- FIFA 24 (Físico): Agotado

-- Movimientos de stock (Casos: Reposición y Venta)
INSERT INTO movimientos_stock (id_stock, tipo_movimiento, cantidad_afectada) VALUES 
(1, 'ENTRADA', 500), -- Carga inicial de keys
(2, 'SALIDA', 1),    -- Venta de una unidad física
(2, 'SALIDA', 1),    -- Venta de otra unidad (deja el stock en 0 pronto)
(3, 'ENTRADA', 10);  -- Intento de reposición fallida (registrada antes de quedar en 0)
