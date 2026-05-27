@echo off
echo.
echo === REINSTALACION DE DEPENDENCIAS MAVEN ===
echo.

REM Paso 1: Eliminar carpeta local de dependencias
echo Eliminando carpeta .m2 ...
rmdir /s /q %USERPROFILE%.m2

REM Paso 2: Eliminar carpetas target de los proyectos
echo Eliminando carpetas target ...
rmdir /s /q C:\tienda-test\eureka\target

rmdir /s /q C:\tienda-test\ms-catalogo\target

rmdir /s /q C:\tienda-test\ms-usuarios\target

rmdir /s /q C:\tienda-test\ms-carrito\target

rmdir /s /q C:\tienda-test\ms-pedidos\target

rmdir /s /q C:\tienda-test\ms-pagos\target

rmdir /s /q C:\tienda-test\ms-inventario\target

rmdir /s /q C:\tienda-test\ms-opinion\target

rmdir /s /q C:\tienda-test\ms-notificaciones\target

rmdir /s /q C:\tienda-test\ms-promociones\target

rmdir /s /q C:\tienda-test\ms-biblioteca\target

REM Paso 3: Instalar todas las dependencias forzadamente
echo Descargando dependencias nuevamente con Maven ...
mvn clean install -U -DskipTests

echo.
echo === PROCESO COMPLETADO ===
pause