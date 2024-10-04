package lm.Gestion_pedidos.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad {@code Order} que representa un pedido dentro de la aplicación.
 * <p>
 * Esta clase está anotada con Entity y Table, 
 * lo que la define como una entidad gestionada por JPA. La anotación Table(name = "orders") se utiliza 
 * para especificar el nombre de la tabla en la base de datos, debido a que "order" es una palabra reservada en SQL.
 * Utiliza las anotaciones Data, NoArgsConstructor y 
 * AllArgsConstructor para generar automáticamente los métodos getter y setter, 
 * así como los constructores con y sin argumentos.
 * </p>
 * <p>
 * Los principales atributos de esta entidad son:
 * <ul>
 *   <li>{@code orderId}: identificador único del pedido, generado automáticamente.</li>
 *   <li>{@code date}: fecha en que se realizó el pedido, representada como un {@link java.time.LocalDate}.</li>
 *   <li>{@code totalPrice}: precio total del pedido, representado como un {@link java.math.BigDecimal}.</li>
 * </ul>
 * </p>
 * <p>
 * Relacionada con otras entidades mediante:
 * <ul>
 *   <li>{@code @ManyToOne} con la entidad {@link Customer} para representar el cliente que realizó el pedido.</li>
 *   <li>{@code @OneToMany} con la entidad {@link OrderProduct} para representar los productos asociados a este pedido. 
 *   Utiliza {@code cascade = CascadeType.ALL} para propagar todas las operaciones (persistencia, eliminación, etc.) 
 *   desde la entidad `Order` a sus productos relacionados.</li>
 * </ul>
 * </p>
 * 
 * @author Lucas Morandeira Parejo
 */
@Entity
@Table(name = "orders") // Cambia el nombre de la tabla a "orders" porque la palabra order es una palabra reservada de SQL
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private LocalDate date;
    private BigDecimal totalPrice;
    
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts;
    
    
}