-- 1. Conexión a la base de datos específica
\c usuarios

-- 2. Eliminar tablas en orden jerárquico inverso
DROP TABLE IF EXISTS perfiles;
DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS roles;

-- 3. Creación de tablas

CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    nombreRol VARCHAR(50)  NOT NULL CHECK (nombreRol IN ('Administrador','Soporte','Cliente')), -- '1.Administrador', '2.Soporte', '3.Cliente'
    descripcion VARCHAR(100),
    permiso_nivel INTEGER DEFAULT 1 -- 'Escala de permisos del 1 al 10'
);

CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(60) NOT NULL, -- Almacenará el hash de BCrypt
    activo BOOLEAN DEFAULT TRUE,
    id_rol INTEGER REFERENCES roles(id) ON DELETE CASCADE
);


CREATE TABLE perfiles (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    usuario_email     VARCHAR(150) UNIQUE NOT NULL REFERENCES usuarios(email) ON DELETE CASCADE,
    fecha_nacimiento DATE,
    pais_origen VARCHAR(50) DEFAULT 'Chile'
);

-- Índice para búsquedas rápidas de login
CREATE INDEX idx_usuarios_activo ON usuarios(activo);
CREATE INDEX idx_perfiles_usuario_email ON perfiles(usuario_email);

-- 4. Poblado con datos de prueba reales

INSERT INTO roles (nombreRol, descripcion, permiso_nivel) VALUES
('Administrador', 'Acceso total al sistema y gestión de catálogo', 10),
('SOPORTE', 'Atención al cliente y gestión de tickets', 5),
('Cliente', 'Usuario final que realiza compras y reseñas', 1);

INSERT INTO usuarios (username, email, password, id_rol) VALUES
('admin_triskel', 'admin@factogames.cl', '$2a$10$8.UnVuG9shgE3.1G9WfDleN', 1),
('juan_gamer', 'j.perez@duocuc.cl', '$2a$10$u79kO79O79O79O79O79O7u', 3),
('marta_dev', 'marta.p@factogames.cl', '$2a$10$x98y98y98y98y98y98y98x', 2),
('joaquien_dev', 'joaquien.p@factogames.cl', '$2a$10$z123z123z123z123z123z', 3);

INSERT INTO perfiles (nombre, apellido, usuario_email, fecha_nacimiento, pais_origen) VALUES
('Luis', 'Gomez', 'admin@factogames.cl' '1990-05-15', 'Chile'),
('Juan','Perez', 'j.perez@duocuc.cl' '2002-11-20', 'Chile'),
('Marta', 'Polanco', 'marta.p@factogames.cl' '1998-03-10', 'Chile'),
('Joaquin', 'Valencia', 'joaquien.p@factogames.cl' '1995-08-25', 'Chile');
