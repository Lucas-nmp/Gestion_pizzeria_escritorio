package lm.Gestion_pedidos.service;

import java.util.List;
import lm.Gestion_pedidos.model.Order;
import lm.Gestion_pedidos.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lucas
 */
@Service
public class OrderService {
    
    @Autowired
    OrderRepository orderRepository;
    
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }
    
    public void addOrder(Order order) {
        orderRepository.save(order);
    }
    
    // buscar el último pedido del cliente
    // buscar pedidos por cliente
    // por rango de fecha
    // del último mes
    // del último año
    // el total de pedido por fechas 
}
