package pe.faustino.gestor.negocio.repository;

import pe.faustino.gestor.negocio.model.Kardex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KardexRepository extends JpaRepository<Kardex, Integer> {
}