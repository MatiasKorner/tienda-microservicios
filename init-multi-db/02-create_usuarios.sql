    -- 1. Conexión a la base de datos específica
    \c usuarios

    -- 2. Eliminar tablas en orden jerárquico inverso
    DROP TABLE IF EXISTS perfiles;
    DROP TABLE IF EXISTS credenciales;
    DROP TABLE IF EXISTS usuarios;

    -- 3. Creación de tablas

    CREATE TABLE usuarios (
        id SERIAL PRIMARY KEY,
        nombre VARCHAR(150) NOT NULL,
        apellido VARCHAR(150) NOT NULL,
        email VARCHAR(100) NOT NULL UNIQUE,
        password VARCHAR(60) NOT NULL, -- Almacenará el hash de BCrypt
        rol VARCHAR(50)  NOT NULL CHECK (rol IN ('Administrador','Soporte','Cliente')), -- '1.Administrador', '2.Soporte', '3.Cliente'
        activo BOOLEAN DEFAULT TRUE
    );


    CREATE TABLE perfiles (
        id SERIAL PRIMARY KEY,
        usuario_email     VARCHAR(100) UNIQUE NOT NULL REFERENCES usuarios(email) ON DELETE CASCADE,
        fecha_registro DATE NOT NULL DEFAULT CURRENT_DATE,
        pais_origen VARCHAR(50) DEFAULT 'Chile',
        direccion  VARCHAR(180)
    );

    CREATE TABLE credenciales (
        id                SERIAL       PRIMARY KEY,
        usuario_email     VARCHAR(100) UNIQUE NOT NULL REFERENCES usuarios(email) ON DELETE CASCADE,
        ultimo_cambio_password     TIMESTAMP,
        bloqueado         BOOLEAN      NOT NULL DEFAULT FALSE,
        intentos_fallidos INT          NOT NULL DEFAULT 0 CHECK (intentos_fallidos >= 0)
    );

    -- Índice para búsquedas rápidas de login
    CREATE INDEX idx_usuarios_rol ON usuarios(rol);
    CREATE INDEX idx_usuarios_activo ON usuarios(activo);
    CREATE INDEX idx_perfiles_usuario_email ON perfiles(usuario_email);

    -- 4. Poblado con datos de prueba reales

    INSERT INTO usuarios (nombre, apellido, email, password, rol) VALUES
    ('Luis', 'Gomez', 'admin@factogames.cl', '$2a$10$8.UnVuG9shgE3.1G9WfDleN', 'Administrador'),
    ('Juan','Perez', 'j.perez@duocuc.cl', '$2a$10$u79kO79O79O79O79O79O7u', 'Cliente'),
    ('Marta', 'Polanco', 'marta.p@factogames.cl', '$2a$10$x98y98y98y98y98y98y98x', 'Soporte'),
    ('Joaquin', 'Valencia', 'joaquin.p@factogames.cl', '$2a$10$z123z123z123z123z123z', 'Soporte');

    INSERT INTO perfiles (usuario_email, pais_origen, direccion) VALUES
    ('admin@factogames.cl', 'Chile', 'Santiago'),
    ('j.perez@duocuc.cl', 'Chile', 'Pudahuel'),
    ('marta.p@factogames.cl', 'Chile', 'Melipilla'),
    ('joaquin.p@factogames.cl', 'Chile', 'Antofagasta');

    INSERT INTO credenciales (usuario_email, ultimo_cambio_password, bloqueado, intentos_fallidos) VALUES
    ('admin@factogames.cl',     NOW(), FALSE, 0),
    ('j.perez@duocuc.cl', NULL, FALSE, 0),
    ('marta.p@factogames.cl',        NOW(), FALSE, 1),
    ('joaquin.p@factogames.cl',        NULL,  FALSE, 2);

