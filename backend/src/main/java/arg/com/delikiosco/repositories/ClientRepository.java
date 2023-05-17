package arg.com.delikiosco.repositories;

import arg.com.delikiosco.entities.Client;
import arg.com.delikiosco.entities.Sale;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Interfaz para Client que extiende de JpaRepository para heredar las querys de JPA y crear
 * querys personalizadas.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

  Optional<Client> findByDni(Integer dni);

  @Query(value = "select c from Client c where c.active = true")
  Set<Client> findActives();

  @Query(value = "select buys from Client ")
  Set<Sale> findBuys();
}
