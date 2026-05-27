-- 1. Conexión a la base de datos (se asume creada previamente)
\c pagos

-- 2. Eliminación en orden jerárquico inverso
DROP TABLE IF EXISTS transacciones CASCADE;
DROP TABLE IF EXISTS metodos_pago CASCADE;
DROP TABLE IF EXISTS estados_pago CASCADE;
DROP TABLE IF EXISTS usuarios_proyeccion CASCADE;

-- 3. Creación de tablas

CREATE TABLE usuarios_proyeccion (
    email VARCHAR(100) PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    apellido VARCHAR(150) NOT NULL,
    rol VARCHAR(50) NOT NULL
);

CREATE TABLE estados_pago (
    id_estado INT PRIMARY KEY,
    nombre VARCHAR(20)  NOT NULL
);
-- Tabla maestra para los estados (Pendiente, Completado, Fallido)
-- Tabla de métodos de pago vinculados al usuario
CREATE TABLE metodos_pago (
    id_metodo SERIAL PRIMARY KEY,
    usuario_email VARCHAR(100) NOT NULL REFERENCES usuarios_proyeccion(email), -- FK lógica al microservicio de Usuarios
    tipo VARCHAR(20) NOT NULL, -- Ejemplo: 'TARJETA', 'PAYPAL'
    ultimo_rastro VARCHAR(4) -- Ej: '4321' (4 últimos dígitos)
);

-- Tabla principal de transacciones
CREATE TABLE transacciones (
    id_transaccion UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_pedido BIGINT NOT NULL, -- FK lógica al microservicio de Pedidos
    id_metodo INT REFERENCES metodos_pago(id_metodo),
    id_estado INT REFERENCES estados_pago(id_estado),
    monto DECIMAL(10, 2) NOT NULL CHECK (monto > 0)
);

-- Índices para optimizar búsquedas por pedido y usuario
CREATE INDEX idx_transaccion_pedido ON transacciones(id_pedido);
CREATE INDEX idx_metodo_usuario ON metodos_pago(usuario_email);

-- 4. Poblado de datos de prueba
INSERT INTO usuarios_proyeccion VALUES ('j.perez@duocuc.cl', 'Juan', 'Perez', 'Cliente');
INSERT INTO usuarios_proyeccion VALUES ('marta.p@factogames.cl', 'Marta', 'Polanco', 'Soporte');

-- Estados básicos
INSERT INTO estados_pago (id_estado, nombre) VALUES 
(1, 'Pendiente'),
(2, 'Completado'),
(3, 'Fallido');

-- Métodos de pago (Casos: Usuario con varios métodos, datos reales)
INSERT INTO metodos_pago (usuario_email, tipo, ultimo_rastro) VALUES 
('j.perez@duocuc.cl', 'TARJETA', '4556'),
('j.perez@duocuc.cl', 'PAYPAL', 'PAYP'),
('marta.p@factogames.cl', 'TARJETA', '1234');

-- Transacciones (Casos de borde: Monto mínimo, diferentes estados)
INSERT INTO transacciones (id_pedido, id_metodo, id_estado, monto) VALUES 
(1, 1, 1, 174970.00), -- Pago pendiente
(2, 3, 2, 39990.00),  -- Pago exitoso
(3, 2, 3, 0.01);  -- Pago fallido monto mínimo