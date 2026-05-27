@echo off
setlocal

:MENU
cls
echo.
echo ============================================
echo   Tienda - MENU PRINCIPAL
echo ============================================
echo.
echo   [1] Iniciar todos los servicios (dev)
echo   [2] Iniciar todos los servicios (test)
echo   [3] Compilar microservicios
echo   [4] Reinstalar dependencias Maven
echo.
echo   --- Servicios individuales ---
echo   [5] Iniciar Eureka
echo   [6] Iniciar ms-catalogo

echo   [7] Iniciar ms-usuarios

echo   [8] Iniciar ms-carrito

echo   [9] Iniciar ms-pedidos

echo   [10] Iniciar ms-pagos

echo   [11] Iniciar ms-inventario

echo   [12] Iniciar ms-opinion

echo   [13] Iniciar ms-notificaciones

echo   [14] Iniciar ms-promociones

echo   [15] Iniciar ms-biblioteca
echo.
echo   [0] Salir
echo.
echo ============================================
set /p opcion="  Selecciona una opcion: "

if "%opcion%"=="1" goto RUN_ALL
if "%opcion%"=="2" goto RUN_TEST
if "%opcion%"=="3" goto COMPILE
if "%opcion%"=="4" goto INSTALL
if "%opcion%"=="5" goto RUN_EUREKA
if "%opcion%"=="6" goto RUN_CATALOGO

if "%opcion%"=="7" goto RUN_USUARIOS

if "%opcion%"=="8" goto RUN_CARRITO

if "%opcion%"=="9" goto RUN_PEDIDOS

if "%opcion%"=="10" goto RUN_PAGOS

if "%opcion%"=="11" goto RUN_INVENTARIO

if "%opcion%"=="12" goto RUN_OPINION

if "%opcion%"=="13" goto RUN_NOTIFICACIONES

if "%opcion%"=="14" goto RUN_PROMOCIONES

if "%opcion%"=="15" goto RUN_BIBLIOTECA
if "%opcion%"=="0" goto SALIR

echo.
echo   Opcion invalida. Intenta de nuevo.
timeout /t 2 /nobreak > nul
goto MENU

REM ============================================

:RUN_ALL
cls
echo.
echo ===== Iniciando Eureka Server =====
start "EUREKA" mvn -f eureka spring-boot:run
timeout /t 5 /nobreak > nul
echo ===== Iniciando Microservicios =====
start "MS-CATALOGO" mvn -f ms-catalogo spring-boot:run

start "MS-USUARIOS" mvn -f ms-usuarios spring-boot:run

start "MS-CARRITO" mvn -f ms-carrito spring-boot:run

start "MS-PEDIDOS" mvn -f ms-pedidos spring-boot:run

start "MS-PAGOS" mvn -f ms-pagos spring-boot:run

start "MS-INVENTARIO" mvn -f ms-inventario spring-boot:run

start "MS-OPINION" mvn -f ms-opinion spring-boot:run

start "MS-NOTIFICACIONES" mvn -f ms-notificaciones spring-boot:run

start "MS-PROMOCIONES" mvn -f ms-promociones spring-boot:run

start "MS-BIBLIOTECA" mvn -f ms-biblioteca spring-boot:run
echo Todos los servicios han sido lanzados.
pause
goto MENU

:RUN_TEST
cls
echo.
echo ===== Iniciando Eureka Server (test) =====
start "EUREKA" java -jar eureka\target\cl-factogames-eureka-1.0-SNAPSHOT.jar --spring.profiles.active=test
timeout /t 5 /nobreak > nul
echo ===== Iniciando Microservicios (test) =====
start "MS-CATALOGO" java -jar ms-catalogo\\target\\cl-factogames-catalogo-0.0.1-SNAPSHOT.jar --spring.profiles.active=test

start "MS-USUARIOS" java -jar ms-usuarios\\target\\cl-factogames-usuarios-0.0.1-SNAPSHOT.jar --spring.profiles.active=test

start "MS-CARRITO" java -jar ms-carrito\\target\\cl-factogames-carrito-0.0.1-SNAPSHOT.jar --spring.profiles.active=test

start "MS-PEDIDOS" java -jar ms-pedidos\\target\\cl-factogames-pedidos-0.0.1-SNAPSHOT.jar --spring.profiles.active=test

start "MS-PAGOS" java -jar ms-pagos\\target\\cl-factogames-pagos-0.0.1-SNAPSHOT.jar --spring.profiles.active=test

start "MS-INVENTARIO" java -jar ms-inventario\\target\\cl-factogames-inventario-0.0.1-SNAPSHOT.jar --spring.profiles.active=test

