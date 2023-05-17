package arg.com.delikiosco.repositories;

import arg.com.delikiosco.entities.Sale;
import java.time.LocalDateTime;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Interfaz para Sale que extiende de JpaRepository para heredar las querys de JPA y crear
 * querys personalizadas.
 */
@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

  @Query(value =
      "select s from Sale s where s.date between :saleDateStartOfDay and :saleDateEndOfDay")
  Set<Sale> getByDate(LocalDateTime saleDateStartOfDay, LocalDateTime saleDateEndOfDay);

  @Query(value = "select s from Sale s join s.providers pr where pr.cuit = :providerCuit")
  Set<Sale> getByProvider(Long providerCuit);

}
