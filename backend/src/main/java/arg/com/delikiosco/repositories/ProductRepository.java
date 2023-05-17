package arg.com.delikiosco.repositories;

import arg.com.delikiosco.entities.Product;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * Interfaz para Product que extiende de JpaRepository para heredar las querys de JPA.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  Optional<Product> findByName(String name);

  @Query(value = "select p from Product p where p.active")
  Set<Product> findActives();

  @Query(value = "select p from Product p where p.active = true and p.stock <= ?1")
  Set<Product> findByLowStock(Integer lowStockQuantity);

}
