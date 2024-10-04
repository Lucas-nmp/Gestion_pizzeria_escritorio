package lm.Gestion_pedidos.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad {@code Customer} que representa un cliente dentro de la aplicación.
 * <p>
 * Esta clase está anotada con {@link javax.persistence.Entity @Entity}, lo que la define como una entidad gestionada por JPA.
 * Utiliza las anotaciones {@link lombok.Data @Data}, {@link lombok.NoArgsConstructor @NoArgsConstructor} y 
 * {@link lombok.AllArgsConstructor @AllArgsConstructor} para generar automáticamente los métodos getter y setter, así como
 * los constructores con y sin argumentos.
 * </p>
 * <p>
 * Los atributos de esta entidad son:
 * <ul>
 *   <li>{@code customerId}: identificador único, generado automáticamente.</li>
 *   <li>{@code name}: nombre del cliente.</li>
 *   <li>{@code phone}: teléfono del cliente.</li>
 *   <li>{@code address}: dirección del cliente.</li>
 *   <li>{@code status}: obserbaciones hechas sobre el cliente.</li>
 * </ul>
 * </p>
 * <p>
 * Relacionada con otras entidades mediante:
 * <ul>
 *   <li>{@code @OneToMany} con la entidad {@link Order} para indicar los pedidos realizados por este cliente.</li>
 * </ul>
 * </p>
 * 
 * @author Lucas Morandeira Parejo
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    private String name;
    private String phone;
    private String address;
    private String status;
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders;
    
}
