-- ES FUNDAMENTAL EJECUTAR ESTE SCRIPT QUE PERMITE ELIMINAR LAS BASES DE DATOS
-- SI ES QUE EXISTEN, PARA LUEGO CREARLAS LIMPIAS SIN TABLAS Y DESDE CERO

SELECT 'CREATE DATABASE usuarios'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'usuarios') \gexec

SELECT 'CREATE DATABASE catalogo'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'catalogo') \gexec

SELECT 'CREATE DATABASE carrito'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'carrito') \gexec

SELECT 'CREATE DATABASE pedidos'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'pedidos') \gexec

SELECT 'CREATE DATABASE pagos'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'pagos') \gexec

SELECT 'CREATE DATABASE inventario'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'inventario') \gexec

SELECT 'CREATE DATABASE opinion'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'opinion') \gexec

SELECT 'CREATE DATABASE notificaciones'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'notificaciones') \gexec

SELECT 'CREATE DATABASE promociones'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'promociones') \gexec

SELECT 'CREATE DATABASE biblioteca'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'biblioteca') \gexec