start "MS-OPINION" java -jar ms-opinion\\target\\cl-factogames-opinion-0.0.1-SNAPSHOT.jar --spring.profiles.active=test

start "MS-NOTIFICACIONES" java -jar ms-notificaciones\\target\\cl-factogames-notificaciones-0.0.1-SNAPSHOT.jar --spring.profiles.active=test

start "MS-PROMOCIONES" java -jar ms-promociones\\target\\cl-factogames-promociones-0.0.1-SNAPSHOT.jar --spring.profiles.active=test

start "MS-BIBLIOTECA" java -jar ms-biblioteca\\target\\cl-factogames-biblioteca-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
echo Todos los servicios han sido lanzados en modo test.
pause
goto MENU

:COMPILE
cls
echo.
echo ===== Compilando microservicios =====
cd /d C:\tienda-test\ms-catalogo

call mvn clean install -U

cd /d C:\tienda-test\ms-usuarios

call mvn clean install -U

cd /d C:\tienda-test\ms-carrito

call mvn clean install -U

cd /d C:\tienda-test\ms-pedidos

call mvn clean install -U

cd /d C:\tienda-test\ms-pagos

call mvn clean install -U

cd /d C:\tienda-test\ms-inventario

call mvn clean install -U

cd /d C:\tienda-test\ms-opinion

call mvn clean install -U

cd /d C:\tienda-test\ms-notificaciones

call mvn clean install -U

cd /d C:\tienda-test\ms-promociones

call mvn clean install -U

cd /d C:\tienda-test\ms-biblioteca

call mvn clean install -U
echo Compilacion completada.
pause
goto MENU

:INSTALL
cls
echo.
echo === REINSTALACION DE DEPENDENCIAS MAVEN ===
echo.
echo Eliminando carpeta .m2 ...
rmdir /s /q %USERPROFILE%\.m2
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
echo Descargando dependencias nuevamente con Maven ...
mvn clean install -U -DskipTests
echo.
echo === PROCESO COMPLETADO ===
pause
goto MENU

:RUN_EUREKA
cls
echo.
echo ===== Iniciando Eureka =====
start "EUREKA" mvn -f eureka spring-boot:run
echo Eureka iniciado.
pause
goto MENU

:RUN_CATALOGO

cls

echo.

echo ===== Iniciando ms-catalogo =====

start "MS-CATALOGO" mvn -f ms-catalogo spring-boot:run

echo ms-catalogo iniciado.

pause

goto MENU



:RUN_USUARIOS

cls

echo.

echo ===== Iniciando ms-usuarios =====

start "MS-USUARIOS" mvn -f ms-usuarios spring-boot:run

echo ms-usuarios iniciado.

pause

goto MENU



:RUN_CARRITO

cls

echo.

echo ===== Iniciando ms-carrito =====

start "MS-CARRITO" mvn -f ms-carrito spring-boot:run

echo ms-carrito iniciado.

pause

goto MENU



:RUN_PEDIDOS

cls

echo.

echo ===== Iniciando ms-pedidos =====

start "MS-PEDIDOS" mvn -f ms-pedidos spring-boot:run

echo ms-pedidos iniciado.

pause

goto MENU



:RUN_PAGOS

cls

echo.

echo ===== Iniciando ms-pagos =====

start "MS-PAGOS" mvn -f ms-pagos spring-boot:run

echo ms-pagos iniciado.

pause

goto MENU



:RUN_INVENTARIO

cls

echo.

echo ===== Iniciando ms-inventario =====

start "MS-INVENTARIO" mvn -f ms-inventario spring-boot:run

echo ms-inventario iniciado.

pause

goto MENU



:RUN_OPINION

cls

echo.

echo ===== Iniciando ms-opinion =====

start "MS-OPINION" mvn -f ms-opinion spring-boot:run

echo ms-opinion iniciado.

pause

goto MENU



:RUN_NOTIFICACIONES

cls

echo.

echo ===== Iniciando ms-notificaciones =====

start "MS-NOTIFICACIONES" mvn -f ms-notificaciones spring-boot:run

echo ms-notificaciones iniciado.

pause

goto MENU



:RUN_PROMOCIONES

cls

echo.

echo ===== Iniciando ms-promociones =====

start "MS-PROMOCIONES" mvn -f ms-promociones spring-boot:run

echo ms-promociones iniciado.

pause

goto MENU



:RUN_BIBLIOTECA

cls

echo.

echo ===== Iniciando ms-biblioteca =====

start "MS-BIBLIOTECA" mvn -f ms-biblioteca spring-boot:run

echo ms-biblioteca iniciado.

pause

goto MENU

:SALIR
cls
echo.
echo   Hasta luego.
echo.
endlocal
exit /b
