
package lm.Gestion_pedidos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Lucas
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor        
public class OrderProduct {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderProductId;
    
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    
    private Integer amount;
    private String observations;
    private BigDecimal priceWithModifications;
    
    
}
