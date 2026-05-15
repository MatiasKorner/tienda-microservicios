-- 1. Conexión a la base de datos específica
\c pedidos

-- 2. Eliminar tablas en orden jerárquico inverso
DROP TABLE IF EXISTS detalle_pedidos;
DROP TABLE IF EXISTS pedidos;
DROP TABLE IF EXISTS estados_pedido;
DROP TABLE IF EXISTS usuarios_proyeccion CASCADE;

-- 3. Creación de tablas

CREATE TABLE usuarios_proyeccion (
    email VARCHAR(100) PRIMARY KEY,
    username VARCHAR(50) NOT NULL
);

CREATE TABLE estados_pedido (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL UNIQUE, -- 'PENDIENTE', 'PAGADO', 'ENVIADO', 'CANCELADO'
    descripcion TEXT
);

CREATE TABLE pedidos (
    id SERIAL PRIMARY KEY,
    id_usuario INTEGER NOT NULL, -- Referencia lógica a microservicio usuarios
    id_estado INTEGER REFERENCES estados_pedido(id),
    fecha_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_venta NUMERIC(12, 2) NOT NULL CHECK (total_venta >= 0),
    codigo_seguimiento VARCHAR(50) UNIQUE
);

CREATE TABLE detalle_pedidos (
    id SERIAL PRIMARY KEY,
    id_pedido INTEGER REFERENCES pedidos(id) ON DELETE CASCADE,
    id_producto INTEGER NOT NULL, -- Referencia lógica a microservicio catalogo
    cantidad INTEGER NOT NULL CHECK (cantidad > 0),
    precio_unitario NUMERIC(10, 2) NOT NULL
);

-- Índice para que el usuario consulte su historial de compras rápidamente
CREATE INDEX idx_pedidos_usuario ON pedidos(id_usuario);

-- 4. Poblado con datos de prueba reales

INSERT INTO estados_pedido (nombre, descripcion) VALUES
('PENDIENTE', 'Pedido registrado esperando confirmación de pago'),
('PAGADO', 'Pago verificado exitosamente'),
('COMPLETADO', 'Productos entregados o licencias liberadas'),
('CANCELADO', 'Pedido anulado por el usuario o falta de stock');

INSERT INTO pedidos (id_usuario, id_estado, total_venta, codigo_seguimiento) VALUES
(2, 2, 174970.00, 'TRK-9928341'), -- Pedido pagado de Juan
(3, 3, 39990.00, 'TRK-1122334'),  -- Pedido completado de Marta
(2, 4, 35000.00, NULL);           -- Pedido cancelado de Juan

INSERT INTO detalle_pedidos (id_pedido, id_producto, cantidad, precio_unitario) VALUES
(1, 1, 1, 54990.00), -- 1 Elden Ring
(1, 2, 2, 59990.00), -- 2 Zelda
(2, 4, 1, 39990.00), -- 1 Resident Evil 4
(3, 5, 1, 35000.00); -- 1 Cyberpunk (cancelado)

INSERT INTO usuarios_proyeccion VALUES ('j.perez@duocuc.cl', 'Juan Perez');