package cl.factogames.pagos.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import cl.factogames.pagos.client.PedidoClient;

import cl.factogames.common.exception.EntityNotFoundException;
import cl.factogames.pagos.dto.TransaccionRequest;
import cl.factogames.pagos.dto.TransaccionResponse;
import cl.factogames.pagos.mapper.TransaccionMapper;
import cl.factogames.pagos.model.EstadoPago;
import cl.factogames.pagos.model.MetodoPago;
import cl.factogames.pagos.model.Transaccion;
import cl.factogames.pagos.repository.EstadoPagoRepository;
import cl.factogames.pagos.repository.MetodoPagoRepository;
import cl.factogames.pagos.repository.TransaccionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final MetodoPagoRepository metodoPagoRepository;
    private final EstadoPagoRepository estadoPagoRepository;
    private final TransaccionMapper transaccionMapper;
    private final PedidoClient pedidoClient;

    public List<TransaccionResponse> findAll() {
        return transaccionMapper.toResponseList(transaccionRepository.findAll());
    }

    public TransaccionResponse findById(UUID id) {
        return transaccionMapper.toResponse(getTransaccionById(id));
    }

    public List<TransaccionResponse> findByPedido(Long idPedido) {
        return transaccionMapper.toResponseList(
                transaccionRepository.findByIdPedido(idPedido));
    }

    @Transactional
    public TransaccionResponse create(TransaccionRequest request) {

        pedidoClient.findById(request.getIdPedido());

        Transaccion transaccion = new Transaccion();
       
        // Mapea campos simples
        transaccionMapper.updateEntity(request, transaccion);

        // Busca método de pago
        MetodoPago metodoPago = metodoPagoRepository.findById(request.getIdMetodo())
                .orElseThrow(() -> new EntityNotFoundException(
                        "MétodoPago",
                        "ID",
                        request.getIdMetodo().longValue()));

        // Estado por defecto
        EstadoPago estadoPago = estadoPagoRepository.findByNombre("PENDIENTE")
                .orElseThrow(() -> new EntityNotFoundException(
                        "EstadoPago",
                        "Nombre",
                        "PENDIENTE"));

        // Asigna relaciones
        transaccion.setMetodoPago(metodoPago);
        transaccion.setEstado(estadoPago);

        return transaccionMapper.toResponse(
                transaccionRepository.save(transaccion));
    }

    @Transactional
    public TransaccionResponse update(UUID id, TransaccionRequest request) {

        Transaccion transaccion = getTransaccionById(id);

        transaccionMapper.updateEntity(request, transaccion);

        MetodoPago metodoPago = metodoPagoRepository.findById(request.getIdMetodo())
                .orElseThrow(() -> new EntityNotFoundException(
                        "MétodoPago",
                        "ID",
                        request.getIdMetodo().longValue()));

        transaccion.setMetodoPago(metodoPago);

        return transaccionMapper.toResponse(
                transaccionRepository.save(transaccion));
    }

    @Transactional
    public void deleteById(UUID id) {

        Transaccion transaccion = getTransaccionById(id);

        transaccionRepository.delete(transaccion);
    }

    // =========================
    // Métodos auxiliares
    // =========================

    private Transaccion getTransaccionById(UUID id) {

        return transaccionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Transaccion",
                        "ID",
                        id.toString()));
    }
}
