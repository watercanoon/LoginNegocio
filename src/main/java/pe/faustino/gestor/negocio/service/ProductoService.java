package pe.faustino.gestor.negocio.service;

import pe.faustino.gestor.negocio.model.Producto;
import pe.faustino.gestor.negocio.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public void eliminar(Integer id) {
        productoRepository.deleteById(id);
    }

    public Producto buscarPorId(Integer id) {
        return productoRepository.findById(id).orElse(null);
    }
}