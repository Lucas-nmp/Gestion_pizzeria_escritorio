package lm.Gestion_pedidos.repository;

import lm.Gestion_pedidos.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Lucas
 */
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long>{
    
}
