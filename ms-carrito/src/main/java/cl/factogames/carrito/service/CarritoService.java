package cl.factogames.carrito.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.factogames.carrito.client.CatalogoClient;
import cl.factogames.carrito.client.UsuarioClient;
import cl.factogames.carrito.dto.*;
import cl.factogames.carrito.mapper.*;
import cl.factogames.carrito.model.*;
import cl.factogames.carrito.repository.*;
import cl.factogames.common.exception.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarritoService {

    private static final String ESTADO_ACTIVO = "ACTIVO";

    private final CarritoRepository carritoRepository;
    private final EstadoCarritoRepository estadoCarritoRepository;
    private final ItemCarritoRepository itemCarritoRepository;
    private final UsuarioProyeccionRepository usuarioProyeccionRepository;
    private final VideojuegoProyeccionRepository videojuegoProyeccionRepository;

    private final CarritoMapper carritoMapper;
    private final UsuarioProyeccionMapper usuarioProyeccionMapper;
    private final VideojuegoProyeccionMapper videojuegoProyeccionMapper;
    private final UsuarioClient usuarioClient;
    private final CatalogoClient catalogoClient;

    // =========================================================
    // CARRITO
    // =========================================================

    public CarritoResponse findById(Long id) {
        return carritoMapper.toResponse(getCarritoById(id));
    }

    public CarritoResponse findCarritoActivo(String email) {
        Carrito carrito = carritoRepository
            .findByUsuarioEmailAndEstadoNombre(email, ESTADO_ACTIVO)
            .orElseThrow(() -> new EntityNotFoundException("Carritos", "email + estado ACTIVO", email));
        return carritoMapper.toResponse(carrito);
    }

    public List<CarritoResponse> findHistorialByUsuario(String email) {
        return carritoMapper.toResponseList(carritoRepository.findByUsuarioEmail(email));
    }

    @Transactional
    public CarritoResponse crearCarrito(CarritoRequest request) {
        validateUsuarioExiste(request.getEmail());
        validateSinCarritoActivo(request.getEmail());

        UsuarioProyeccion usuario = getUsuarioByEmail(request.getEmail());
        EstadoCarrito estadoActivo = getEstadoByNombre(ESTADO_ACTIVO);

        Carrito carrito = new Carrito();
        carrito.setUsuario(usuario);
        carrito.setEstado(estadoActivo);

        return carritoMapper.toResponse(carritoRepository.save(carrito));
    }

    @Transactional
    public CarritoResponse cambiarEstado(Long id, String nombreEstado) {
        Carrito carrito = getCarritoById(id);
        EstadoCarrito nuevoEstado = getEstadoByNombre(nombreEstado);
        carrito.setEstado(nuevoEstado);
        return carritoMapper.toResponse(carritoRepository.save(carrito));
    }

    @Transactional
    public void vaciarCarrito(Long id) {
        Carrito carrito = getCarritoById(id);
        validateCarritoActivo(carrito);
        itemCarritoRepository.deleteByCarritoId(id);
    }

    // =========================================================
    // ÍTEMS
    // =========================================================

    //valida existencia en catálogo antes de operar en proyección local
    @Transactional
    public CarritoResponse agregarItem(Long carritoId, ItemCarritoRequest request) {
        Carrito carrito = getCarritoById(carritoId);
        validateCarritoActivo(carrito);

        // Verifica contra el microservicio de catálogo (fuente de verdad)
        if (!catalogoClient.existsByEan(request.getEan())) {
            throw new EntityNotFoundException("Videojuegos", "EAN", request.getEan());
        }

        VideojuegoProyeccion videojuego = getVideojuegoByEan(request.getEan());

        Optional<ItemCarrito> itemExistente = itemCarritoRepository
            .findByCarritoIdAndVideojuegoEan(carritoId, request.getEan());

        if (itemExistente.isPresent()) {
            ItemCarrito item = itemExistente.get();
            item.setCantidad(item.getCantidad() + request.getCantidad());
            itemCarritoRepository.save(Objects.requireNonNull(item));
        } else {
            ItemCarrito nuevoItem = new ItemCarrito();
            nuevoItem.setCarrito(carrito);
            nuevoItem.setVideojuego(videojuego);
            nuevoItem.setCantidad(request.getCantidad());
            nuevoItem.setPrecioUnitarioMomento(videojuego.getPrecio());
            itemCarritoRepository.save(Objects.requireNonNull(nuevoItem));
        }

        return carritoMapper.toResponse(getCarritoById(carritoId));
    }

    @Transactional
    public CarritoResponse actualizarCantidadItem(Long carritoId, String ean, ItemCarritoRequest request) {
        validateCarritoActivo(getCarritoById(carritoId));

        ItemCarrito item = getItemByCarritoIdAndEan(carritoId, ean);
        item.setCantidad(request.getCantidad());
        itemCarritoRepository.save(item);

        return carritoMapper.toResponse(getCarritoById(carritoId));
    }

    @Transactional
    public void eliminarItem(Long carritoId, String ean) {
        validateCarritoActivo(getCarritoById(carritoId));
        ItemCarrito item = Objects.requireNonNull(getItemByCarritoIdAndEan(carritoId, ean));
        itemCarritoRepository.delete(item);
    }

    // =========================================================
    // PROYECCIONES
    // =========================================================

    @Transactional
    public void sincronizarUsuario(UsuarioProyeccionRequest request) {
        if (usuarioProyeccionRepository.existsByEmail(request.getEmail())) {
            UsuarioProyeccion existente = getUsuarioByEmail(request.getEmail());
            usuarioProyeccionMapper.updateEntity(request, existente);
            usuarioProyeccionRepository.save(Objects.requireNonNull(existente));
        } else {
            UsuarioProyeccion nuevo = Objects.requireNonNull(usuarioProyeccionMapper.toEntity(request));
            usuarioProyeccionRepository.save(nuevo);
        }
    }

    @Transactional
    public void sincronizarVideojuego(VideojuegoProyeccionRequest request) {
        if (videojuegoProyeccionRepository.existsByEan(request.getEan())) {
            VideojuegoProyeccion existente = getVideojuegoByEan(request.getEan());
            videojuegoProyeccionMapper.updateEntity(request, existente);
            videojuegoProyeccionRepository.save(Objects.requireNonNull(existente));
        } else {
            VideojuegoProyeccion nuevo = Objects.requireNonNull(videojuegoProyeccionMapper.toEntity(request));
            videojuegoProyeccionRepository.save(nuevo);
        }
    }

    // =========================================================
    // HELPERS PRIVADOS
    // =========================================================

    private Carrito getCarritoById(Long id) {
        return carritoRepository.findById(Objects.requireNonNull(id))
            .orElseThrow(() -> new EntityNotFoundException("Carritos", "ID", id));
    }

    private UsuarioProyeccion getUsuarioByEmail(String email) {
        return usuarioProyeccionRepository.findById(Objects.requireNonNull(email))
            .orElseThrow(() -> new EntityNotFoundException("Usuarios", "email", email));
    }

    private VideojuegoProyeccion getVideojuegoByEan(String ean) {
        return videojuegoProyeccionRepository.findById(Objects.requireNonNull(ean))
            .orElseThrow(() -> new EntityNotFoundException("Videojuegos", "EAN", ean));
    }

    private EstadoCarrito getEstadoByNombre(String nombre) {
        return estadoCarritoRepository.findByNombre(Objects.requireNonNull(nombre))
            .orElseThrow(() -> new EntityNotFoundException("EstadosCarrito", "nombre", nombre));
    }

    private ItemCarrito getItemByCarritoIdAndEan(Long carritoId, String ean) {
    return itemCarritoRepository.findByCarritoIdAndVideojuegoEan(carritoId, ean)
        .orElseThrow(() -> new EntityNotFoundException(
            "ItemsCarrito", "carritoId + EAN", carritoId + " / " + ean));            
}

    private void validateUsuarioExiste(String email) {
        if (!usuarioClient.existsByEmail(email)) {
            throw new EntityNotFoundException("Usuarios", "email", email);
        }
    }

    private void validateSinCarritoActivo(String email) {
        if (carritoRepository.existsByUsuarioEmailAndEstadoNombre(email, ESTADO_ACTIVO)) {
            throw new DuplicateResourceException("Un Carrito Activo", "email", email, ESTADO_ACTIVO);
        }
    }

    private void validateCarritoActivo(Carrito carrito) {
        if (!ESTADO_ACTIVO.equals(carrito.getEstado().getNombre())) {
            throw new InvalidStateException("Carrito", carrito.getId(), 
                carrito.getEstado().getNombre(), ESTADO_ACTIVO);
        }
    }
}