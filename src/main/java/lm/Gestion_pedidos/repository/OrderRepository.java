package lm.Gestion_pedidos.repository;

import lm.Gestion_pedidos.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Lucas
 */
public interface OrderRepository extends JpaRepository<Order, Long>{
    
}
