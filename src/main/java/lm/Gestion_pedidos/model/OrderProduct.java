
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
 * Entidad {@code OrderProduct} que representa la relación entre un pedido y los productos que contiene dentro de la aplicación.
 * <p>
 * Esta clase está anotada con Entity, lo que la define como una entidad gestionada por JPA.
 * Utiliza las anotaciones Data, NoArgsConstructor y 
 * AllArgsConstructor para generar automáticamente los métodos getter y setter, 
 * así como los constructores con y sin argumentos.
 * </p>
 * <p>
 * Los principales atributos de esta entidad son:
 * <ul>
 *   <li>{@code orderProductId}: identificador único de la relación entre pedido y producto, generado automáticamente.</li>
 *   <li>{@code amount}: cantidad de productos solicitados en el pedido.</li>
 *   <li>{@code observations}: observaciones adicionales relacionadas con el producto en el contexto del pedido.</li>
 *   <li>{@code priceWithModifications}: precio del producto teniendo en cuenta modificaciones o ajustes, representado como un {@link java.math.BigDecimal}.</li>
 * </ul>
 * </p>
 * <p>
 * Relacionada con otras entidades mediante:
 * <ul>
 *   <li>{@code @ManyToOne} con la entidad {@link Order} para indicar el pedido al que pertenece esta relación.</li>
 *   <li>{@code @ManyToOne} con la entidad {@link Product} para indicar el producto específico que forma parte del pedido.</li>
 * </ul>
 * </p>
 * 
 * @author Lucas Morandeira Parejo
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
