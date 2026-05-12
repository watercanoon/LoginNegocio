package pe.faustino.gestor.negocio.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.faustino.gestor.negocio.model.Kardex;
import pe.faustino.gestor.negocio.model.Producto;
import pe.faustino.gestor.negocio.repository.KardexRepository;
import pe.faustino.gestor.negocio.repository.ProductoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KardexService {

    private final KardexRepository kardexRepository;
    private final ProductoRepository productoRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public List<Kardex> listarHistorial() {
        return kardexRepository.findAll();
    }

    @Transactional // Asegura que si falla la actualización del producto, no se guarde el kardex
    public Kardex registrarMovimiento(Integer codiProd, Integer cantidad, Integer tipoOper) {
        // 1. Buscar el producto
        Producto producto = productoRepository.findById(codiProd)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + codiProd));

        // 2. Calcular nuevo saldo (1: Entrada, 0: Salida)
        // Nota: En tu DDL de producto no definimos stock,
        // pero para un Kardex real el producto debe tener un campo 'stock' o calcularlo del último kardex.
        // Asumiendo que agregaste 'stock' al modelo Producto:
        int stockActual = (producto.getStock() != null) ? producto.getStock() : 0;
        int nuevoSaldo;

        if (tipoOper == 1) { // Entrada
            nuevoSaldo = stockActual + cantidad;
        } else { // Salida
            if (stockActual < cantidad) {
                throw new RuntimeException("Stock insuficiente para realizar la salida");
            }
            nuevoSaldo = stockActual - cantidad;
        }

        // 3. Actualizar el stock del producto
        producto.setStock(nuevoSaldo);
        productoRepository.save(producto);

        // 4. Crear registro en Kardex
        Kardex movimiento = new Kardex();
        movimiento.setProducto(producto);
        movimiento.setCantProd(cantidad);
        movimiento.setTipoOper(tipoOper);
        movimiento.setSaldProd(nuevoSaldo);

        Kardex guardado = kardexRepository.save(movimiento);

        // 5. NOTIFICACIÓN WEBSOCKET (Tiempo real)
        // Enviamos el producto actualizado al canal para que el dashboard refresque el stock
        messagingTemplate.convertAndSend("/topic/stock-actualizado", producto);

        return guardado;
    }
}