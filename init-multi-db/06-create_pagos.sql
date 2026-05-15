-- 1. Conexión a la base de datos (se asume creada previamente)
\c pagos

-- 2. Eliminación en orden jerárquico inverso
DROP TABLE IF EXISTS transacciones CASCADE;
DROP TABLE IF EXISTS metodos_pago CASCADE;
DROP TABLE IF EXISTS estados_pago CASCADE;

-- 3. Creación de tablas
-- Tabla maestra para los estados (Pendiente, Completado, Fallido, Reembolsado)
CREATE TABLE estados_pago (
    id_estado INT PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL UNIQUE
);

-- Tabla de métodos de pago vinculados al usuario
CREATE TABLE metodos_pago (
    id_metodo SERIAL PRIMARY KEY,
    id_usuario INT NOT NULL, -- FK lógica al microservicio de Usuarios
    tipo VARCHAR(20) NOT NULL, -- Ejemplo: 'TARJETA', 'PAYPAL'
    ultimo_rastro VARCHAR(4), -- Ej: '4321' (4 últimos dígitos)
    CONSTRAINT uq_usuario_metodo UNIQUE (id_usuario, tipo, ultimo_rastro)
);

-- Tabla principal de transacciones
CREATE TABLE transacciones (
    id_transaccion UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_pedido INT NOT NULL, -- FK lógica al microservicio de Pedidos
    id_metodo INT REFERENCES metodos_pago(id_metodo),
    id_estado INT REFERENCES estados_pago(id_estado),
    monto DECIMAL(10, 2) NOT NULL CHECK (monto > 0)
);

-- Índices para optimizar búsquedas por pedido y usuario
CREATE INDEX idx_transaccion_pedido ON transacciones(id_pedido);
CREATE INDEX idx_metodo_usuario ON metodos_pago(id_usuario);

-- 4. Poblado de datos de prueba
-- Estados básicos
INSERT INTO estados_pago (id_estado, nombre) VALUES 
(1, 'PENDIENTE'),
(2, 'COMPLETADO'),
(3, 'FALLIDO'),
(4, 'REEMBOLSADO');

-- Métodos de pago (Casos: Usuario con varios métodos, datos reales)
INSERT INTO metodos_pago (id_usuario, tipo, ultimo_rastro) VALUES 
(101, 'TARJETA', '4556'),
(101, 'PAYPAL', 'PAYP'),
(102, 'TARJETA', '1234');

-- Transacciones (Casos de borde: Monto mínimo, diferentes estados)
INSERT INTO transacciones (id_pedido, id_metodo, id_estado, monto) VALUES 
(5001, 1, 2, 59.99),  -- Pago exitoso
(5002, 2, 3, 0.01),   -- Pago fallido monto mínimo
(5003, 3, 1, 120.50), -- Pago pendiente
(5004, 1, 4, 15.00);  -- Pago reembolsado