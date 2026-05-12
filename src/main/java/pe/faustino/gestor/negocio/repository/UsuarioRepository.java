package pe.faustino.gestor.negocio.repository;

import pe.faustino.gestor.negocio.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // Método personalizado para validar el login
    Optional<Usuario> findByLogiUsuaAndPassUsua(String logiUsua, String passUsua);
}