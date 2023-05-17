package arg.com.delikiosco.repositories;

import arg.com.delikiosco.entities.Provider;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * Interfaz para Provider que extiende de JpaRepository para heredar las querys de JPA y para crear
 * querys personalizadas.
 */
@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {

  Optional<Provider> findByCuit(Long cuit);

  @Query(value =
      "select pr from Provider pr join fetch "
          + "pr.products p where pr.active = true and p.active = true")
  Set<Provider> findActives();

}
