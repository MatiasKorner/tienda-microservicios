package cl.factogames.inventario.service;

import cl.factogames.common.exception.EntityNotFoundException;
import cl.factogames.inventario.client.VideojuegoClient;
import cl.factogames.inventario.dto.StockProductoRequest;
import cl.factogames.inventario.dto.StockProductoResponse;
import cl.factogames.inventario.mapper.StockProductoMapper;
import cl.factogames.inventario.model.Almacen;
import cl.factogames.inventario.model.StockProducto;
import cl.factogames.inventario.repository.AlmacenRepository;
import cl.factogames.inventario.repository.StockProductoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class StockProductoService {

    private final StockProductoRepository stockProductoRepository;
    private final AlmacenRepository almacenRepository;
    private final StockProductoMapper stockProductoMapper;
    private final VideojuegoClient videojuegoClient;

    public List<StockProductoResponse> findAll() {
        return stockProductoMapper.toResponseList(stockProductoRepository.findAll());
    }

    public StockProductoResponse findById(Integer id) {
        return stockProductoMapper.toResponse(getStockById(id));
    }

    public List<StockProductoResponse> findByIdJuego(Integer idJuego) {
        return stockProductoMapper.toResponseList(stockProductoRepository.findByIdJuego(idJuego));
    }

    public List<StockProductoResponse> findByAlmacenId(Integer idAlmacen) {
        return stockProductoMapper.toResponseList(stockProductoRepository.findByAlmacen_IdAlmacen(idAlmacen));
    }

    @Transactional
    public StockProductoResponse create(StockProductoRequest request) {

        videojuegoClient.findById(request.getIdJuego());

        Almacen almacen = getAlmacenById(request.getIdAlmacen());

        StockProducto stockProducto = stockProductoMapper.toEntity(request);
        stockProducto.setAlmacen(almacen);

        return stockProductoMapper.toResponse(stockProductoRepository.save(stockProducto));
    }

    @Transactional
    public StockProductoResponse update(Integer id, StockProductoRequest request) {
        StockProducto stockProducto = getStockById(id);
        Almacen almacen = getAlmacenById(request.getIdAlmacen());

        stockProductoMapper.updateEntity(request, stockProducto);
        stockProducto.setAlmacen(almacen);

        return stockProductoMapper.toResponse(stockProductoRepository.save(stockProducto));
    }

    @Transactional
    public void deleteById(Integer id) {
        StockProducto stockProducto = getStockById(id);
        stockProductoRepository.delete(stockProducto);
    }

    private StockProducto getStockById(Integer id) {
        return stockProductoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("StockProducto", "ID", id.longValue()));
    }

    private Almacen getAlmacenById(Integer idAlmacen) {
        return almacenRepository.findById(idAlmacen)
                .orElseThrow(() -> new EntityNotFoundException("Almacen", "ID", idAlmacen.longValue()));
    }
}