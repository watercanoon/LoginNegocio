package pe.faustino.gestor.negocio.service;

import lombok.RequiredArgsConstructor;
import pe.faustino.gestor.negocio.model.Producto;
import pe.faustino.gestor.negocio.repository.ProductoRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    public Producto guardarProducto(Producto producto) {
        Producto nuevo = productoRepository.save(producto);
        // Notificación WebSocket
        messagingTemplate.convertAndSend("/topic/alertas", "Nuevo producto agregado: " + nuevo.getNombProd());
        return nuevo;
    }
}