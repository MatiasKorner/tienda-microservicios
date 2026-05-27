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
    nombre VARCHAR(150) NOT NULL,
    apellido VARCHAR(150) NOT NULL,
    rol VARCHAR(50) NOT NULL
);

CREATE TABLE videojuegos_proyeccion (
    ean VARCHAR(13) PRIMARY KEY,
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
    usuario_email VARCHAR(100) NOT NULL REFERENCES usuarios_proyeccion(email), -- Relación lógica con microservicio usuarios
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_estado INTEGER REFERENCES estados_carrito(id),
    ultima_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE items_carrito (
    id SERIAL PRIMARY KEY,
    id_carrito INTEGER REFERENCES carritos(id) ON DELETE CASCADE,
    ean VARCHAR(13)  REFERENCES videojuegos_proyeccion(ean), -- Relación lógica con microservicio catalogo
    cantidad INTEGER NOT NULL CHECK (cantidad > 0),
    precio_unitario_momento NUMERIC(10, 2) NOT NULL -- Precio al momento de agregar
);

-- Índice para agilizar la recuperación del carrito de un usuario activo
CREATE INDEX idx_carrito_usuario_activo ON carritos(usuario_email) WHERE id_estado = 1;

-- 4. Poblado con datos de prueba reales
INSERT INTO usuarios_proyeccion VALUES ('j.perez@duocuc.cl', 'Juan', 'Perez', 'Cliente');
INSERT INTO usuarios_proyeccion VALUES ('marta.p@factogames.cl', 'Marta', 'Polanco', 'Soporte');
INSERT INTO videojuegos_proyeccion VALUES ('3391892017250', 'Elden Ring', 54990.00);
INSERT INTO videojuegos_proyeccion VALUES ('0045496478759', 'The Legend of Zelda', 59990.00);
INSERT INTO videojuegos_proyeccion VALUES ('5055060903056', 'Resident Evil 4', 39990.00);
INSERT INTO videojuegos_proyeccion VALUES ('3391892005981', 'Cyberpunk 2077', 35000.00);

INSERT INTO estados_carrito (nombre, descripcion) VALUES
('ACTIVO', 'El usuario está agregando productos actualmente'),
('ABANDONADO', 'El carrito no ha tenido actividad por más de 24 horas'),
('CONVERTIDO', 'El carrito pasó a ser una orden de compra (pedido)');

INSERT INTO carritos (usuario_email, id_estado) VALUES
('j.perez@duocuc.cl', 1), -- Carrito activo de Juan Pérez
('marta.p@factogames.cl', 3), -- Carrito de Marta que ya se convirtió en compra
('j.perez@duocuc.cl', 2); -- Carrito antiguo abandonado por Juan

INSERT INTO items_carrito (id_carrito, ean, cantidad, precio_unitario_momento) VALUES
(1, '3391892017250', 1, 54990.00), -- Elden Ring en el carrito de Juan
(1, '0045496478759', 2, 59990.00), -- 2 Zelda en el carrito de Juan
(2, '5055060903056', 1, 39990.00), -- Resident Evil 4 comprado por Marta
(3, '3391892005981', 1, 35000.00); -- Cyberpunk que Juan dejó abandonado

