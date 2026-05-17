package cl.factogames.pedidos.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import cl.factogames.pedidos.dto.PedidoRequest;
import cl.factogames.pedidos.dto.PedidoResponse;
import cl.factogames.pedidos.dto.StockProductoResponse;
import cl.factogames.common.exception.*;
import cl.factogames.pedidos.mapper.PedidoMapper;
import cl.factogames.pedidos.model.Pedido;
import cl.factogames.pedidos.repository.PedidoRepository;
import cl.factogames.pedidos.client.InventarioClient;
import cl.factogames.pedidos.client.UsuarioClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;
    private final UsuarioClient usuarioClient;
    private final InventarioClient inventarioClient;

    public List<PedidoResponse> findAll() {
        return pedidoMapper.toResponseList(pedidoRepository.findAll());
    }

    public PedidoResponse findById(Integer id) {
        return pedidoMapper.toResponse(getPedidoById(id));
    }

    public List<PedidoResponse> findByUsuarioId(Integer idUsuario) {
        return pedidoMapper.toResponseList(pedidoRepository.findByIdUsuario(idUsuario));
    }

    public PedidoResponse findByCodigoSeguimiento(String codigo) {
        return pedidoMapper.toResponse(getPedidoByCodigo(codigo));
    }

    @Transactional
public PedidoResponse create(PedidoRequest request) {

    usuarioClient.findById(request.getIdUsuario());

    List<StockProductoResponse> stocks =
            inventarioClient.findByIdJuego(request.getIdVideojuego());

    if (stocks.isEmpty()) {
        throw new RuntimeException("No existe stock para el videojuego");
    }

    boolean stockDisponible = stocks.stream()
            .anyMatch(stock -> stock.getCantidad() >= request.getCantidad());

    if (!stockDisponible) {
        throw new RuntimeException("Stock insuficiente");
    }

    Pedido pedido = new Pedido();
    pedidoMapper.updateEntity(request, pedido);

    // Lógica adicional para creación de pedido
    pedido.setCodigoSeguimiento(generarCodigoUnico());

    return pedidoMapper.toResponse(pedidoRepository.save(pedido));
}

    @Transactional
    public PedidoResponse update(Integer id, PedidoRequest request) {
        Pedido pedido = getPedidoById(id);
        pedidoMapper.updateEntity(request, pedido);
        return pedidoMapper.toResponse(pedidoRepository.save(pedido));
    }

    @Transactional
    public void deleteById(Integer id) {
        Pedido pedido = getPedidoById(id);
        pedidoRepository.delete(pedido);
    }

    // Métodos auxiliares de búsqueda y validación similares a UsuarioService

    private Pedido getPedidoById(Integer id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedidos", "ID", id.longValue()));
    }

    private Pedido getPedidoByCodigo(String codigo) {
        return pedidoRepository.findByCodigoSeguimiento(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Pedidos", "Código Seguimiento", codigo));
    }

    private String generarCodigoUnico() {
        // Genera un código tipo TRK-XXXXX para el pedido
        return "TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}