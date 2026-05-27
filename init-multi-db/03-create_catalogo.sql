-- 1. Conexión a la base de datos específica
-- Nota: La base de datos 'catalogo' debe estar creada previamente
\c catalogo

-- 2. Eliminar tablas en orden jerárquico inverso
DROP TABLE IF EXISTS videojuego_categoria;
DROP TABLE IF EXISTS videojuegos;
DROP TABLE IF EXISTS categorias;

-- 3. Creación de tablas


CREATE TABLE categorias (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion TEXT,
    restriccion_edad INTEGER NOT NULL,
    tags VARCHAR(100)
);

CREATE TABLE videojuegos (
    id SERIAL PRIMARY KEY,
    ean VARCHAR(13) UNIQUE NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    precio NUMERIC(10, 2) NOT NULL CHECK (precio >= 0),
    desarrollador VARCHAR(100) NOT NULL
);

CREATE TABLE videojuego_categoria (
    id SERIAL PRIMARY KEY,
    videojuego_id INT NOT NULL REFERENCES videojuegos(id) ON DELETE CASCADE,
    categoria_id INT NOT NULL REFERENCES categorias(id) ON DELETE RESTRICT,
    UNIQUE (videojuego_id, categoria_id)
);

-- Índices para optimizar búsquedas en la tienda
CREATE INDEX idx_videojuegos_titulo ON videojuegos(titulo);
CREATE INDEX idx_videojuego_categoria_videojuego ON videojuego_categoria(videojuego_id);
CREATE INDEX idx_videojuego_categoria_categoria ON videojuego_categoria(categoria_id);

-- 4. Poblado con datos de prueba reales y coherentes


INSERT INTO categorias (nombre, descripcion, restriccion_edad, tags) VALUES
('Acción/Aventura', 'Juegos de exploración y combate', 12, 'aventura, accion, mundo-abierto'),
('RPG', 'Juegos de rol y progresión de personajes', 16, 'rol, historia, fantasia'),
('Deportes', 'Simuladores deportivos y competencia', 3, 'futbol, carreras, tenis'),
('Horror', 'Juegos de terror y suspenso', 18, 'miedo, gore, supervivencia');

INSERT INTO videojuegos (ean, titulo, precio, desarrollador) VALUES
('3391892017250', 'Elden Ring', 54990.00, 'FromSoftware'),
('0045496478759', 'The Legend of Zelda', 59990.00, 'Nintendo'), 
('0014633382075', 'FC 24', 45000.00, 'EA'),               
('5055060903056', 'Resident Evil 4', 39990.00, 'Capcom'),   
('3391892005981', 'Cyberpunk 2077', 35000.00, 'CD Projekt');    

INSERT INTO videojuego_categoria (videojuego_id, categoria_id) VALUES
(1, 2), (1, 1), 
(2, 1), 
(3, 3), 
(4, 4), (4, 1), 
(5, 2), (5, 1);