-- 1. Conexión a la base de datos específica
\c carrito

-- 2. Eliminar tablas en orden jerárquico inverso
DROP TABLE IF EXISTS items_carrito;
DROP TABLE IF EXISTS carritos;
DROP TABLE IF EXISTS estados_carrito;
DROP TABLE IF EXISTS usuarios_proyeccion CASCADE;
DROP TABLE IF EXISTS videojuegos_proyeccion CASCADE;

-- 3. Creación de tablas

CREATE TABLE usuarios_proyeccion (
    email VARCHAR(100) PRIMARY KEY,
    username VARCHAR(50) NOT NULL
);

CREATE TABLE videojuegos_proyeccion (
    id VARCHAR(20) PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    precio NUMERIC(10, 2) NOT NULL
);

CREATE TABLE estados_carrito (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL UNIQUE, -- 'ACTIVO', 'ABANDONADO', 'CONVERTIDO'
    descripcion VARCHAR(100)
);

CREATE TABLE carritos (
    id SERIAL PRIMARY KEY,
    id_usuario INTEGER NOT NULL, -- Relación lógica con microservicio usuarios
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_estado INTEGER REFERENCES estados_carrito(id),
    ultima_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE items_carrito (
    id SERIAL PRIMARY KEY,
    id_carrito INTEGER REFERENCES carritos(id) ON DELETE CASCADE,
    id_producto INTEGER NOT NULL, -- Relación lógica con microservicio catalogo
    cantidad INTEGER NOT NULL CHECK (cantidad > 0),
    precio_unitario_momento NUMERIC(10, 2) NOT NULL -- Precio al momento de agregar
);

-- Índice para agilizar la recuperación del carrito de un usuario activo
CREATE INDEX idx_carrito_usuario_activo ON carritos(id_usuario) WHERE id_estado = 1;

-- 4. Poblado con datos de prueba reales

INSERT INTO estados_carrito (nombre, descripcion) VALUES
('ACTIVO', 'El usuario está agregando productos actualmente'),
('ABANDONADO', 'El carrito no ha tenido actividad por más de 24 horas'),
('CONVERTIDO', 'El carrito pasó a ser una orden de compra (pedido)');

INSERT INTO carritos (id_usuario, id_estado) VALUES
(2, 1), -- Carrito activo de Juan Pérez
(3, 3), -- Carrito de Marta que ya se convirtió en compra
(2, 2); -- Carrito antiguo abandonado por Juan

INSERT INTO items_carrito (id_carrito, id_producto, cantidad, precio_unitario_momento) VALUES
(1, 1, 1, 54990.00), -- Elden Ring en el carrito de Juan
(1, 2, 2, 59990.00), -- 2 Zelda en el carrito de Juan
(2, 4, 1, 39990.00), -- Resident Evil 4 comprado por Marta
(3, 5, 1, 35000.00); -- Cyberpunk que Juan dejó abandonado

INSERT INTO usuarios_proyeccion VALUES ('j.perez@duocuc.cl', 'Juan Perez');
INSERT INTO videojuegos_proyeccion VALUES ('ER-2022', 'Elden Ring', 59.99);