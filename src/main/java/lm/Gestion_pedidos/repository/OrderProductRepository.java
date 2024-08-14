package lm.Gestion_pedidos.repository;

import java.util.List;
import lm.Gestion_pedidos.model.Order;
import lm.Gestion_pedidos.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Lucas
 */
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long>{
    
    
    @Query("SELECT op FROM OrderProduct op WHERE op.order = :order")
    List<OrderProduct> getOrderProductsByOrder(@Param("order") Order order);
    
}
