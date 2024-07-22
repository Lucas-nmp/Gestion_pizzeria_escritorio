package lm.Gestion_pedidos.repository;

import lm.Gestion_pedidos.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Lucas
 */
public interface ProductRepository extends JpaRepository<Product, Long>{
    
}
