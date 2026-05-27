-- 1. Conexión a la base de datos específica
\c pedidos

-- 2. Eliminar tablas en orden jerárquico inverso
DROP TABLE IF EXISTS detalle_pedidos;
DROP TABLE IF EXISTS pedidos;
DROP TABLE IF EXISTS estados_pedido;
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

CREATE TABLE estados_pedido (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(30)  NOT NULL CHECK (nombre IN ('Pendiente','Pagado','Completado', 'Cancelado')), -- 'PENDIENTE', 'PAGADO', 'ENVIADO', 'CANCELADO'
    descripcion TEXT
);

CREATE TABLE pedidos (
    id SERIAL PRIMARY KEY,
    usuario_email VARCHAR(100) NOT NULL REFERENCES usuarios_proyeccion(email), -- Referencia lógica a microservicio usuarios
    id_estado INTEGER REFERENCES estados_pedido(id),
    fecha_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_venta NUMERIC(12, 2) NOT NULL CHECK (total_venta >= 0),
    codigo_seguimiento VARCHAR(50) UNIQUE
);

CREATE TABLE detalle_pedidos (
    id SERIAL PRIMARY KEY,
    id_pedido INTEGER REFERENCES pedidos(id) ON DELETE CASCADE,
    ean VARCHAR(13)  REFERENCES videojuegos_proyeccion(ean), -- Referencia lógica a microservicio catalogo
    cantidad INTEGER NOT NULL CHECK (cantidad > 0),
    precio_unitario NUMERIC(10, 2) NOT NULL
);

-- Índice para que el usuario consulte su historial de compras rápidamente
CREATE INDEX idx_pedidos_usuario ON pedidos(usuario_email);

-- 4. Poblado con datos de prueba reales

INSERT INTO usuarios_proyeccion VALUES ('j.perez@duocuc.cl', 'Juan', 'Perez', 'Cliente');
INSERT INTO usuarios_proyeccion VALUES ('marta.p@factogames.cl', 'Marta', 'Polanco', 'Soporte');
INSERT INTO videojuegos_proyeccion VALUES ('3391892017250', 'Elden Ring', 54990.00);
INSERT INTO videojuegos_proyeccion VALUES ('0045496478759', 'The Legend of Zelda', 59990.00);
INSERT INTO videojuegos_proyeccion VALUES ('5055060903056', 'Resident Evil 4', 39990.00);
INSERT INTO videojuegos_proyeccion VALUES ('3391892005981', 'Cyberpunk 2077', 35000.00);

INSERT INTO estados_pedido (nombre, descripcion) VALUES
('Pendiente', 'Pedido registrado esperando confirmación de pago'),
('Pagado', 'Pago verificado exitosamente'),
('Completado', 'Productos entregados o licencias liberadas'),
('Cancelado', 'Pedido anulado por el usuario o falta de stock');

INSERT INTO pedidos (usuario_email, id_estado, total_venta, codigo_seguimiento) VALUES
('j.perez@duocuc.cl', 2, 174970.00, 'TRK-9928341'), -- Pedido pagado de Juan
('marta.p@factogames.cl', 3, 39990.00, 'TRK-1122334'),  -- Pedido completado de Marta
('j.perez@duocuc.cl', 4, 35000.00, NULL);           -- Pedido cancelado de Juan

INSERT INTO detalle_pedidos (id_pedido, ean, cantidad, precio_unitario) VALUES
(1, '3391892017250', 1, 54990.00), -- 1 Elden Ring
(1, '0045496478759', 2, 59990.00), -- 2 Zelda
(2, '5055060903056', 1, 39990.00), -- 1 Resident Evil 4
(3, '3391892005981', 1, 35000.00); -- 1 Cyberpunk (cancelado)
