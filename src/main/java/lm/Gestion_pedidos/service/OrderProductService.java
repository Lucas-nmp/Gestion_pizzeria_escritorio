package lm.Gestion_pedidos.service;

import java.util.List;
import lm.Gestion_pedidos.model.Order;
import lm.Gestion_pedidos.model.OrderProduct;
import lm.Gestion_pedidos.repository.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lucas
 */
@Service
public class OrderProductService {
    
    @Autowired
    OrderProductRepository orderProductRepository;
    
    public void saveOrderProduct(OrderProduct orderProduct){
        orderProductRepository.save(orderProduct);
    }
    
    public List<OrderProduct> getOrdersProductByOrder (Order order) {
        return orderProductRepository.getOrderProductsByOrder(order);
    }
}


